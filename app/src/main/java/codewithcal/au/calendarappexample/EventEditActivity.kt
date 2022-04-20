package codewithcal.au.calendarappexample

import codewithcal.au.calendarappexample.CalendarUtils.formattedDate
import codewithcal.au.calendarappexample.CalendarUtils.formattedTime
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import android.view.View
import codewithcal.au.calendarappexample.R
import codewithcal.au.calendarappexample.databinding.ActivityEventEditBinding
import java.time.LocalTime

class EventEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventEditBinding
    private var time: LocalTime? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEventEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        time = LocalTime.now()
        binding.eventDateTV.text = "日期 : " + formattedDate(
            CalendarUtils.selectedDate!!
        )
        binding.eventTimeTV.text = "時間 : " + formattedTime(time!!)
    }



    fun saveEventAction(view: View?) {
        var eventName : String
        if (binding.eventNameET.text.isEmpty()){
            binding.eventNameET.setText("我的活動")
            eventName = binding.eventNameET.text.toString()
            val newEvent = Event(eventName, CalendarUtils.selectedDate!!, time!!)
            Event.eventsList.add(newEvent)
            finish()
        } else{
            eventName = binding.eventNameET.text.toString()
            val newEvent = Event(eventName, CalendarUtils.selectedDate!!, time!!)
            Event.eventsList.add(newEvent)
            finish()
        }

    }

    fun exitEventAction(view: View) {
        finish()
    }
}