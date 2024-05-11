package com.example.taskflowapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskflowapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var db: TaskDatabaseHelper

    private lateinit var tasksAdapter: TasksAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        tasksAdapter = TasksAdapter(db.getAllTasks(), this)

        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.taskRecyclerView.adapter = tasksAdapter

        binding.imageAdd.setOnClickListener {
            val intent1 = Intent(this, AddTaskActivity::class.java)
            startActivity(intent1)
        }
    }

    override fun onResume() {
        super.onResume()
        tasksAdapter.refreshTaskData(db.getAllTasks())
    }
}