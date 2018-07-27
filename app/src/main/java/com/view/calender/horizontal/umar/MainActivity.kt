package com.view.calender.horizontal.umar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.view.calender.horizontal.umar.horizontalcalendarview.DayDateMonthYearModel
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarListener
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarView

class MainActivity : AppCompatActivity() , HorizontalCalendarListener  {

    private lateinit var textView : TextView
    lateinit var button : Button
    private var controlsShown = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.month)
        button = findViewById(R.id.button)
        val  hcv = findViewById<HorizontalCalendarView>(R.id.hcv)
        hcv.setContext(this@MainActivity)
        hcv.setBackgroundColor(resources.getColor(R.color.colorPrimary))
//        hcv.showControls(true)
//        hcv.showControls(true)
        hcv.setControlTint(R.color.colorAccent)
        hcv.changeAccent(R.color.white)

        button.setOnClickListener {
            if(controlsShown){
                hcv.showControls(false)
                button.text =  "Show Controls"
                hcv.changeAccent(R.color.black)
            }else{
                hcv.showControls(true)
                button.text =  "Hide Controls"
                hcv.changeAccent(R.color.white)
            }
            controlsShown = !controlsShown
        }
    }

    override fun updateMonthOnScroll(selectedDate: DayDateMonthYearModel?) {
        val value =  ""+ selectedDate?.month + " " + selectedDate?.year
        textView.text = value

    }

    override fun newDateSelected(selectedDate: DayDateMonthYearModel?) {
        Toast.makeText(this@MainActivity ,selectedDate?.date +""+ selectedDate?.month + " " + selectedDate?.year , Toast.LENGTH_LONG).show()
    }

}
