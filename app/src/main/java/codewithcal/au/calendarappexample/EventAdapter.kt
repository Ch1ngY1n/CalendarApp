package codewithcal.au.calendarappexample

import android.content.Context
import codewithcal.au.calendarappexample.CalendarUtils.formattedTime
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class EventAdapter(context: Context, events: List<Event?>?) :
    ArrayAdapter<Event?>(context, 0, events!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val event = getItem(position)
        if (convertView == null) convertView =
            LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)
        val eventCellTV0 = convertView!!.findViewById<TextView>(R.id.eventCellTV0)
        val eventCellTV1 = convertView.findViewById<TextView>(R.id.eventCellTV1)
        val eventCellTV2 = convertView.findViewById<TextView>(R.id.eventCellTV2)
        val eventCellTV3 = convertView.findViewById<TextView>(R.id.eventCellTV3)
        val eventTitle = event!!.name
        val eventTime = formattedTime(event.time)
        val eventRemark = event.remark
        val eventNoti = event.noti.substring(3,4)
        eventCellTV0.text = eventNoti
        eventCellTV1.text = eventTitle
        eventCellTV2.text = eventTime
        eventCellTV3.text = eventRemark
        return convertView
    }
}