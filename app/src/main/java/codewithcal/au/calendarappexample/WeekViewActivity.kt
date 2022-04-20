package codewithcal.au.calendarappexample

import codewithcal.au.calendarappexample.CalendarUtils.monthYearFromDate
import codewithcal.au.calendarappexample.CalendarUtils.daysInWeekArray
import codewithcal.au.calendarappexample.Event.Companion.eventsForDate
import androidx.appcompat.app.AppCompatActivity
import codewithcal.au.calendarappexample.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import codewithcal.au.calendarappexample.databinding.ActivityWeekViewBinding
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.util.*

class WeekViewActivity : AppCompatActivity(), OnItemListener {
    private lateinit var binding: ActivityWeekViewBinding
    private  var currentDay = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWeekViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //設定日曆(週)
        setWeekView()
        dayOfToday()
        onclickMenu()
    }
    private fun dayOfToday(){
        //今天日期
        binding.navWeek.ucNavLayoutRight.text = currentDay.get(Calendar.DAY_OF_MONTH).toString()

        //回到今天日期
        binding.navWeek.ucNavLayoutRight.setOnClickListener {
            //今天的時間
            CalendarUtils.selectedDate = LocalDate.now()
            //設定日曆(週)
            setWeekView()
        }
    }
    private fun onclickMenu(){
        //側邊選單
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout_week)
        val imgMenu = findViewById<ImageView>(R.id.uc_img_nav_left)
        val navView = findViewById<NavigationView>(R.id.nav_drawer)
        navView.itemIconTintList = null
        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //選單點擊(週)
        binding.navDrawer.BTNMainWeek.setOnClickListener {
            drawerLayout.closeDrawers()
        }
        //選單點擊(月)
        binding.navDrawer.BTNMainMonth.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            drawerLayout.closeDrawers()
        }
        //選單點擊(日)
        binding.navDrawer.BTNMainDaily.setOnClickListener {
            startActivity(Intent(this, DailyCalendarActivity::class.java))
            drawerLayout.closeDrawers()
        }
    }
    fun addEvent(view: View?){
        startActivity(Intent(this, EventEditActivity::class.java))
    }

    private fun setWeekView() {
        binding.monthYearTV.text = monthYearFromDate(CalendarUtils.selectedDate!!)
        val days = daysInWeekArray(CalendarUtils.selectedDate!!)
        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
        setEventAdpater()
    }

    fun previousWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusWeeks(1)
        setWeekView()
    }

    fun nextWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusWeeks(1)
        setWeekView()
    }

    override fun onItemClick(position: Int, date: LocalDate?) {
        CalendarUtils.selectedDate = date
        setWeekView()
    }

    override fun onResume() {
        super.onResume()
        setEventAdpater()
    }

    private fun setEventAdpater() {
        val dailyEvents: ArrayList<Event?> = eventsForDate(
            CalendarUtils.selectedDate!!
        )
        val eventAdapter = EventAdapter(applicationContext, dailyEvents)
        binding.eventListView.adapter = eventAdapter
    }

    fun newEventAction(view: View?) {
        startActivity(Intent(this, EventEditActivity::class.java))
    }

    fun dailyAction(view: View?) {
        startActivity(Intent(this, DailyCalendarActivity::class.java))
    }
}