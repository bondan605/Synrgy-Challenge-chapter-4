package com.example.notes.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id_notes: Int,
    val title: String,
    val content: String,
    val username: String,
) : Parcelable
