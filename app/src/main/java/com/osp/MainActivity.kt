package com.osp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.osp.R
import com.example.osp.databinding.ActivityMainBinding
import com.osp.library.FilterCalenderView
import com.osp.library.utils.DefaultSelectedOption

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUI()
    }

    private fun initUI() {

        //app:arrowBackgroundColor="@color/light_blue_600"
        //app:arrowIconColor="@color/black"
        //app:optionSelectedBackgroundColor="@color/black"
        //app:optionUnselectedBackgroundColor="@color/black_overlay"
        //app:optionUnselectedStrokeColor="@color/black"
        //app:optionSelectedStrokeColor="@color/light_blue_A400"
        //app:optionSelectedTextColor="@color/light_blue_600"
        //app:optionUnselectedTextColor="@color/white"
        //app:optionTextSize="12sp"
        //app:displayTextColor="@color/light_blue_600"
        //app:displayTextSize="18sp"
        //app:defaultSelectedOption="None"

        binding.apply {
            calenderView.setOnCalenderChangeListener { dateFrom, dateTo ->
                println("test>> from: $dateFrom to: $dateTo")
            }
            calenderView.setMaxDate(System.currentTimeMillis())
            calenderView.defaultSelectedOption=DefaultSelectedOption.All
            calenderView.setCalendarTheme(R.style.CustomDatePickerDialog)
            calenderView.arrowBackgroundColor=ContextCompat.getColorStateList(this@MainActivity, R.color.light_blue_600)
            calenderView.arrowIconColor= ContextCompat.getColor(this@MainActivity,R.color.black)
            calenderView.optionSelectedBackgroundColor=ContextCompat.getColor(this@MainActivity,R.color.black)
            calenderView.optionUnselectedBackgroundColor=ContextCompat.getColor(this@MainActivity,R.color.black_overlay)
            calenderView.optionSelectedStrokeColor= ContextCompat.getColor(this@MainActivity,R.color.light_blue_A400)
            calenderView.optionUnselectedStrokeColor=ContextCompat.getColor(this@MainActivity,R.color.black)
            calenderView.optionSelectedTextColor=ContextCompat.getColor(this@MainActivity,R.color.light_blue_600)
            calenderView.optionUnselectedTextColor=ContextCompat.getColor(this@MainActivity,R.color.white)
            calenderView.optionTextSize=30f
            calenderView.displayTextColor= ContextCompat.getColor(this@MainActivity,R.color.light_blue_600)
            calenderView.displayTextSize=25f

        }

    }
}