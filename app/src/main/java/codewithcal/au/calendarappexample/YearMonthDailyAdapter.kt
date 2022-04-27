package codewithcal.au.calendarappexample

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class YearMonthDailyAdapter(
    private var myDates: ArrayList<LocalDate>,
    private val onItemListener: OnItemListener,
) : RecyclerView.Adapter<YearMonthDailyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearMonthDailyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.year_month_daily_cell, parent, false)
        val layoutParams = view.layoutParams

        if (myDates.size > 15) //month view
            layoutParams.height = (parent.height * 0.166666666).toInt() else  // week view
            layoutParams.height = parent.height
        return YearMonthDailyViewHolder(view, onItemListener, myDates)

    }
    override fun onBindViewHolder(holder: YearMonthDailyViewHolder, position: Int) {
        val myDate = myDates[position]
        //設定日期
        holder.dayOfMonth.text = myDate.dayOfMonth.toString()
        //設定背景
        if (myDate == CalendarUtils.selectedDate) holder.parentView.setBackgroundResource(R.drawable.select_bk)
        if (myDate.month == CalendarUtils.selectedDate!!.month) holder.dayOfMonth.setTextColor(Color.WHITE) else holder.dayOfMonth.setTextColor(
            Color.GRAY)
        if (myDate.month != CalendarUtils.selectedDate!!.month) {
            holder.parentView.setBackgroundResource(R.color.deep_gray)
            holder.dayOfMonth.setText("")
            holder.event1.visibility = View.INVISIBLE
            holder.event2.visibility = View.INVISIBLE
            holder.event3.visibility = View.INVISIBLE
            holder.event4.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return myDates.size
    }
    interface OnItemListener {
         fun onItemClick(position: Int, date: LocalDate)
         fun OnItemLongClick(position: Int, date: LocalDate)
    }
}