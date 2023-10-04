package com.example.retailcloudproject.Room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "image_items")
data class ImageItem(
    @PrimaryKey val id: String,
    val author: String,
    val download_url: String
)