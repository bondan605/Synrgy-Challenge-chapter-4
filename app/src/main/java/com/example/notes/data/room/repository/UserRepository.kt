package com.example.notes.data.room.repository

import androidx.lifecycle.LiveData
import com.example.notes.data.model.User
import com.example.notes.data.room.dao.UserDao

class UserRepository(private val userDao: UserDao) {

    val getUser: LiveData<List<User>> = userDao.getAllDataUser()

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    fun getUser(username: String, password: String) = userDao.getUser(username, password)

}