package com.example.meditake.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date
@Entity
data class ToTake(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title : String,
    var dose : String,
    var numberInPackage: Int,
    var dosageTime: String,
    var mealVar: String?,
    var routeOfAdministration: String,
    var createdAt : Date,
    var isPrescription: Boolean = false,
    var isChecked: Boolean = false


)


