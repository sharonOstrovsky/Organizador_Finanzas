package com.example.organizador

import android.app.Application
import com.example.organizador.data.network.OrganizadorRepository
import com.example.organizador.database.OrganizadorDataBase
import com.example.organizador.sharedPreferences.Prefs

class OrganizadorApplication: Application() {
    private val database by lazy { OrganizadorDataBase.getDatabase(this) }
    val repository by lazy { OrganizadorRepository(database.taskItemDao(), database.expenseItemDao()) }

    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }

}