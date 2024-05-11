package com.example.taskflowapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskflowapp.databinding.UpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity(){

    private lateinit var binding: UpdateTaskBinding
    private lateinit var db: TaskDatabaseHelper
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = UpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        taskId = intent.getIntExtra("task_id", -1)
        if(taskId == -1){
            finish()
            return
        }

        val task = db.getTaskByID(taskId)
        binding.textEditName.setText(task.title)
        binding.textEditDesc.setText(task.description)

        binding.saveEditBtn.setOnClickListener {
            val newName = binding.textEditName.text.toString()
            val newDesc = binding.textEditDesc.text.toString()
            val updatedTask = TaskData(taskId, newName, newDesc)
            db.updateTask(updatedTask)
            finish()
            Toast.makeText(this, "Changes have been saved", Toast.LENGTH_SHORT).show()
        }
    }
}