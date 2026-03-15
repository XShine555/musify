package com.musify.ui.playlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musify.api.Api
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditPlaylistBottomSheetViewModel : ViewModel() {
    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> = _updateResult

    private val _errorMessage = MutableLiveData<String?>(null)

    val errorMessage: LiveData<String?> = _errorMessage

    fun updatePlaylist(playlistId: Int, title: String, imageFile: File?) {
        viewModelScope.launch {
            try {
                val titlePart = title.toRequestBody("text/plain".toMediaType())
                val imagePart = imageFile?.let {
                    val reqFile = it.asRequestBody("image/*".toMediaType())
                    MultipartBody.Part.createFormData("image", it.name, reqFile)
                }

                val response = Api.getPlaylistService().updatePlaylist(
                    playlistId = playlistId, newTitle = titlePart, image = imagePart
                )

                if (!response.isSuccessful) {
                    _errorMessage.value = "Failed to update playlist. Please try again."
                }
                _updateResult.value = response.isSuccessful
            } catch (e: Exception) {
                Log.e("EditPlaylistViewModel", "Error updating playlist", e)
                _errorMessage.value = "An error occurred. Please try again."
            }
        }
    }
}