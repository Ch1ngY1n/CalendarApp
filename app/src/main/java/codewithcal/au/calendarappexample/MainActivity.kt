package codewithcal.au.calendarappexample

import codewithcal.au.calendarappexample.CalendarUtils.monthYearFromDate
import codewithcal.au.calendarappexample.CalendarUtils.daysInMonthArray
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
import codewithcal.au.calendarappexample.CalendarUtils.formattedToday
import codewithcal.au.calendarappexample.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity(), OnItemListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        CalendarUtils.selectedDate = LocalDate.now()
        //設定日曆(月)
        setMonthView()
        dayOfToday()
        onclickMenu()
        addEvent()
    }
    private fun dayOfToday(){
        //今天日期
        binding.navMain.ucNavLayoutRight.text = formattedToday(CalendarUtils.selectedDate!!)

        //回到今天日期
        binding.navMain.ucNavLayoutRight.setOnClickListener {
            //今天的時間
            CalendarUtils.selectedDate = LocalDate.now()
            //設定日曆(月)
            setMonthView()
        }
    }
    private fun onclickMenu(){
        //側邊選單
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout_mian)
        val imgMenu = findViewById<ImageView>(R.id.uc_img_nav_left)
        val navView = findViewById<NavigationView>(R.id.nav_drawer)
        navView.itemIconTintList = null
        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //選單點擊(週)
        binding.navDrawer.BTNMainWeek.setOnClickListener {
            startActivity(Intent(this, WeekViewActivity::class.java))
            drawerLayout.closeDrawers()
        }
        //選單點擊(月)
        binding.navDrawer.BTNMainMonth.setOnClickListener {
            drawerLayout.closeDrawers()
        }
        //選單點擊(日)
        binding.navDrawer.BTNMainDaily.setOnClickListener {
            startActivity(Intent(this, DailyCalendarActivity::class.java))
            drawerLayout.closeDrawers()
        }
    }
    private fun addEvent(){
        binding.eventAddMain.eventAddEdit.setOnClickListener {
            binding.eventAddMain.eventAddButton.setImageResource(R.drawable.event_add_check_icon)
        }
        binding.eventAddMain.eventAddButton.setOnClickListener {
            startActivity(Intent(this, EventEditActivity::class.java))
        }
    }

    private fun setMonthView() {
        binding.monthYearTV.text = monthYearFromDate(CalendarUtils.selectedDate!!)
        val daysInMonth = daysInMonthArray()
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    fun previousMonthAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusMonths(1)
        setMonthView()
    }

    override fun onItemClick(position: Int, date: LocalDate?) {
        if (date != null) {
            CalendarUtils.selectedDate = date
            setMonthView()
        }
    }

//    fun weeklyAction(view: View?) {
//        startActivity(Intent(this, WeekViewActivity::class.java))
//    }
}