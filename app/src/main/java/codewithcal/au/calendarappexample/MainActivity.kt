package codewithcal.au.calendarappexample

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codewithcal.au.calendarappexample.CalendarAdapter.OnItemListener
import codewithcal.au.calendarappexample.CalendarUtils.daysInMonthArray
import codewithcal.au.calendarappexample.CalendarUtils.formattedToday
import codewithcal.au.calendarappexample.CalendarUtils.monthYearFromDate
import codewithcal.au.calendarappexample.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.LocalTime.ofInstant
import java.time.ZoneId
import java.util.*

class MainActivity : AppCompatActivity(), OnItemListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var flag = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //設定日曆(月)
        setMonthView()
        onclickMenu()
        dayOfToday()
        setDate()
        binding.monthYearTV.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this,AlertDialog.THEME_HOLO_DARK,{ _, year, month, day->
                c.set(Calendar.YEAR,year)
                c.set(Calendar.MONTH,month)
                c.set(Calendar.DAY_OF_MONTH,day)
                binding.monthYearTV.setText(""+ (month+1) +"月"+" "+year)
                CalendarUtils.selectedDate = c.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                setMonthView()
            },year,month,day).show()
        }
        binding.navDrawer.BTNMainSignOut.setOnClickListener {
            signOut()
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
    private fun dayOfToday(){
        //今天日期
        binding.navMain.ucNavLayoutRight.text = formattedToday(LocalDate.now())

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
        //選單點擊(年)
        binding.navDrawer.BTNMainYear.setOnClickListener {
            startActivity(Intent(this,YearViewActivity::class.java))
            drawerLayout.closeDrawers()
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
    fun addEvent(view: View?){
        startActivity(Intent(this, EventEditActivity::class.java))
    }

    private fun setMonthView() {
        binding.monthYearTV.text = monthYearFromDate(CalendarUtils.selectedDate!!)
        val daysInMonth = daysInMonthArray()
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
    }
    private fun setDate(){
        binding.monthYearTV.setOnClickListener {

        }
    }

    fun previousMonthAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusMonths(1)
        setMonthView()
    }

    private fun signOut(){
        //登出
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        binding.navDrawer.BTNMainSignOut.setOnClickListener {v->
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onItemClick(position: Int, date: LocalDate) {
            CalendarUtils.selectedDate = date
            setMonthView()
    }

    override fun OnItemLongClick(position: Int, date: LocalDate) {
        showDialog()
    }
    private fun showDialog(){
        val yesNoDialog= YesNoDialog1(this)
        yesNoDialog.show()
    }




//    fun weeklyAction(view: View?) {
//        startActivity(Intent(this, WeekViewActivity::class.java))
//    }
}
class YesNoDialog1(context: Context): Dialog(context){
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