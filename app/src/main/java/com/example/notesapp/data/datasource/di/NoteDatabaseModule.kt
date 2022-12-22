package com.example.notesapp.data.datasource.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.data.datasource.NoteDao
import com.example.notesapp.data.datasource.NoteDatabase
import com.example.notesapp.data.datasource.NoteDatabase.Companion.DATABASE_NAME
import com.example.notesapp.data.repository.NoteRepositoryImpl
import com.example.notesapp.domain.repository.NoteRepository
import com.example.notesapp.presentation.allnotes.viewmodel.AllNotesViewModel
import com.example.notesapp.presentation.createnote.viewmodel.CreateNoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun providesDatabase(application: Application): NoteDatabase =
    Room.databaseBuilder(application, NoteDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()

val noteDatabaseModule = module {
    single { providesDatabase(get()) }
    single { provideDao(get()) }
//    factory { GetNotesUseCase(get()) }
//    factory { InsertNoteUseCase(get()) }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    viewModel {
        AllNotesViewModel(get())
    }
    viewModel {
        CreateNoteViewModel(get())
    }
}
