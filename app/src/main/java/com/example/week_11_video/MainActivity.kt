package com.example.week_11_video

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.week_11_video.databinding.ActivityMainBinding
import com.example.week_11_video.model.Item
import com.example.week_11_video.repository.DreamListDatabase

private val itemsAdapter = ItemsAdapter()
private var dataBase : DreamListDatabase ?= null

class MainActivity : AppCompatActivity() {
    companion object{
        private const val DATABASE_NAME = "DreamList-Database"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setUpAdapter(items)
            addItem.setOnClickListener {
                AddItemDialog.create {
                   addItem(it, dataBase!!)
                }.show(supportFragmentManager,"Add_Dialog")
            }
        }

        dataBase = Room.databaseBuilder(
            applicationContext,
            DreamListDatabase::class.java,
            DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    override fun onResume() {
        super.onResume()
        AsyncTask.execute {
            val items = dataBase?.dreamListDao()?.getAll()
            runOnUiThread {
                itemsAdapter.submitList(items)
                }
            }
    }

    private fun setUpAdapter(recyclerView: RecyclerView){
        recyclerView.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun addItem(item: Item, database: DreamListDatabase) = AsyncTask.execute{
        database.dreamListDao().add(item)
        val items = database.dreamListDao().getAll()
        itemsAdapter.submitList(items)
    }
}