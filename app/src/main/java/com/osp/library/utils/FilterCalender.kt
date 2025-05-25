package com.osp.library.utils

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.core.content.ContextCompat
import com.example.osp.R
import java.text.DateFormatSymbols
import java.util.Locale

internal class FilterCalender{
    companion object {
        const val INCREMENT = 1
        const val DECREMENT = -1
    }

    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat =
        SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()) //// Format the date as "dd-MMM-yyyy"

    internal fun customDatePickerDialog(context: Context,themeId:Int,onDateSelected: (String) -> Unit):DatePickerDialog {
       return DatePickerDialog(
            context,themeId, { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val formattedDate = dateFormat.format(calendar.time)
                onDateSelected(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    internal fun getDate(noOfDays: Int = 0): String {
        calendar.add(Calendar.DAY_OF_MONTH, -noOfDays)
        return dateFormat.format(calendar.time)
    }

    internal fun setDay(value: Int = 0, result: (text: String, from: String, to: String) -> Unit) {
        calendar.add(Calendar.DAY_OF_MONTH, value)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = DateFormatSymbols().shortMonths[calendar.get(Calendar.MONTH)]
        val year = calendar.get(Calendar.YEAR)
        result("$day-$month-$year", "$day-$month-$year", "$day-$month-$year")
    }

    internal fun setWeek(value: Int = 0, result: (text: String, from: String, to: String) -> Unit) {
        calendar.add(Calendar.WEEK_OF_MONTH, value)
        val newCalender = calendar.clone() as Calendar
        newCalender.set(Calendar.DAY_OF_WEEK, newCalender.firstDayOfWeek)
        val firstDay = newCalender.get(Calendar.DAY_OF_MONTH)
        val firstMonth = DateFormatSymbols().shortMonths[newCalender.get(Calendar.MONTH)]
        val firstYear = newCalender.get(Calendar.YEAR)
        newCalender.add(Calendar.DAY_OF_WEEK, 6)
        val lastDay = newCalender.get(Calendar.DAY_OF_MONTH)
        val lastMonth = DateFormatSymbols().shortMonths[newCalender.get(Calendar.MONTH)]
        val lastYear = newCalender.get(Calendar.YEAR)
        result(
            "$firstDay-$firstMonth-$firstYear to $lastDay-$lastMonth-$lastYear",
            "$firstDay-$firstMonth-$firstYear",
            "$lastDay-$lastMonth-$lastYear"
        )
    }

    internal fun setMonth(value: Int = 0, result: (text: String, from: String, to: String) -> Unit) {
        calendar.add(Calendar.MONTH, value)
        val month = DateFormatSymbols().shortMonths[calendar.get(Calendar.MONTH)]
        val maxDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val minDaysInMonth = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)
        result(
            "$month-$year",
            "$minDaysInMonth-$month-$year",
            "$maxDaysInMonth-$month-$year"
        )
    }

    internal fun setYear(value: Int = 0, result: (text: String, from: String, to: String) -> Unit) {
        calendar.add(Calendar.YEAR, value)
        val year = calendar.get(Calendar.YEAR)
        result(year.toString(), "1-Jan-$year", "31-Dec-$year")
    }
}
