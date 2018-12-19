package com.nandaadisaputra.master.password

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

import java.util.Timer
import java.util.TimerTask

/**
 * create by nandaadisaputra
 */

class MainActivity : AppCompatActivity() {

    private var timer: Timer? = Timer()
    private val DELAY: Long = 500

    private var editTextPass: EditText? = null
    private var statusPassword: TextView? = null
    private var progressBarPass: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPass = findViewById<View>(R.id.inputPassword) as EditText?
        statusPassword = findViewById<View>(R.id.textPass) as TextView?
        progressBarPass = findViewById<View>(R.id.progressBarPass) as ProgressBar?

        editTextPass!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (timer != null)
                    timer!!.cancel()
            }

            override fun afterTextChanged(s: Editable) {
                //avoid triggering event when text is too short
                if (s.length >= 0) {

                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                        override fun run() {
                            // do what you need here (refresh list)
                            val search = editTextPass!!.text.toString()

                            runOnUiThread { checkPassword(search) }

                        }

                    }, DELAY)


                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun checkPassword(parameters: String) {
        if (parameters == "") {
            editTextPass!!.error = "Input Password"
            progressBarPass!!.progress = 0
            progressBarPass!!.progressTintList = ColorStateList.valueOf(Color.RED)
        } else if (parameters.length > 10) {
            statusPassword!!.text = "Password Kuat"
            progressBarPass!!.progress = 6012
            progressBarPass!!.progressTintList = ColorStateList.valueOf(Color.GREEN)
        } else if (parameters.length > 6) {
            statusPassword!!.text = "Password Sedang"
            progressBarPass!!.progress = 60
            progressBarPass!!.progressTintList = ColorStateList.valueOf(Color.YELLOW)
        } else {
            statusPassword!!.text = "Password Lemah"
            progressBarPass!!.progress = 40
            progressBarPass!!.progressTintList = ColorStateList.valueOf(Color.RED)
        }
    }

}
