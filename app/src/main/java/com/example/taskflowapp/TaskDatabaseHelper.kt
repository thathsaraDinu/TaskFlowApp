package com.example.taskflowapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "taskFlow.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allTasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESC = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESC TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertTask(taskData: TaskData){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, taskData.title)
            put(COLUMN_DESC, taskData.description)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTasks(): List<TaskData>{
        val tasksList = mutableListOf<TaskData>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val theCursor = db.rawQuery(query, null)

        while(theCursor.moveToNext()){
            val id = theCursor.getInt(theCursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = theCursor.getString(theCursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = theCursor.getString((theCursor.getColumnIndexOrThrow(COLUMN_DESC)))

            val task = TaskData(id, title, content)
            tasksList.add(task)
        }
        theCursor.close()
        db.close()
        for (task in tasksList) {
            Log.d("TaskData", "ID: ${task.id}, Title: ${task.title}, Description: ${task.description}")
        }
        return tasksList
    }

    fun updateTask(taskData: TaskData){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, taskData.title)
            put(COLUMN_DESC, taskData.description)

        }
        val findClause = "$COLUMN_ID = ?"
        val findArgs = arrayOf(taskData.id.toString())
        db.update(TABLE_NAME, values, findClause, findArgs)
        db.close()
    }

    fun getTaskByID(taskId: Int): TaskData{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskId"
        val theCursor = db.rawQuery(query, null)
        theCursor.moveToFirst()

        val id = theCursor.getInt(theCursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = theCursor.getString(theCursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = theCursor.getString((theCursor.getColumnIndexOrThrow(COLUMN_DESC)))

        theCursor.close()
        db.close()
        return TaskData(id, title, content)
    }
}