package com.example.notes.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndNotes(
    @Embedded val notes: Notes,
    @Relation(
        parentColumn = "username",
        entityColumn = "username"
    )
    val user: User
)
