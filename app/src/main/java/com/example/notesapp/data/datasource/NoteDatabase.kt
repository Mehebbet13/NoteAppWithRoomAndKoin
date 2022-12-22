package com.example.notesapp.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}
