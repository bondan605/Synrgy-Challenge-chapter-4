package com.example.notes.ui.fragment.authentication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.data.model.User
import com.example.notes.databinding.FragmentRegisterBinding
import com.example.notes.ui.Constant.Companion.EMAIL
import com.example.notes.ui.Constant.Companion.NAMA
import com.example.notes.ui.Constant.Companion.PASSWORD
import com.example.notes.ui.Constant.Companion.USER
import com.example.notes.ui.viewmodel.AuthenticationViewModel

class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authenticationViewModel : AuthenticationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        binding.apply {
            val username = etUsernameRegis.text.toString()
            val email = etEmailRegister.text.toString().lowercase()
            val password = etPassRegister.text.toString()
            val konfirmPass = etKonfirPass.text.toString()

            if (etUsernameRegis.text!!.isEmpty() || etEmailRegister.text!!.isEmpty() || etPassRegister.text!!.isEmpty() || etKonfirPass.text!!.isEmpty()){
                Toast.makeText(context, "Tidak boleh kosong!!", Toast.LENGTH_SHORT).show()
            }else{
                if (konfirmPass != password){
                    Toast.makeText(context, "Password tidak sama", Toast.LENGTH_SHORT).show()
                }else{
                    authenticationViewModel.registerUser(User(0, username, email, password))
                    Toast.makeText(context, "Register berhasil!", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}