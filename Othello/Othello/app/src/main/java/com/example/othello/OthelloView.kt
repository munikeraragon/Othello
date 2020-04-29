package com.example.othello

/*
 *  Othello Custom View.
 *  Author: Muniker Aragon
 */

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
        drawScore(canvas)
        drawResetButton(canvas)
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
                if(game.isActive(i-1, d-1)){ // if Cell is active
                    paint.color = game.getColor(i-1, d-1)
                    canvas.drawCircle(size * 0.125f*d - (size*0.125f/2),size*0.125f*i - (size*0.125f/2), size*0.125f/3,paint)
                }
            }
        }
    }

    private fun drawScore(canvas: Canvas){
        // background boxes
        paint.color = Color.LTGRAY
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 0.5f
        canvas.drawRect(size*0.05f, size*1.12f, size*0.35f,size*1.23f, paint )
        canvas.drawRect(size*0.65f, size*1.12f, size*0.95f,size*1.23f, paint )

        // text for left box
        paint.color = Color.WHITE
        paint.strokeWidth = 0.5f
        var score1 = game.whiteCells.size
        canvas.drawText("Score: $score1",size/5f, size*1.2f, paint)

        // text for right box
        paint.color = Color.BLACK
        paint.strokeWidth = 0.5f
        var score2 = game.blackCells.size
        canvas.drawText("Score: $score2",size/1.25f, size*1.2f, paint)
    }

    private fun drawResetButton(canvas: Canvas){
        // background box
        paint.color = Color.LTGRAY
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 0.5f
        canvas.drawRect(size*0.35f, size*1.30f, size*0.65f,size*1.44f, paint )

        // text for box
        paint.color = Color.RED
        canvas.drawText("RESET",size/2f, size*1.4f, paint)
    }

    private fun toReset(x: Float, y: Float): Boolean {
        var inWidth = x>0.35f && x<0.65f
        var inHeight = y>1.30f && y<1.44f
        if(inWidth && inHeight){ // is the click within limits
            return true
        }
        return false
    }

    private fun handleTouch(x: Float, y: Float){
        var row: Int
        var col: Int

        if(toReset(x, y)){ // if RESET was clicked
            game.reset()
            postInvalidate() // redraw view
        }

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

        game.update(row, col, context)
        postInvalidate() // redraw view
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action: Int = MotionEventCompat.getActionMasked(event)
        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                println("Action was DOWN")
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)

    }

}