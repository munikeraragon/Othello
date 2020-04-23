package com.example.othello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setup layout params
        val myview = findViewById<OthelloView>(R.id.emotionalFaceView)

        myview.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->{
                        println("Action was DOWN")
                        println("touched X: "+ event.getX())
                        println("touched Y: "+ event.getY())
                        //handleTouch(event.getX()/size, event.getY()/size)
                        true
                    } //Do Something
                }

                return v?.onTouchEvent(event) ?: true
            }
        })

    }
}
