package com.example.meditake.Main

import android.app.Application
import androidx.room.Room
import com.example.meditake.db.ToTakeDataBase

class MainAplication : Application() {

    companion object{
        lateinit var  totakeDatabase: ToTakeDataBase
    }


    override fun onCreate(){
        super.onCreate()
      totakeDatabase =  Room.databaseBuilder(
            applicationContext,
            ToTakeDataBase::class.java,
            ToTakeDataBase.NAME
        ).build()
    }

}