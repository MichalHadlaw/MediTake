package com.example.meditake

import android.icu.text.CaseMap.Title
import java.time.Instant
import java.util.Date

object ToTakeManager {

    private val ToTakeList = mutableListOf<ToTake>()


    fun getAllToTake() : List<ToTake>{
        return ToTakeList
    }

    fun addToTake(title: String){
        ToTakeList.add(ToTake(System.currentTimeMillis().toInt(),title, Date.from(Instant.now())))
    }

    fun deleteToTake(id : Int){
        ToTakeList.removeIf{
            it.id == id
        }
    }
}