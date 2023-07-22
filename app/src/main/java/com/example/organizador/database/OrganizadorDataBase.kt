package com.example.organizador.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.organizador.TaskItemDao
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.model.TaskItem

@Database(entities = [TaskItem::class, ExpenseItem::class], version = 2, exportSchema = false)
abstract class OrganizadorDataBase: RoomDatabase() {

    abstract fun expenseItemDao(): ExpenseItemDao
    abstract fun taskItemDao(): TaskItemDao

    companion object
    {
        @Volatile
        private var INSTANCE: OrganizadorDataBase? = null

        fun getDatabase(context: Context): OrganizadorDataBase
        {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrganizadorDataBase::class.java,
                    "organizador_item_database"
                ).fallbackToDestructiveMigration() // Permite migraciones destructivas
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}