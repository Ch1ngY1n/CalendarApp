package codewithcal.au.calendarappexample

import android.view.View
import codewithcal.au.calendarappexample.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import java.time.LocalDate
import kotlin.collections.ArrayList

class CalendarViewHolder internal constructor(
    itemView: View,
    onItemListener: OnItemListener,
    days: ArrayList<LocalDate>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener,View.OnLongClickListener {
    private val days: ArrayList<LocalDate>
    val parentView: View
    val dayOfMonth: TextView
    val event1:TextView
    val event2:TextView
    val event3:TextView
    val event4:TextView
    private val onItemListener: OnItemListener
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, days[adapterPosition])
    }

    init {
        parentView = itemView.findViewById(R.id.parentView)
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        event1 = itemView.findViewById(R.id.event1)
        event2 = itemView.findViewById(R.id.event2)
        event3 = itemView.findViewById(R.id.event3)
        event4 = itemView.findViewById(R.id.event4)


        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
        this.days = days
    }

    override fun onLongClick(view: View?): Boolean {
        onItemListener.OnItemLongClick(adapterPosition,days[adapterPosition])
        return true
    }


}