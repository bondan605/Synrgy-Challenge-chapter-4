package com.example.notes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.data.room.NotesDatabase
import com.example.notes.data.model.Notes
import com.example.notes.data.model.UserAndNotes
import com.example.notes.data.room.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository: NotesRepository

    init {
        val notesDao = NotesDatabase.getInstance(application)?.noteDao()
        notesRepository = NotesRepository(notesDao!!)
    }

    fun getDataNotes(username: String): LiveData<List<UserAndNotes>> =
        notesRepository.getAllDataNotes(username)

    fun addNote(notes: Notes) {
        viewModelScope.launch {
            notesRepository.addNote(notes)
        }
    }

    fun editNotes(notes: Notes) {
        viewModelScope.launch {
            notesRepository.editNote(notes)
        }
    }

    fun deleteNote(notes: Notes) {
        viewModelScope.launch {
            notesRepository.deleteNote(notes)
        }
    }
}