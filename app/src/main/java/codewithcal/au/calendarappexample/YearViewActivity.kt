package codewithcal.au.calendarappexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codewithcal.au.calendarappexample.CalendarUtils.YearFromMonth
import codewithcal.au.calendarappexample.databinding.ActivityYearViewBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate

class YearViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYearViewBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYearViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CalendarUtils.selectedDate = LocalDate.now()
        setYearView()
        onclickMenu()
        dayOfToday()
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
        binding.navMain.ucNavLayoutRight.text =
            CalendarUtils.formattedToday(CalendarUtils.selectedDate!!)

        //回到今天日期
        binding.navMain.ucNavLayoutRight.setOnClickListener {
            //今天的時間
            CalendarUtils.selectedDate = LocalDate.now()
            //設定日曆(月)
            setYearView()
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
            drawerLayout.closeDrawers()
        }
        //選單點擊(週)
        binding.navDrawer.BTNMainWeek.setOnClickListener {
            startActivity(Intent(this, WeekViewActivity::class.java))
            drawerLayout.closeDrawers()
        }
        //選單點擊(月)
        binding.navDrawer.BTNMainMonth.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            drawerLayout.closeDrawers()
        }
        //選單點擊(日)
        binding.navDrawer.BTNMainDaily.setOnClickListener {
            startActivity(Intent(this, DailyCalendarActivity::class.java))
            drawerLayout.closeDrawers()
        }
    }

    private fun setYearView() {
        binding.YearTV.text = YearFromMonth(CalendarUtils.selectedDate!!)
        val listMonths = ArrayList<String>()
        for (i in 0..11){
            listMonths.add((i+1).toString())
        }
        val yearMonthAdapter = YearMonthAdapter(listMonths)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 3)
        binding.yearCalendarRecyclerView.layoutManager = layoutManager
        binding.yearCalendarRecyclerView.adapter = yearMonthAdapter
    }
    fun previousMonthAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusYears(1)
        setYearView()
    }

    fun nextMonthAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusYears(1)
        setYearView()
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


}
