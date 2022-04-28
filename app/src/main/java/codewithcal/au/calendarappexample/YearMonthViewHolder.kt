package codewithcal.au.calendarappexample

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class YearMonthViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    val dataView: TextView = view.findViewById(R.id.cellMonthText)
}