package com.example.rpncalculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat

class SettingsActivity : AppCompatActivity() {

    private var background: LinearLayout? = null
    private var textViewChooseDarkMode: TextView? = null
    private var switch: Switch? = null
    private var textViewChoosePrecision: TextView? = null
    private var buttonChoosePrecision2: Button? = null
    private var buttonChoosePrecision3: Button? = null
    private var buttonChoosePrecision4: Button? = null
    private var buttonBack: Button? = null
    private var buttonSave: Button? = null
    private var precision: Int = 2
    private val returnIntent = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val actionBar = supportActionBar
        actionBar!!.title = "Settings"

        background = findViewById(R.id.background)
        textViewChooseDarkMode = findViewById(R.id.txtChooseDarkMode)
        switch = findViewById(R.id.switchDarkMode)
        textViewChoosePrecision = findViewById(R.id.txtChoosePrecision)
        buttonChoosePrecision2 = findViewById(R.id.btnChoosePrecision2)
        buttonChoosePrecision3 = findViewById(R.id.btnChoosePrecision3)
        buttonChoosePrecision4 = findViewById(R.id.btnChoosePrecision4)
        buttonBack = findViewById(R.id.btnBack)
        buttonSave = findViewById(R.id.btnSave)

        val dark = intent.getIntExtra("darkTheme", 0)
        if (dark == 0) { // if the dark theme is OFF in the main activity
            switch!!.isChecked = false
            background!!.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            textViewChooseDarkMode!!.setTextColor(ContextCompat.getColor(this, R.color.black))
            textViewChoosePrecision!!.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else { // if the dark theme is ON in the main activity
            switch!!.isChecked = true
            background!!.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.backgroundColorDark
                )
            )
            textViewChooseDarkMode!!.setTextColor(ContextCompat.getColor(this, R.color.white))
            textViewChoosePrecision!!.setTextColor(ContextCompat.getColor(this, R.color.white))
            buttonChoosePrecision2!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
            buttonChoosePrecision3!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
            buttonChoosePrecision4!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
            buttonBack!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
            buttonSave!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnMenuColorDark))
        }

        precision = intent.getIntExtra("precisionPrev", 4)

        when(precision){
            2 -> {
                buttonChoosePrecision2!!.isEnabled = false
            }
            3 -> {
                buttonChoosePrecision3!!.isEnabled = false
            }
            4 -> {
                buttonChoosePrecision4!!.isEnabled = false
            }
        }

        buttonChoosePrecision2!!.setOnClickListener { choosePrecision(2) }
        buttonChoosePrecision3!!.setOnClickListener { choosePrecision(3) }
        buttonChoosePrecision4!!.setOnClickListener { choosePrecision(4) }

        buttonSave!!.setOnClickListener { save() }
        buttonBack!!.setOnClickListener { back() }
    }


    private fun choosePrecision(chosenPrecision: Int){
        precision = chosenPrecision
        when(chosenPrecision){
            2 -> {
                buttonChoosePrecision2!!.isEnabled = false
                buttonChoosePrecision3!!.isEnabled = true
                buttonChoosePrecision4!!.isEnabled = true
            }
            3 -> {
                buttonChoosePrecision2!!.isEnabled = true
                buttonChoosePrecision3!!.isEnabled = false
                buttonChoosePrecision4!!.isEnabled = true
            }
            4 -> {
                buttonChoosePrecision2!!.isEnabled = true
                buttonChoosePrecision3!!.isEnabled = true
                buttonChoosePrecision4!!.isEnabled = false
            }
        }
    }

    private fun save() {
        if (switch!!.isChecked) {
            returnIntent.putExtra("dark", 1)
        } else {
            returnIntent.putExtra("dark", 0)
        }
        returnIntent.putExtra("precision", precision)
        setResult(Activity.RESULT_OK, returnIntent)
        println("settings")
        finish()
    }

    private fun back() {
        setResult(Activity.RESULT_CANCELED, returnIntent)
        finish()
    }


}