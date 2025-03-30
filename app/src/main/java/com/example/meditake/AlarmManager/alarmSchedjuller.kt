package com.example.meditake.AlarmManager

interface alarmSchedjuller {
    fun schedjule(item: AlarmItem)
    fun cancle(item: AlarmItem)
}