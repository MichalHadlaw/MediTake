package com.example.meditake.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.meditake.ToTake

@Dao
interface ToTakeDAO {
@Query("SELECT * FROM TOTAKE")
    fun getAllToTake() : LiveData<List<ToTake>>
@Insert
    fun addToTake(take: ToTake)

@Query("Delete FROM ToTake where id = :id")
    fun deleteToTake(id : Int)


}