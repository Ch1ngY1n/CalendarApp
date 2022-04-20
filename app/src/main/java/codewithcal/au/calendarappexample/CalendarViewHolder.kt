package codewithcal.au.calendarappexample

import android.view.View
import codewithcal.au.calendarappexample.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import java.time.LocalDate
import java.util.ArrayList

class CalendarViewHolder internal constructor(
    itemView: View,
    onItemListener: OnItemListener,
    days: ArrayList<LocalDate>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val days: ArrayList<LocalDate>
    val parentView: View
    val dayOfMonth: TextView
    private val onItemListener: OnItemListener
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, days[adapterPosition])
    }

    init {
        parentView = itemView.findViewById(R.id.parentView)
        dayOfMonth = itemView.findViewById(R.id.cellDayText)

        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
        this.days = days
    }
}