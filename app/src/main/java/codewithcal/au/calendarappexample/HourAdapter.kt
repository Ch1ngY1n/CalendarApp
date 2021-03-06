package codewithcal.au.calendarappexample

import android.content.Context
import codewithcal.au.calendarappexample.CalendarUtils.formattedShortTime
import codewithcal.au.calendarappexample.HourEvent
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import codewithcal.au.calendarappexample.R
import android.widget.TextView
import codewithcal.au.calendarappexample.databinding.HourCellBinding
import java.time.LocalTime
import java.util.ArrayList

class HourAdapter(context: Context, hourEvents: List<HourEvent?>?) :
    ArrayAdapter<HourEvent?>(context, 0, hourEvents!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val event = getItem(position)
        if (convertView == null) convertView =
            LayoutInflater.from(context).inflate(R.layout.hour_cell, parent, false)
        setHour(convertView, event!!.time)
        setEvents(convertView, event.events)
        val TV_event1 = convertView!!.findViewById<TextView>(R.id.event1)
        val TV_event2 = convertView.findViewById<TextView>(R.id.event2)
        val TV_event3 = convertView.findViewById<TextView>(R.id.event3)
        TV_event1.setOnClickListener {
            event.events.removeAt(0)
            setEvents(convertView, event.events)
        }
        TV_event2.setOnClickListener {
            event.events.removeAt(1)
            setEvents(convertView, event.events)
        }
        TV_event3.setOnClickListener {
            event.events.removeAt(2)
            setEvents(convertView, event.events)
        }
        return convertView
    }

    private fun setHour(convertView: View?, time: LocalTime) {
        val timeTV = convertView!!.findViewById<TextView>(R.id.timeTV)
        timeTV.text = formattedShortTime(time)
    }

    private fun setEvents(convertView: View?, events: ArrayList<Event>) {
        val event1 = convertView!!.findViewById<TextView>(R.id.event1)
        val event2 = convertView.findViewById<TextView>(R.id.event2)
        val event3 = convertView.findViewById<TextView>(R.id.event3)
        val event4 = convertView.findViewById<TextView>(R.id.event4)
        if (events.size == 0) {
            hideEvent(event1)
            hideEvent(event2)
            hideEvent(event3)
            hideEvent(event4)
        } else if (events.size == 1) {
            setEvent(event1, events[0])
            hideEvent(event2)
            hideEvent(event3)
            hideEvent(event4)
        } else if (events.size == 2) {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            hideEvent(event3)
            hideEvent(event4)
        } else if (events.size == 3) {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            setEvent(event3, events[2])
            hideEvent(event4)
        } else {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            setEvent(event3, events[2])
            event4.visibility = View.VISIBLE
            var eventsNotShown = (events.size - 3).toString()
            event4.text = "+"+eventsNotShown
        }
    }

    private fun setEvent(textView: TextView, event: Event) {
        textView.text = event.name
        textView.visibility = View.VISIBLE
    }

    private fun hideEvent(tv: TextView) {
        tv.visibility = View.INVISIBLE
    }
}