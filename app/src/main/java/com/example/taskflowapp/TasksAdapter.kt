package com.example.taskflowapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private var tasks: List<TaskData>, context: Context) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardName: TextView = itemView.findViewById(R.id.cardName)
        val cardDesc: TextView = itemView.findViewById(R.id.cardDesc)
        val updateButton: Button = itemView.findViewById(R.id.editBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksAdapter.TaskViewHolder {
        val theView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(theView)
    }

    override fun onBindViewHolder( holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.cardName.text = task.title
        holder.cardDesc.text = task.description
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java)
            intent.putExtra("task_id", task.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() : Int = tasks.size

    @SuppressLint("NotifyDataSetChanged")
    fun refreshTaskData(newTasks: List<TaskData>){
        tasks = newTasks
        notifyDataSetChanged()
    }

}