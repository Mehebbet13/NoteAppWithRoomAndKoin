package com.example.notesapp.presentation.allnotes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.domain.model.Note
import kotlinx.android.synthetic.main.note.view.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notes: MutableList<Note> = mutableListOf()
    var onItemClick: ((id: Int) -> Unit)? = null
    var onDeleteItemClick: ((note: Note) -> Unit)? = null

    fun setData(data: List<Note>) {
        this.notes.clear()
        this.notes.addAll(data)
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.note,
            parent,
            false
        )
        return NotesViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.title.text = note.title
            itemView.content.text = note.body
            itemView.delete.setOnClickListener {
                onDeleteItemClick?.invoke(note)
                notifyItemRangeRemoved(notes.indexOf(note), notes.size - 1)
            }
            itemView.note.setOnClickListener {
                note.id?.let { it1 -> onItemClick?.invoke(it1) }
            }
        }
    }
}
