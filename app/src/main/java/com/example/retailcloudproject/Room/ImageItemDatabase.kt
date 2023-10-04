package com.example.retailcloudproject.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageItem::class], version = 1)
abstract class ImageItemDatabase : RoomDatabase() {
    abstract fun imageItemDao(): ImageItemDao

    companion object {
        @Volatile
        private var INSTANCE: ImageItemDatabase? = null

        fun getInstance(context: Context): ImageItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImageItemDatabase::class.java,
                    "image_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}