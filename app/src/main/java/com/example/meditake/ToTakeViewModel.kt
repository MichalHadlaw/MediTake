package com.example.meditake


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meditake.Main.MainAplication
import com.example.meditake.db.ToTake

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

import java.time.Instant
import java.util.Date


class ToTakeViewModel : ViewModel(){


     private val ToTakeDAO = MainAplication.totakeDatabase.getToTakeDao()

    var drugNames by mutableStateOf(listOf<String>())


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
    fun increaseTookOnTimeForItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = ToTakeDAO.getById(id)
            item?.let {
                // Sprawdzenie, czy remainingDoses jest wystarczająco duże, żeby zmniejszyć o wartość dawki
                val newRemainingDoses = it.remainingDoses - it.dose.toInt()

                // Jeśli remainingDoses jest mniejsze od zera, nie pozwól na zmniejszenie
                if (newRemainingDoses >= 0) {
                    // Zaktualizuj tookOnTime oraz remainingDoses
                    val updatedItem = it.copy(
                        tookOnTime = it.tookOnTime + 1,
                        remainingDoses = newRemainingDoses
                    )
                    // Aktualizuj rekord w bazie danych
                    ToTakeDAO.updateToTake(updatedItem)
                }
            }
        }
    }
    fun increaseNotTakenForItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = ToTakeDAO.getById(id)
            item?.let {
                val updatedItem = it.copy(notTaken = it.notTaken + 1)
                ToTakeDAO.updateToTake(updatedItem)
            }
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

    fun resetRemainingDoses(id: Int, originalDoseCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = ToTakeDAO.getById(id)
            item?.let {
                val updatedItem = it.copy(remainingDoses = originalDoseCount)
                ToTakeDAO.updateToTake(updatedItem)
            }
        }
    }


    fun loadDrugNamesFromTXT(context: Context) {
        val loadedDrugNames = mutableListOf<String>()

        // Otwieramy plik w zasobach aplikacji
        val inputStream = context.assets.open("Nazwy_Lekow.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))

        // Czytamy plik linia po linii
        reader.use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                // Dodajemy każdą linię (nazwę leku) do listy
                line?.let {
                    loadedDrugNames.add(it)
                }
            }
        }

        // Przypisujemy załadowane nazwy leków do stanu
        drugNames = loadedDrugNames
    }

}






