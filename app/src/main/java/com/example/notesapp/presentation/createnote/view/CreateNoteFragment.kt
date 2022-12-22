package com.example.notesapp.presentation.createnote.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentCreateNoteBinding
import com.example.notesapp.domain.model.Note
import com.example.notesapp.presentation.createnote.viewmodel.CreateNoteViewModel
import com.example.notesapp.utils.ID
import com.example.notesapp.utils.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateNoteFragment : Fragment() {

    private val viewModel by viewModel<CreateNoteViewModel>()
    private lateinit var binding: FragmentCreateNoteBinding

    var id: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ID)?.let { viewModel.getNote(it) }
        setEditText()
        setListeners()
    }

    private fun setEditText() {
        viewModel.note.observe(viewLifecycleOwner) {
            binding.noteTitle.text.append(it.title)
            binding.noteContent.text.append(it.body)
            id = it.id
        }
    }

    private fun setListeners() {
        binding.saveNote.setOnClickListener {
            if (binding.noteTitle.text.toString().isNotEmpty()) {
                viewModel.addNote(
                    Note(
                        binding.noteTitle.text.toString(),
                        binding.noteContent.text.toString(),
                        id
                    )
                ) {
                    findNavController().popBackStack()
                }
            } else {
                showSnackBar(
                    view,
                    requireContext(),
                    getString(R.string.save_snackbar_text),
                    R.color.warning
                )
            }
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
            ActivityNavigator.applyPopAnimationsToPendingTransition(requireActivity())
        }
    }
}
