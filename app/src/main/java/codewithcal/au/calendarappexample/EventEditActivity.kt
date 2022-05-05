package codewithcal.au.calendarappexample

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import codewithcal.au.calendarappexample.CalendarUtils.formattedDate
import codewithcal.au.calendarappexample.CalendarUtils.formattedTime
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import codewithcal.au.calendarappexample.databinding.ActivityEventEditBinding
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class EventEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventEditBinding
    private var time: LocalTime? = null
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEventEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //設置鬧鐘通知
        creaateNotificationChannel()

        //設置日期
        binding.eventDateTV.setOnClickListener {
            calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK,{ _, year, month, day->
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.DAY_OF_MONTH,day)
                binding.eventDateTV.setText(""+year+"年 "+ (month+1) +"月"+" "+day+"日")
                CalendarUtils.selectedDate = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            },year,month,day).show()
        }

        //設置時間
        binding.eventTimeTV.setOnClickListener {
            calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR_OF_DAY)
            var minute = calendar.get(Calendar.MINUTE)
            TimePickerDialog(this,AlertDialog.THEME_HOLO_DARK,{ _,hour,minute->
                calendar.set(Calendar.HOUR_OF_DAY,hour)
                calendar.set(Calendar.MINUTE,minute)
                binding.eventTimeTV.text = SimpleDateFormat("HH : mm", Locale.TAIWAN).format(calendar.time)
                time = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            },hour,minute,true).show()
        }

        //一開始先抓取當前時間和選擇日期
        binding.eventDateTV.text = formattedDate(
            CalendarUtils.selectedDate!!
        )
        time = LocalTime.now()
        binding.eventTimeTV.text = formattedTime(time!!)

        //選擇是否設置提醒
        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.eventNotiTV.text = "提醒已開啟"

            } else {
                binding.eventNotiTV.text = "提醒已關閉"

            }
        }
    }

    private fun cancelAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,1234,intent,0)

        alarmManager.cancel(pendingIntent)

        Toast.makeText(this,"提醒已關闢",Toast.LENGTH_SHORT).show()
    }

    private fun setAlarm() {

        if (calendar.before(Calendar.getInstance())){
            Toast.makeText(this,"時間已過",Toast.LENGTH_SHORT).show()
            cancelAlarm()
        }
        else{
            alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(this,AlarmReceiver::class.java)

            pendingIntent = PendingIntent.getBroadcast(this,1234,intent,0)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis, pendingIntent)
            Toast.makeText(this,"已設置提醒",Toast.LENGTH_SHORT).show()
        }
    }

    private fun creaateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "行事曆"
            val description = "?"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Alarm1",name,importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }
    }


    fun saveEventAction(view: View?) {
        var eventName : String
        var remark : String
        var noti :String
        if (binding.eventNameET.text.isEmpty()){
            binding.eventNameET.setText("我的活動")
            eventName = binding.eventNameET.text.toString()
            remark = binding.eventRemarkET.text.toString()
            noti = binding.eventNotiTV.text.toString()
            val newEvent = Event(eventName, CalendarUtils.selectedDate!!, time!!,remark,noti)
            Event.eventsList.add(newEvent)
            finish()
        } else{
            eventName = binding.eventNameET.text.toString()
            remark = binding.eventRemarkET.text.toString()
            noti = binding.eventNotiTV.text.toString()
            val newEvent = Event(eventName, CalendarUtils.selectedDate!!, time!!,remark,noti)
            Event.eventsList.add(newEvent)
            finish()
        }

    }

    fun exitEventAction(view: View) {
        finish()
    }
}