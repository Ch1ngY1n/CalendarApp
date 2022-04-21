package codewithcal.au.calendarappexample

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.time.LocalDate
import java.util.ArrayList

internal class CalendarAdapter(
    private val days: ArrayList<LocalDate>,
    private val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams

        val dailyEvents: ArrayList<Event> = Event.eventsForDate(
            CalendarUtils.selectedDate!!)
        setEvents(view, dailyEvents)

        if (days.size > 15) //month view
            layoutParams.height = (parent.height * 0.166666666).toInt() else  // week view
            layoutParams.height = parent.height
        return CalendarViewHolder(view, onItemListener, days)
    }


    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days[position]
        holder.dayOfMonth.text = date.dayOfMonth.toString()
        if (date == CalendarUtils.selectedDate) holder.parentView.setBackgroundResource(R.drawable.select_bk)
        if (date.month == CalendarUtils.selectedDate!!.month) holder.dayOfMonth.setTextColor(Color.WHITE) else holder.dayOfMonth.setTextColor(Color.GRAY)
        if (date.month != CalendarUtils.selectedDate!!.month) {
            holder.parentView.setBackgroundResource(R.color.deep_gray)
            holder.dayOfMonth.setText("")
        }

    }

    private fun setEvents(convertView: View?, events: ArrayList<Event>) {
        val event1 = convertView!!.findViewById<TextView>(R.id.cell_event1)
        val event2 = convertView.findViewById<TextView>(R.id.cell_event2)
        val event3 = convertView.findViewById<TextView>(R.id.cell_event3)
        if (events.size == 0) {
            hideEvent(event1)
            hideEvent(event2)
            hideEvent(event3)
        } else if (events.size == 1) {
            setEvent(event1, events[0])
            hideEvent(event2)
            hideEvent(event3)
        } else if (events.size == 2) {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            hideEvent(event3)
        } else if (events.size == 3) {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            setEvent(event3, events[2])
        } else {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            event3.visibility = View.VISIBLE
            var eventsNotShown = (events.size - 2).toString()
            eventsNotShown += " More Events"
            event3.text = eventsNotShown
        }
    }
    private fun setEvent(textView: TextView, event: Event) {
        textView.text = event.name
        textView.visibility = View.VISIBLE
    }

    private fun hideEvent(tv: TextView) {
        tv.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return days.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, date: LocalDate?)
    }
}