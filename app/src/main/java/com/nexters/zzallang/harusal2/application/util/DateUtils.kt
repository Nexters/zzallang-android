package com.nexters.zzallang.harusal2.application.util

import java.util.*

object DateUtils {
    fun getLastDayOfMonth(): Int {
        return getLastDayOfMonthAfter(0)
    }

    fun getLastDayOfMonthAfter(month: Int): Int {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, month)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getDay(): Int {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun getMonth(): Int {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MONTH)
    }

    fun getTodayDate(): Date{
        return Date(System.currentTimeMillis())
    }

    fun endDate(date: Date): Date{
        val newDate = Date()
        val argsDate = date.date
        newDate.month = when (argsDate) {
            1 -> argsDate
            else -> argsDate + 1
        }

        newDate.date = when (argsDate) {
            1 -> this.getLastDayOfMonth()
            else -> argsDate
        }
        return date
    }
}