package com.example.notes.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.data.model.Notes
import com.example.notes.data.model.UserAndNotes

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(notes: Notes)


    @Query("SELECT * FROM notes WHERE username=:username ORDER BY id_notes DESC")
    fun getAllDataNotes(username : String) : LiveData<List<UserAndNotes>>

    @Update
    suspend fun editNote(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)
}