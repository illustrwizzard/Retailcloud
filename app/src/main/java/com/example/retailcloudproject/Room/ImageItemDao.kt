package com.example.retailcloudproject.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageItemDao {
    @Query("SELECT * FROM image_items")
    fun getAllImageItems(): LiveData<List<ImageItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(imageItems: List<ImageItem>)

    @Delete
    fun deleteImageItem(imageItem: ImageItem)
}