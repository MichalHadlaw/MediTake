package com.example.meditake.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.meditake.ToTake

@Database(entities = [ToTake::class], version = 1)
@TypeConverters(Converters::class)
abstract class ToTakeDataBase : RoomDatabase(){

    companion object{
        const val NAME = "ToTake_DB"
    }

    abstract fun getToTakeDao() : ToTakeDAO

}