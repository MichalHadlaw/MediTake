package com.example.meditake.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToTake::class], version = 4)
@TypeConverters(Converters::class)
abstract class ToTakeDataBase : RoomDatabase(){

    companion object{
        private var INSTANCE: ToTakeDataBase? = null

        fun getDatabase(context: Context): ToTakeDataBase {
            Log.d("DatabaseInit", "Przed inicjalizacją INSTANCE: ${INSTANCE != null}")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToTakeDataBase::class.java,
                    NAME
                ).build()
                Log.d("DatabaseInit", "Po inicjalizacji INSTANCE: ${INSTANCE != null}")
                INSTANCE = instance
                instance
            }
        }


        const val NAME = "ToTake_DB"
    }

    abstract fun getToTakeDao() : ToTakeDAO

}