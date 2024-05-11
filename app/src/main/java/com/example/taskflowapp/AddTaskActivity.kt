package com.example.taskflowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskflowapp.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTaskBinding
    private lateinit var db: TaskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        binding.saveBtn.setOnClickListener {
            val title = binding.textName.text.toString()
            val content = binding .textDesc.text.toString()
            val taskData = TaskData(0, title,content)
            db.insertTask(taskData)
            finish()
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
        }
    }
}