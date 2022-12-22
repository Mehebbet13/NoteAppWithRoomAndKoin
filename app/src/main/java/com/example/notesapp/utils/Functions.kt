package com.example.notesapp.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.notesapp.R
import com.example.notesapp.presentation.allnotes.view.AllNotesFragment
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(view: View?, context: Context, snackBarText: String, color: Int) {
    view?.let { it1 ->
        val snackbar = Snackbar.make(it1, snackBarText, Snackbar.LENGTH_SHORT)
        val sbView = snackbar.view
        sbView.apply {
            val params: ViewGroup.MarginLayoutParams =
                sbView.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(
                MARGIN,
                MARGIN,
                MARGIN,
                MARGIN
            )
            sbView.layoutParams = params

            snackbar.setTextColor(ContextCompat.getColor(context, R.color.black))
            background =
                ContextCompat.getDrawable(context, R.drawable.roundy_background)
            setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    color
                )
            )
        }
        snackbar.show()
    }
}

const val MARGIN = 0
const val ID = "id"
