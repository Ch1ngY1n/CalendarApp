package codewithcal.au.calendarappexample

import codewithcal.au.calendarappexample.CalendarUtils.monthDayFromDate
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import codewithcal.au.calendarappexample.R
import codewithcal.au.calendarappexample.HourAdapter
import codewithcal.au.calendarappexample.HourEvent
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import codewithcal.au.calendarappexample.CalendarUtils.selectedDate
import codewithcal.au.calendarappexample.EventEditActivity
import codewithcal.au.calendarappexample.databinding.ActivityDailyCalendarBinding
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*

class DailyCalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyCalendarBinding
    private  var currentDay = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDailyCalendarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dayOfToday()
        onclickMenu()
        addEvent()
    }

    override fun onResume() {
        super.onResume()
        setDayView()
    }
    private fun dayOfToday(){
        //今天日期
        binding.navDaily.ucNavLayoutRight.text = currentDay.get(Calendar.DAY_OF_MONTH).toString()

        //回到今天日期
        binding.navDaily.ucNavLayoutRight.setOnClickListener {
            //今天的時間
            selectedDate = LocalDate.now()
            //設定日曆(月)
            setDayView()
        }
    }
    private fun onclickMenu(){
        //側邊選單
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout_daily)
        val imgMenu = findViewById<ImageView>(R.id.uc_img_nav_left)
        val navView = findViewById<NavigationView>(R.id.nav_drawer)
        navView.itemIconTintList = null
        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //選單點擊週
        binding.navDrawer.BTNMainWeek.setOnClickListener {
            drawerLayout.closeDrawers()
            startActivity(Intent(this,WeekViewActivity::class.java))
        }
        //選單點擊月
        binding.navDrawer.BTNMainMonth.setOnClickListener {
            drawerLayout.closeDrawers()
            startActivity(Intent(this,MainActivity::class.java))
        }
        //選單點擊日
        binding.navDrawer.BTNMainDaily.setOnClickListener {
            drawerLayout.closeDrawers()
        }
    }
    private fun addEvent(){
        binding.eventAddMain.eventAddButton.setOnClickListener {
            startActivity(Intent(this, EventEditActivity::class.java))
        }
    }

    private fun setDayView() {
        binding.monthDayText.text =
            selectedDate?.let { monthDayFromDate(it) }
        val dayOfWeek: String =
            selectedDate!!.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())
        binding.dayOfWeekTV.text = dayOfWeek
        setHourAdapter()
    }

    private fun setHourAdapter() {
        val hourAdapter = HourAdapter(applicationContext, hourEventList())
        binding.hourListView.adapter = hourAdapter
    }
    //0:00-23:00 一整天
    private fun hourEventList(): ArrayList<HourEvent> {
        val list = ArrayList<HourEvent>()
        for (hour in 0..23) {
            val time = LocalTime.of(hour, 0)
            val events = selectedDate?.let { Event.eventsForDateAndTime(it, time) }
            val hourEvent = events?.let { HourEvent(time, it) }
            if (hourEvent != null) {
                list.add(hourEvent)
            }
        }
        return list
    }

    fun previousDayAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusDays(1)
        setDayView()
    }

    fun nextDayAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusDays(1)
        setDayView()
    }

    fun newEventAction(view: View?) {
        startActivity(Intent(this, EventEditActivity::class.java))
    }
}