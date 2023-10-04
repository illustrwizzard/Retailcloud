package com.example.retailcloudproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retailcloudproject.Retrofit.RetrofitClient
import com.example.retailcloudproject.Room.ImageItemDatabase
import com.example.retailcloudproject.Room.ImageRepository
import com.example.retailcloudproject.ViewModel.ImageViewModel
import com.example.retailcloudproject.ViewModel.ImageViewModelFactory



class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var toggleButton: ToggleButton
    private lateinit var imageAdapter: ImageAdapter



    private val imageViewModel: ImageViewModel by viewModels {
        val apiService = RetrofitClient.apiService
        val database = ImageItemDatabase.getInstance(this)
        val repository = ImageRepository(apiService, database.imageItemDao())
        ImageViewModelFactory(repository)
    }

    private var currentPage = 1
    private val pageSize = 30

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        toggleButton = findViewById(R.id.toggleButton)

        imageAdapter = ImageAdapter(false) // Default to list view
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = imageAdapter

        imageViewModel.imageItems.observe(this, Observer {
            imageAdapter.setImageItems(it)
        })

        imageViewModel.fetchImages(currentPage, pageSize)

        // Pagination: Load more data when reaching the end of the list
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    currentPage++
                    imageViewModel.fetchImages(currentPage, pageSize)
                }
            }
        })

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            // Toggle between grid and list view
            imageAdapter = ImageAdapter(isChecked)
            if (isChecked) {
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            } else {
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
            recyclerView.adapter = imageAdapter
        }
    }
}