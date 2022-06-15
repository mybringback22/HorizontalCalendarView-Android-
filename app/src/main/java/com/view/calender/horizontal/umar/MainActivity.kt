package com.view.calender.horizontal.umar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.view.calender.horizontal.umar.horizontalcalendarview.CalAdapter
import com.view.calender.horizontal.umar.horizontalcalendarview.DayDateMonthYearModel
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarListener
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarView

class MainActivity : AppCompatActivity(), HorizontalCalendarListener {

    private lateinit var textView: TextView
    lateinit var button: Button
    private var controlsShown = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.month)
        button = findViewById(R.id.button)
        val hcv = findViewById<HorizontalCalendarView>(R.id.hcv)
        hcv.setContext(this@MainActivity)
        hcv.setBackgroundColor(resources.getColor(R.color.background))
        hcv.setMaterialStyle(true)
        hcv.setWeekNameMode(CalAdapter.WeekNameMode.MEDIUM)
        hcv.showControls(false)
        hcv.setControlTint(R.color.colorAccent)
        hcv.changeAccent(R.color.text_color)

        button.setOnClickListener {
            if (controlsShown) {
                hcv.showControls(false)
                button.text = "Show Controls"
            } else {
                hcv.showControls(true)
                button.text = "Hide Controls"
            }
            controlsShown = !controlsShown
        }
    }

    override fun updateMonthOnScroll(selectedDate: DayDateMonthYearModel?) {
        val value = "" + selectedDate?.month + " " + selectedDate?.year
        textView.text = value

    }

    override fun newDateSelected(selectedDate: DayDateMonthYearModel?) {
        Toast.makeText(this@MainActivity, selectedDate?.date + "" + selectedDate?.month + " " + selectedDate?.year, Toast.LENGTH_LONG).show()
    }

}
