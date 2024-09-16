package com.example.meditake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meditake.db.ToTakeDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class ToTakeViewModel : ViewModel(){


        val ToTakeDAO = MainAplication.totakeDatabase.getToTakeDao()

    val toTakeList : LiveData<List<ToTake>> = ToTakeDAO.getAllToTake()



    fun addToTake(title : String){

        viewModelScope.launch(Dispatchers.IO ){
            ToTakeDAO.addToTake(ToTake(title = title, createdAt = Date.from(Instant.now())))
        }


    }

    fun deleteToTake(id: Int){
        viewModelScope.launch(Dispatchers.IO ){
            ToTakeDAO.deleteToTake(id)
        }

    }


}