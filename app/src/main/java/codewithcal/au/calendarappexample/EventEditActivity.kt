package codewithcal.au.calendarappexample

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import codewithcal.au.calendarappexample.CalendarUtils.formattedDate
import codewithcal.au.calendarappexample.CalendarUtils.formattedTime
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import codewithcal.au.calendarappexample.databinding.ActivityEventEditBinding
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class EventEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventEditBinding
    private var time: LocalTime? = null
    var cal = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEventEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        time = LocalTime.now()
        binding.eventDateTV.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK,{ _, year, month, day->
                c.set(Calendar.YEAR,year)
                c.set(Calendar.MONTH,month)
                c.set(Calendar.DAY_OF_MONTH,day)
                binding.eventDateTV.setText("日期 : "+year+"年 "+ (month+1) +"月"+" "+day+"日")
                CalendarUtils.selectedDate = c.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            },year,month,day).show()
        }
        binding.eventDateTV.text = "日期 : " + formattedDate(
            CalendarUtils.selectedDate!!
        )
        binding.eventTimeTV.text = "時間 : " + formattedTime(time!!)

    }



    fun saveEventAction(view: View?) {
        var eventName : String
        var remark : String
        if (binding.eventNameET.text.isEmpty()){
            binding.eventNameET.setText("我的活動")
            eventName = binding.eventNameET.text.toString()
            remark = binding.eventRemarkET.text.toString()
            val newEvent = Event(eventName, CalendarUtils.selectedDate!!, time!!,remark)
            Event.eventsList.add(newEvent)
            finish()
        } else{
            eventName = binding.eventNameET.text.toString()
            remark = binding.eventRemarkET.text.toString()
            val newEvent = Event(eventName, CalendarUtils.selectedDate!!, time!!,remark)
            Event.eventsList.add(newEvent)
            finish()
        }

    }

    fun exitEventAction(view: View) {
        finish()
    }
}