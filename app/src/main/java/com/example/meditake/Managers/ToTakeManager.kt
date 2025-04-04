package com.example.meditake.Managers

import com.example.meditake.db.ToTake
import java.time.Instant
import java.util.Date

object ToTakeManager {

    private val ToTakeList = mutableListOf<ToTake>()

    fun getAllToTake() : List<ToTake>{
        return ToTakeList
    }

    fun addToTake(
        title: String,
        dose: String,
        numberInPackage: Int ,
        dosageTime: String,mealVar: String?,
        routeOfAdministration: String
    ){
        ToTakeList.add(ToTake(
            System.currentTimeMillis().toInt(),
            title,
            dose,
            numberInPackage,
            dosageTime,
            mealVar,
            routeOfAdministration,
            Date.from(Instant.now())))
    }

    fun deleteToTake(id : Int){
        ToTakeList.removeIf{
            it.id == id
        }
    }

}