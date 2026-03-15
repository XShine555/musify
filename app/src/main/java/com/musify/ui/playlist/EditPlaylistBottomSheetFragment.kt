package com.musify.ui.playlist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.musify.R
import java.io.File

class EditPlaylistBottomSheetFragment(
    private val onPlaylistUpdated: () -> Unit
) : BottomSheetDialogFragment() {
    private var playlistId: Int = 0
    private lateinit var playlistImageUrl: String
    private lateinit var playlistName: String

    private var selectedImageUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                val playlistImage = view?.findViewById<ShapeableImageView>(R.id.playlist_image)
                val radius = requireContext().resources.getDimensionPixelSize(R.dimen.radius_small)
                Glide.with(requireContext()).load(it).centerCrop()
                    .placeholder(R.drawable.img_playlist_placeholder)
                    .transform(RoundedCorners(radius)).into(playlistImage!!)
            }
        }

    private val viewModel: EditPlaylistBottomSheetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlistId = arguments?.getInt("playlistId") ?: -1
        playlistImageUrl = arguments?.getString("playlistImageUrl") ?: ""
        playlistName = arguments?.getString("playlistName") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_edit_playlist_layout, container, false)

        val playlistTitle = view.findViewById<EditText>(R.id.playlist_title_input)
        val playlistImage = view.findViewById<ShapeableImageView>(R.id.playlist_image)
        val saveButton = view.findViewById<Button>(R.id.save_button)

        playlistTitle.setText(playlistName)

        val context = requireContext()
        val radius = context.resources.getDimensionPixelSize(R.dimen.radius_small)
        Glide.with(context).load(playlistImageUrl).centerCrop()
            .placeholder(R.drawable.img_playlist_placeholder).transform(RoundedCorners(radius))
            .into(playlistImage)

        playlistImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            val errorMessage = errorMessage ?: return@observe
            Toast.makeText(
                requireContext(), errorMessage, Toast.LENGTH_SHORT
            ).show()
        }

        saveButton.setOnClickListener {
            viewModel.updatePlaylist(
                playlistId,
                playlistTitle.text.toString(),
                selectedImageUri?.let { uriToFile(it, requireContext()) },
            )
        }

        viewModel.updateResult.observe(viewLifecycleOwner) { isSuccess ->
            onPlaylistUpdated()
            dismiss()
        }

        return view
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("No se pudo abrir el URI")
        val tempFile = File.createTempFile("playlist_image", ".jpg", context.cacheDir)
        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }
}