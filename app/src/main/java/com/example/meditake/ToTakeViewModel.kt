package com.example.meditake

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meditake.Main.MainAplication
import com.example.meditake.db.ToTake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date


class ToTakeViewModel : ViewModel(){


     private val ToTakeDAO = MainAplication.totakeDatabase.getToTakeDao()


    val toTakeList : LiveData<List<ToTake>> = ToTakeDAO.getAllToTake()


    fun addToTake(
        title: String,
        dose: String,
        numberInPackage: Int,
        dosageTime: String,
        mealVar: String?,
        routeOfAdministration: String,
        isPrescription: Boolean

        ){

        viewModelScope.launch(Dispatchers.IO ){
            ToTakeDAO.addToTake(
                ToTake(
                title = title,
                dose = dose,
                numberInPackage = numberInPackage,
                dosageTime = dosageTime,
                mealVar = mealVar,
                routeOfAdministration = routeOfAdministration,
                    isPrescription = isPrescription,
                createdAt = Date.from(Instant.now())))

        }


    }

    fun deleteToTake(id: Int){
        viewModelScope.launch(Dispatchers.IO ){
            ToTakeDAO.deleteToTake(id)
        }

    }

    fun updateCheckBoxState(itemId: Int, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = ToTakeDAO.getById(itemId) // Stwórz odpowiednie zapytanie w DAO
            item?.let {
                val updatedItem = it.copy(isChecked = isChecked)
                ToTakeDAO.updateToTake(updatedItem) // Dodaj metodę `updateToTake` w DAO
            }
        }
    }
}


