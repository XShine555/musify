package com.musify.ui.create_playlist_modal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.musify.R

class CreatePlaylistModalFragment : BottomSheetDialogFragment() {
    var listener: OnPlaylistCreatedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_playlist_sheet, container, false)

        val editText = view.findViewById<EditText>(R.id.create_playlist_name)
        val createButton = view.findViewById<Button>(R.id.create_playlist_button)

        editText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                createButton.isEnabled = !s.isNullOrBlank()
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

        })

        createButton.setOnClickListener {
            val playlistName = editText.text.toString()
            listener?.onPlaylistCreated(playlistName)
            dismiss()
        }

        return view
    }
}