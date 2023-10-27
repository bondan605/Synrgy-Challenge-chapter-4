package com.example.notes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.data.model.User
import com.example.notes.data.room.NotesDatabase
import com.example.notes.data.room.repository.UserRepository
import kotlinx.coroutines.launch

class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {

    var getAllDataUser : LiveData<List<User>>
    private var userRepository : UserRepository

    init {
        val userDao = NotesDatabase.getInstance(application)?.userDao()
        userRepository = UserRepository(userDao!!)
        getAllDataUser = userRepository.getUser
    }

    fun registerUser(user: User){
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun verifyUser(username : String, password : String) : LiveData<User> =
        userRepository.getUser(username, password)
}