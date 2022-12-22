package com.example.notesapp.presentation.allnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllNotesViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(listOf())
    val notes: StateFlow<List<Note>> = _notes

    fun getNotes(callback: () -> Unit) {
        try {
            viewModelScope.launch {
                noteRepository.getNotes().collect { notes ->
                    _notes.value = notes
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            callback.invoke()
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
}
