package codewithcal .au.calendarappexample

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import codewithcal.au.calendarappexample.CalendarUtils
import codewithcal.au.calendarappexample.CalendarViewHolder
import codewithcal.au.calendarappexample.R
import java.time.LocalDate

class CalendarAdapter(
    private var myDates: ArrayList<LocalDate>,
    private val onItemListener: OnItemListener,
) : RecyclerView.Adapter<CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams

        if (myDates.size > 15) //month view
            layoutParams.height = (parent.height * 0.166666666).toInt() else  // week view
            layoutParams.height = parent.height
        return CalendarViewHolder(view, onItemListener, myDates)

    }



    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val myDate = myDates[position]
        //設定日期
        holder.dayOfMonth.text = myDate.dayOfMonth.toString()
        //設定背景
        if (myDate == CalendarUtils.selectedDate) holder.parentView.setBackgroundResource(R.drawable.select_bk)
        if (myDate.month == CalendarUtils.selectedDate!!.month) holder.dayOfMonth.setTextColor(Color.WHITE) else holder.dayOfMonth.setTextColor(Color.GRAY)
        if (myDate.month != CalendarUtils.selectedDate!!.month) {
            holder.parentView.setBackgroundResource(R.color.deep_gray)
            holder.dayOfMonth.setText("")
        }
        if (position == 0) holder.dayOfMonth.setTextColor(Color.RED)
        if (position == 7) holder.dayOfMonth.setTextColor(Color.RED)
        if (position == 14) holder.dayOfMonth.setTextColor(Color.RED)
        if (position == 21) holder.dayOfMonth.setTextColor(Color.RED)
        if (position == 28) holder.dayOfMonth.setTextColor(Color.RED)
        if (position == 35) holder.dayOfMonth.setTextColor(Color.RED)

        if (position == 6) holder.dayOfMonth.setTextColor(Color.YELLOW)
        if (position == 13) holder.dayOfMonth.setTextColor(Color.YELLOW)
        if (position == 20) holder.dayOfMonth.setTextColor(Color.YELLOW)
        if (position == 27) holder.dayOfMonth.setTextColor(Color.YELLOW)
        if (position == 34) holder.dayOfMonth.setTextColor(Color.YELLOW)
        if (position == 41) holder.dayOfMonth.setTextColor(Color.YELLOW)

    }


    override fun getItemCount(): Int {
        return myDates.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, date: LocalDate)
        fun OnItemLongClick(position: Int, date: LocalDate)
    }
}