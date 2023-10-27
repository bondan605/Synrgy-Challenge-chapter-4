package com.example.notes.data.room.repository

import androidx.lifecycle.LiveData
import com.example.notes.data.room.dao.NoteDao
import com.example.notes.data.model.Notes
import com.example.notes.data.model.UserAndNotes

class NotesRepository(private val noteDao: NoteDao) {

    fun getAllDataNotes(username: String): LiveData<List<UserAndNotes>> =
        noteDao.getAllDataNotes(username)

    suspend fun addNote(notes: Notes) = noteDao.insertNote(notes)

    suspend fun editNote(notes: Notes) = noteDao.editNote(notes)

    suspend fun deleteNote(notes: Notes) = noteDao.deleteNote(notes)

}