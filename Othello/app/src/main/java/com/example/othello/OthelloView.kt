package com.example.othello

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.MotionEventCompat

class OthelloView : View {
    private val game = GameControl()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var size = 320 // View size in pixels
    private var borderWidth = 7.0f
    private var borderColor = Color.BLACK

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)
        drawGrid(canvas)
        fillGrid(canvas)
    }

    private fun drawGrid(canvas: Canvas) {
        // draw square background
        paint.color = Color.LTGRAY
        paint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, size*1f, size*1f, paint)

        // draw border
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10.0f
        canvas.drawRect(0f+borderWidth, 0f+borderWidth, size*1f-borderWidth, size*1f-borderWidth, paint)

        //draw grid lines
        paint.strokeWidth = 2.0f
        for(i in 1..8){ //draw horizontal lines
            canvas.drawLine(size * 0.125f*i, 0f, size * 0.125f*i, size*1f,paint)
        }
        for(i in 1..8){ // draw vertical lines
            canvas.drawLine(0f, size * 0.125f*i, size*1f, size*0.125f*i, paint)
        }
    }

    private fun fillGrid(canvas: Canvas){
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 2.0f
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = size/13f // adjust font size to fill cell

        for(i in 1..8){
            for(d in 1..8){
                var iscellActive = game.isActive(i-1, d-1)
                if(iscellActive){
                    paint.color = game.getColor(i-1, d-1)
                    canvas.drawCircle(size * 0.125f*d - (size*0.125f/2),size*0.125f*i - (size*0.125f/2), size*0.125f/3,paint)
                }
            }
        }
    }

    private fun handleTouch(x: Float, y: Float){
        var row: Int
        var col: Int

        // find selected column
        when{
            x <= 0.125f -> col = 0
            x <= 0.25f -> col = 1
            x <= 0.375f -> col = 2
            x <= 0.5f -> col = 3
            x <= 0.625f -> col = 4
            x <= 0.750f -> col = 5
            x <= 0.875f -> col = 6
            else -> col = 7
        }
        // find selected row
        when{
            y <= 0.125f -> row = 0
            y <= 0.25f -> row = 1
            y <= 0.375f -> row = 2
            y <= 0.5f -> row = 3
            y <= 0.625f -> row = 4
            y <= 0.750f -> row = 5
            y <= 0.875f -> row = 6
            else -> row = 7
        }

        //game.update(row, col)
        postInvalidate() // redraw view
    }

  /*  override fun onTouchEvent(event: MotionEvent): Boolean {
        val action: Int = MotionEventCompat.getActionMasked(event)
        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                println("Action was DOWN")
                println("touched X: "+ event.getX())
                println("touched Y: "+ event.getY())
                handleTouch(event.getX()/size, event.getY()/size)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                println("Action was MOVE")
                true
            }
            MotionEvent.ACTION_UP -> {
                println( "Action was UP")
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                println("Action was CANCEL")
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                println( "Movement occurred outside bounds of current screen element")
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
*/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)

    }
}