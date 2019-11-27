package com.frank.han

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frank.han.util.DateExtensions
import java.util.Calendar
import kotlinx.android.synthetic.main.activity_main.name

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeText()
    }

    fun changeText() {
        val isSameYear = DateExtensions.isSameYear(Calendar.getInstance(), Calendar.getInstance())
        name.text = String.format("test = %s", isSameYear)
    }
}
