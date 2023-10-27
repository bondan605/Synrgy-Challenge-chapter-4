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
import com.example.notes.databinding.FragmentLoginBinding
import com.example.notes.ui.Constant.Companion.EMAIL
import com.example.notes.ui.Constant.Companion.NAMA
import com.example.notes.ui.Constant.Companion.PASSWORD
import com.example.notes.ui.Constant.Companion.USER
import com.example.notes.ui.Constant.Companion.USER_SEDANG_LOGIN
import com.example.notes.ui.viewmodel.AuthenticationViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(USER, Context.MODE_PRIVATE)

        binding.btnToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

    }

    private fun login() {
        binding.apply {
            val email = etEmailLogin.text.toString().lowercase()
            val password = etPassLogin.text.toString()

            if (etEmailLogin.text!!.isEmpty() || etPassLogin.text!!.isEmpty()) {
                Toast.makeText(context, "Tidak boleh kosong!!", Toast.LENGTH_SHORT).show()
            } else {
                authenticationViewModel.verifyUser(email, password).observe(viewLifecycleOwner) {
                    if (it == null) {
                        Toast.makeText(context, "Password/Email salah!", Toast.LENGTH_SHORT).show()
                    } else {
                        if (it.email == email && it.password == password) {
                            getSharedPreferences(it.username)
                            Toast.makeText(context, "Login berhasil!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }
                }
            }
        }
    }

    private fun getSharedPreferences(username: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(USER_SEDANG_LOGIN, true)
        editor.putString(NAMA, username)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}