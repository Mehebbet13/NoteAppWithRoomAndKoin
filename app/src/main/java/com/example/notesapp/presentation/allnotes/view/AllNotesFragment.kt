package com.example.notesapp.presentation.allnotes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAllNotesBinding
import com.example.notesapp.presentation.allnotes.viewmodel.AllNotesViewModel
import com.example.notesapp.utils.ID
import com.example.notesapp.utils.showSnackBar
import kotlinx.android.synthetic.main.fragment_all_notes.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllNotesFragment : Fragment() {

    private val viewModel by viewModel<AllNotesViewModel>()
    private lateinit var binding: FragmentAllNotesBinding
    private val adapter by lazy { NotesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNotes()
        binding.createNote.setOnClickListener {
            findNavController().navigate(R.id.action_allNotesFragment_to_createNoteFragment)
        }

        setAdapter()
    }

    private fun getNotes() {
        viewModel.getNotes {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.notes.collect {
                        adapter.setData(it)
                        if (it.isEmpty()) {
                            binding.emptyTitle.visibility = View.VISIBLE
                            binding.emptyView.visibility = View.VISIBLE
                        } else {
                            binding.emptyTitle.visibility = View.GONE
                            binding.emptyView.visibility = View.GONE
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        adapter.onItemClick = { id ->
            findNavController().navigate(
                R.id.action_allNotesFragment_to_createNoteFragment,
                bundleOf(ID to id)
            )
        }
        adapter.onDeleteItemClick = { note ->
            viewModel.deleteNote(note)
            showSnackBar(
                view,
                requireContext(),
                getString(R.string.delete_snackbar_text),
                R.color.warning
            )
        }
    }
}
