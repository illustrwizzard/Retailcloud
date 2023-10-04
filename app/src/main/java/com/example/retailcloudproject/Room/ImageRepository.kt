package com.example.retailcloudproject.Room

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.retailcloudproject.Retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageRepository(private val apiService: ApiService, private val imageItemDao: ImageItemDao) {

    suspend fun fetchImages(page: Int, limit: Int): List<ImageItem>? {
        return try {
            val response = apiService.getImages(page, limit)
            if (response.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    imageItemDao.insertAll(response)
                }
            }
            response
        } catch (e: Exception) {
            // Handle network or API errors here
            null
        }
    }

    fun getAllImages(): LiveData<List<ImageItem>> {
        return imageItemDao.getAllImageItems()
    }

    suspend fun deleteImage(imageItem: ImageItem) {
        withContext(Dispatchers.IO) {
            imageItemDao.deleteImageItem(imageItem)
        }
    }
}