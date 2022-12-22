package com.example.notesapp.presentation.createnote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.launch

class CreateNoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    fun addNote(note: Note, callback: () -> Unit) {
        try {
            viewModelScope.launch {
                noteRepository.insertNote(note)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            callback.invoke()
        }
    }

    fun getNote(id: Int) {
        try {
            viewModelScope.launch {
                _note.value = noteRepository.getNoteById(id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
