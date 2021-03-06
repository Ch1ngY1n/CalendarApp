package codewithcal.au.calendarappexample

import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class Event(var name: String, var date: LocalDate, var time: LocalTime,var remark:String,var noti:String) {

    companion object {
        @JvmField
        var eventsList = ArrayList<Event>()
        @JvmStatic
        fun eventsForDate(date: LocalDate): ArrayList<Event> {
            val events = ArrayList<Event>()
            for (event in eventsList) {
                if (event.date == date) events.add(event)
                events.sortBy { Event->Event.time }
            }
            return events
        }

        fun eventsForDateAndTime(date: LocalDate, time: LocalTime): ArrayList<Event> {
            val events = ArrayList<Event>()
            for (event in eventsList) {
                val eventHour = event.time.hour
                val cellHour = time.hour
                if (event.date == date && eventHour == cellHour) events.add(event)
                events.sortBy { Event->Event.time }
            }
            return events
        }
    }
}