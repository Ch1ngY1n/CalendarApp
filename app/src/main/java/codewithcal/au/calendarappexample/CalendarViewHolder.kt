package codewithcal.au.calendarappexample

import android.app.AlertDialog
import android.view.View
import codewithcal.au.calendarappexample.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate
import java.util.ArrayList

class CalendarViewHolder internal constructor(
    itemView: View,
    onItemListener: OnItemListener,
    days: ArrayList<LocalDate>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener,View.OnLongClickListener {
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
        itemView.setOnLongClickListener(this)
        this.days = days
    }

    override fun onLongClick(view: View?): Boolean {
        onItemListener.OnItemLongClick(adapterPosition,days[adapterPosition])
        return true
    }


}