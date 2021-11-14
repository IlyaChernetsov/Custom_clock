package com.example.task_4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.withSave
import java.util.*

class CustomClock@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {




    private val hoursLine = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }

    private val center = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
    }

    private val minuteLine = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private val text = Paint().apply {
        isAntiAlias = true
        textSize = 50f
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }



    private var stripColor:Int = 0
    private var secColor:Int = 0
    private var minuteAndHoursColor:Int = 0


    private var hours = 0.0f
    private var minutes = 0.0f
    private var seconds = 0.0f

    private var mWidth = 0.0f
    private var mHeight = 0.0f
    private var wrapperRadius = 0.0f

    private var arrowRadius = 0.0f


    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            getTime()
            invalidate()
        }
    }



    private val secondArrow = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }
    private val minuteArrow = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }
    private val hourArrow = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 12f
    }



    private fun getAttributes(attrs: AttributeSet?){
        context.withStyledAttributes(attrs, R.styleable.CustomViewWatchArrows){
            stripColor = getColor(R.styleable.CustomViewWatchArrows_stripColor, 0)
            secColor = getColor(R.styleable.CustomViewWatchArrows_secondsColor, 0)
            minuteAndHoursColor = getColor(R.styleable.CustomViewWatchArrows_minuteAndHoursColor,0)
            seconds = getFloat(R.styleable.CustomViewWatchArrows_seconds,0f)
            minutes = getFloat(R.styleable.CustomViewWatchArrows_minutes,0f)
            hours = getFloat(R.styleable.CustomViewWatchArrows_hours,0f)
        }
    }
    init {
        getAttributes(attrs)
        getTime()
        mHandler.sendEmptyMessageDelayed(1, 1000)
    }






    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mWidth = width.toFloat()
        mHeight = height.toFloat()
        wrapperRadius = mWidth/2 - mWidth/100
        arrowRadius = mWidth/2 - mWidth/20


        canvas.drawCircle(mWidth/2,mHeight/2,wrapperRadius, hoursLine)

        canvas.drawCircle(mWidth/2,mHeight/2,20f, center)

        for (i in 1..12) {
            canvas.withSave {
                canvas.rotate(360/12f * (i), mWidth/2, mHeight/2)
                canvas.drawLine(mWidth/2, mHeight/2 - wrapperRadius + mHeight/50, mWidth/2, mHeight/2 - wrapperRadius, hoursLine)
                canvas.drawText((i).toString(), mWidth/2 - 20, mHeight/2-mWidth/2+100, text)
            }
        }

        for (i in 1..60) {
            canvas.withSave {
                canvas.rotate(360/60f * (i), mWidth/2, mHeight/2)
                canvas.drawLine(mWidth/2, mHeight/2 - wrapperRadius + mHeight/60, mWidth/2, mHeight/2 - wrapperRadius, minuteLine)
            }
        }




        canvas.withSave {
            canvas.rotate(360/60 * seconds, mWidth /2, mHeight /2)
            canvas.drawLine(mWidth /2, mHeight /2, mWidth /2, mHeight /2 - arrowRadius + mHeight/40, secondArrow)
        }

        canvas.withSave {
            canvas.rotate(360/60 * minutes + seconds * 0.1f, mWidth /2, mHeight /2)
            canvas.drawLine(mWidth /2, mHeight /2, mWidth /2, mHeight /2 - arrowRadius + mHeight/7, minuteArrow)
        }

        canvas.withSave {
            canvas.rotate(360/12 * hours + minutes * 0.5f, mWidth /2, mHeight /2)
            canvas.drawLine(mWidth /2, mHeight /2, mWidth /2, mHeight /2 - arrowRadius + mHeight/4, hourArrow)
        }

        mHandler.sendEmptyMessageDelayed(1, 1000)
    }

    private fun getTime(){
        val c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"))
        seconds = c.get(Calendar.SECOND).toFloat()
        minutes = c.get(Calendar.MINUTE).toFloat()
        hours = c.get(Calendar.HOUR).toFloat()
    }

}