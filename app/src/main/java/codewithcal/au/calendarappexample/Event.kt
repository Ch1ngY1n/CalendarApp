package codewithcal.au.calendarappexample

import java.time.LocalDate
import java.time.LocalTime
import java.util.ArrayList

class Event(var name: String, var date: LocalDate, var time: LocalTime) {

    companion object {
        @JvmField
        var eventsList = ArrayList<Event>()
        @JvmStatic
        fun eventsForDate(date: LocalDate): ArrayList<Event?> {
            val events = ArrayList<Event?>()
            for (event in eventsList) {
                if (event.date == date) events.add(event)
            }
            return events
        }

        fun eventsForDateAndTime(date: LocalDate, time: LocalTime): ArrayList<Event> {
            val events = ArrayList<Event>()
            for (event in eventsList) {
                val eventHour = event.time.hour
                val cellHour = time.hour
                if (event.date == date && eventHour == cellHour) events.add(event)
            }
            return events
        }
    }
}