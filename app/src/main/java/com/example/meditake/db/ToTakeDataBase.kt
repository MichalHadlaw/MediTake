package com.example.meditake.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToTake::class], version = 4)
@TypeConverters(Converters::class)
abstract class ToTakeDataBase : RoomDatabase(){

    companion object{
        const val NAME = "ToTake_DB"
    }

    abstract fun getToTakeDao() : ToTakeDAO

}