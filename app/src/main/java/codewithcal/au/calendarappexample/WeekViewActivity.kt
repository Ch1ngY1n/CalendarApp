package codewithcal.au.calendarappexample

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
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
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import codewithcal.au.calendarappexample.databinding.ActivityWeekViewBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
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
        binding.monthYearTV.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK,{ _, year, month, day->
                c.set(Calendar.YEAR,year)
                c.set(Calendar.MONTH,month)
                c.set(Calendar.DAY_OF_MONTH,day)
                binding.monthYearTV.setText(""+ (month+1) +"月"+" "+year)
                CalendarUtils.selectedDate = c.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                setWeekView()
            },year,month,day).show()
        }

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
    override fun onStart() {
        super.onStart()
        //抓取google登入資料
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val headerLayout: View = binding.navDrawer.navDrawer.getHeaderView(0)
        val DrawerUserName: TextView = headerLayout.findViewById(R.id.profile_name)
        DrawerUserName.setText(account?.displayName)
        val DrawerUserPhoto:ImageView = headerLayout.findViewById(R.id.profile_image)
        Glide.with(this).load(account?.photoUrl).into(DrawerUserPhoto)
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
        //選單點擊(年)
        binding.navDrawer.BTNMainYear.setOnClickListener {
            startActivity(Intent(this,YearViewActivity::class.java))
            drawerLayout.closeDrawers()
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

    override fun onItemClick(position: Int, date: LocalDate) {
        CalendarUtils.selectedDate = date
        setWeekView()
    }


    override fun OnItemLongClick(position: Int, date: LocalDate) {
        showDialog()
    }
    private fun showDialog(){
        val yesNoDialog= YesNoDialog2(this)
        yesNoDialog.show()
    }

    override fun onResume() {
        super.onResume()
        setEventAdpater()
    }

    private fun setEventAdpater() {
        val dailyEvents: ArrayList<Event> = eventsForDate(
            CalendarUtils.selectedDate!!
        )
        val eventAdapter = EventAdapter(applicationContext, dailyEvents)


        binding.eventListView.setOnItemClickListener { adapterView, view, i, l ->
            Event.eventsList.removeAt(i)
            dailyEvents.removeAt(i)
            setEventAdpater()
        }

        binding.eventListView.adapter = eventAdapter
    }
    private fun showDeleteDialog(){
        val deleteDialog= DeleteDialog(this)
        deleteDialog.show()
    }
    private fun dismissDeleteDialog(){
        val deleteDialog= DeleteDialog(this)
        deleteDialog.dismiss()
    }

}
class DeleteDialog(context: Context):Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_dialog)
        val dialog = Dialog(context)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bk)
    }
}
class YesNoDialog2(context: Context): Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_dialog)
        val dialog = Dialog(context)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bk)
        setHourAdapter()
    }

    private fun setHourAdapter() {
        val hourAdapter = HourAdapter(context, hourEventList())
        val month_hourAdapter = findViewById<ListView>(R.id.month_hourListView)
        month_hourAdapter.adapter = hourAdapter
    }
    //0:00-23:00 一整天
    private fun hourEventList(): ArrayList<HourEvent> {
        val list = ArrayList<HourEvent>()
        for (hour in 0..23) {
            val time = LocalTime.of(hour, 0)
            val events = CalendarUtils.selectedDate?.let { Event.eventsForDateAndTime(it, time) }
            val hourEvent = events?.let { HourEvent(time, it) }
            if (hourEvent != null) {
                list.add(hourEvent)
            }
        }
        return list
    }

}