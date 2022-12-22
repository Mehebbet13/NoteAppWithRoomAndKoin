package com.example.notesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.data.datasource.NoteDatabase.Companion.DATABASE_NAME

@Entity
data class Note(
    val title: String,
    val body: String,
    @PrimaryKey
    val id: Int? = null
)
