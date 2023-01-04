package com.example.amazingtalkerhomework.misc.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val DATE_PATTERN_1 = "yyyy-MM-dd"
    private const val DATE_PATTERN_2 = "HH:mm"
    private const val STANDARD_PATTERN = "$DATE_PATTERN_1'T'HH:mm:ss'Z'"

    private val STANDARD_FORMAT = SimpleDateFormat(STANDARD_PATTERN, Locale.US)

    fun getDayByFormat(offset: Int = 0, dateFormat: SimpleDateFormat): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        if (offset != 0) {
            calendar.add(Calendar.DATE, offset)
        }

        return dateFormat.format(calendar.time)
    }

    fun getDayOfWeekString(offset: Int = 0): String {
        val dateFormat = SimpleDateFormat("EEE, MM/dd", Locale.getDefault())
        return getDayByFormat(offset, dateFormat)
    }

    fun isBeforeToday(offset: Int): Boolean {
        val currentDate = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        if (offset != 0) {
            calendar.add(Calendar.DATE, offset)
        }
        return calendar.before(currentDate)
    }

    fun getTodayOfWeekString(): String {
        val dateFormat = SimpleDateFormat("EEE, MM/dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        return dateFormat.format(calendar.time)
    }

    fun getDateString(offset: Int = 0): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return getDayByFormat(offset, dateFormat)
    }

    fun getDisplayTimezone(): String {
        val timezone: TimeZone = TimeZone.getDefault()
        return timezone.id.toString() + ": " + timezone.getDisplayName(false, TimeZone.SHORT)
    }

    fun getFullTimeString(offset: Int = 0): String {
        val dateFormat = STANDARD_FORMAT
        return getDayByFormat(offset, dateFormat)
    }

    fun convertTimezone(dateStr: String): String {
        val dateFormat = STANDARD_FORMAT
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val parsed = dateFormat.parse(dateStr)
        dateFormat.timeZone = TimeZone.getDefault()

        return dateFormat.format(parsed)
    }

    fun addHalfHour(dateString: String, minutes: Int = 30): String {
        val dateFormat = STANDARD_FORMAT
        val date = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MINUTE, minutes)
        return dateFormat.format(calendar.time)
    }

    fun compareTwoDate(start: String, end: String): Boolean {
        val dateFormat = STANDARD_FORMAT
        val startDate = dateFormat.parse(start)
        val startCalendar = Calendar.getInstance()
        startCalendar.time = startDate

        val endDate = dateFormat.parse(end)
        val endCalendar = Calendar.getInstance()
        endCalendar.time = endDate

        return startCalendar.before(endCalendar)
    }

    fun getHourAndMinutes(dateStr: String): String {
        val dateFormat = STANDARD_FORMAT
        val date = dateFormat.parse(dateStr)
        return SimpleDateFormat(DATE_PATTERN_2, Locale.getDefault()).format(date).toString()
    }

    fun getDate(dateStr: String): String {
        val dateFormat = STANDARD_FORMAT
        val date = dateFormat.parse(dateStr)
        return SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault()).format(date).toString()
    }
}