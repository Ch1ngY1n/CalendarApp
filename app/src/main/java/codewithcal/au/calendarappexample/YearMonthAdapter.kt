package codewithcal.au.calendarappexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class YearMonthAdapter (private val listMonths: ArrayList<String>)
    : RecyclerView.Adapter<YearMonthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearMonthViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.year_cell, parent, false)
        return YearMonthViewHolder(v)
    }

    override fun onBindViewHolder(holder: YearMonthViewHolder, position: Int) {
        holder.dataView.text = listMonths[position]+"月"

    }

    override fun getItemCount(): Int {
        return listMonths.size
    }
}