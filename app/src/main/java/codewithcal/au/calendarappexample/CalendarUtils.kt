package codewithcal.au.calendarappexample

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object CalendarUtils {
    @JvmField
    var selectedDate: LocalDate? = null
    @JvmStatic
    fun formattedToday(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd")
        return date.format(formatter)
    }
    @JvmStatic
    fun formattedDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy年 MMMM dd日")
        return date.format(formatter)
    }

    @JvmStatic
    fun formattedTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return time.format(formatter)
    }

    @JvmStatic
    fun formattedShortTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return time.format(formatter)
    }

    @JvmStatic
    fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }
    @JvmStatic
    fun YearFromMonth(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy")
        return date.format(formatter)
    }

    @JvmStatic
    fun monthDayFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM d")
        return date.format(formatter)
    }

    @JvmStatic
    fun daysInMonthArray(): ArrayList<LocalDate> {
        val daysInMonthArray = ArrayList<LocalDate>()
        val yearMonth = YearMonth.from(selectedDate)
        val daysInMonth = yearMonth.lengthOfMonth()
        val prevMonth = selectedDate!!.minusMonths(1)
        val nextMonth = selectedDate!!.plusMonths(1)
        val prevYearMonth = YearMonth.from(prevMonth)
        val prevDaysInMonth = prevYearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek) daysInMonthArray.add(
                LocalDate.of(
                    prevMonth.year,
                    prevMonth.month,
                    prevDaysInMonth + i - dayOfWeek
                )

            ) else if (i > daysInMonth + dayOfWeek) daysInMonthArray.add(
                LocalDate.of(nextMonth.year, nextMonth.month, i - dayOfWeek - daysInMonth)
            ) else daysInMonthArray.add(
                LocalDate.of(
                    selectedDate!!.year, selectedDate!!.month, i - dayOfWeek
                )
            )
        }
        return daysInMonthArray
    }

    @JvmStatic
    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate> {
        val days = ArrayList<LocalDate>()
        var current = sundayForDate(selectedDate)
        val endDate = current!!.plusWeeks(1)
        while (current!!.isBefore(endDate)) {
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    private fun sundayForDate(current: LocalDate): LocalDate? {
        var current = current
        val oneWeekAgo = current.minusWeeks(1)
        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek == DayOfWeek.SUNDAY) return current
            current = current.minusDays(1)
        }
        return null
    }
}