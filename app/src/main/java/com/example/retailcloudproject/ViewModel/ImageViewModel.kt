package com.example.retailcloudproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retailcloudproject.Room.ImageItem
import com.example.retailcloudproject.Room.ImageRepository
import kotlinx.coroutines.launch
class ImageViewModel(private val repository: ImageRepository) : ViewModel() {

    private val _imageItems = MutableLiveData<List<ImageItem>?>() // Use MutableLiveData
    val imageItems: MutableLiveData<List<ImageItem>?> get() = _imageItems

    private val _isGridView = MutableLiveData<Boolean>()
    val isGridView: LiveData<Boolean> get() = _isGridView

    init {
        _isGridView.value = false // Default to ListView
    }

    fun fetchImages(page: Int, limit: Int) {
        viewModelScope.launch {
            val images = repository.fetchImages(page, limit)
            images?.let {
                _imageItems.postValue(images) // Use postValue to update MutableLiveData
            }
        }
    }

    fun getAllImages() {
        viewModelScope.launch {
            val images = repository.getAllImages().value // Get the LiveData value
            _imageItems.value = images // Update MutableLiveData
        }
    }

    fun deleteImage(imageItem: ImageItem) {
        viewModelScope.launch {
            repository.deleteImage(imageItem)
            getAllImages() // Refresh the data after deletion
        }
    }

    fun toggleView() {
        _isGridView.value = !_isGridView.value!!
    }
}
