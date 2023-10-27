package com.example.notes.ui.fragment.notes

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.data.model.Notes
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.ui.Constant.Companion.NAMA
import com.example.notes.ui.Constant.Companion.USER
import com.example.notes.ui.Constant.Companion.USER_SEDANG_LOGIN
import com.example.notes.ui.adapter.NotesAdapter
import com.example.notes.ui.viewmodel.NotesViewModel

class HomeFragment : Fragment(), NotesAdapter.NotesInterface {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NotesAdapter
    private val notesViewModel: NotesViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(USER, Context.MODE_PRIVATE)
        builder = AlertDialog.Builder(context)
        setDataRv()

        binding.apply {

            btnToAdd.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addNotesFragment)
            }

            btnLogout.setOnClickListener {
                logout()
            }
        }
    }

    private fun setDataRv() {
        adapter = NotesAdapter(this)
        val editor = sharedPreferences.getString(NAMA, "")
        binding.tvGreetUsername.text = "Welcome, $editor!"

        binding.apply {
            notesViewModel.getDataNotes(editor.toString()).observe(viewLifecycleOwner) {
                adapter.setData(it)

                if (it.isEmpty()) {
                    tvAlertKosong.visibility = View.VISIBLE
                } else
                    tvAlertKosong.visibility = View.GONE
            }

            rvNotes.adapter = adapter
            rvNotes.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun editNote(notes: Notes) {
        val actionEdit = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(notes)
        findNavController().navigate(actionEdit)
    }

    override fun deleteNote(notes: Notes) {
        builder.setTitle("Warning!")
            .setMessage("Apakah kamu yakin ingin menghapus note ini?")
            .setCancelable(true)
            .setPositiveButton("Ya") { _, _ ->
                Toast.makeText(context, "Catatan Berhasil dihapus", Toast.LENGTH_SHORT).show()
                notesViewModel.deleteNote(notes)
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        val editor = sharedPreferences.edit()
        editor.remove(USER_SEDANG_LOGIN)
        editor.apply()

        Toast.makeText(context, "Berhasil logout", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}