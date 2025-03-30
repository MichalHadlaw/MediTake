package com.example.meditake.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToTakeDAO {
@Query("SELECT * FROM TOTAKE")
    fun getAllToTake() : LiveData<List<ToTake>>
@Insert
    fun addToTake(take: ToTake)

@Query("Delete FROM ToTake where id = :id")
    fun deleteToTake(id : Int)

    @Query("SELECT * FROM ToTake WHERE id = :id")
    fun getById(id: Int): ToTake?
    @Update
    fun updateToTake(toTake: ToTake)

    @Query("SELECT * FROM ToTake")
    suspend fun getAllToTakeDirect(): List<ToTake>

    @Query("SELECT * FROM ToTake WHERE id = :id")
    fun getToTakeById(id: Int): ToTake?

}