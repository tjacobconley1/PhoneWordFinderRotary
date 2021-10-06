package com.example.phonewordfinderrotary

import android.content.Context
import android.view.View
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import kotlin.math.asin
import kotlin.math.round
import kotlin.math.sqrt

class Dialer (context: Context, private val attributeSet: AttributeSet): View(context, attributeSet){

    interface KeyDialler {
        fun dial(number: Int)
    }

    private var imageAngle: Float = 0.0f
    private var rotorImage: Drawable? = ContextCompat.getDrawable(context, R.drawable.spinner)
    private var innerRadius = 50
    private var outerRadius = 160
    private var lastFi = 0.0f
    private lateinit var keyDialer: KeyDialler


    fun setDialer(keyDialer: KeyDialler) {
        this.keyDialer = keyDialer
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val wWidth = right - left
        val hHeight = bottom - top
        val x = wWidth / 2
        val y = hHeight / 2

        canvas.save()
        rotorImage?.setBounds(
            0,
            0,
            rotorImage?.intrinsicWidth ?: 0,
            rotorImage?.intrinsicHeight ?: 0
        )

        if (imageAngle != 0f) {
            canvas.rotate(imageAngle, x.toFloat(), y.toFloat())
        }
        rotorImage?.draw(canvas)
    }

    private fun fireDialListener(number: Int) {

        keyDialer.dial(number)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val x0 = width / 2
        val y0 = height / 2
        val x1 = event?.x ?: 0f
        val y1 = event?.y ?: 0f

        val x = x0 - x1
        val y = y0 - y1

        val radius = sqrt(((x * x) + (y * y)).toDouble())
        val sini = y / radius
        var fi = Math.toDegrees(asin(sini))


        if (x1 > x0 && y0 > y1) {
            fi = 180 - fi
        } else if (x1 > x0 && y1 > y0) {
            fi = 180 - fi
        } else if (x0 > x1 && y1 > y0) {
            fi += 360
        }

        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {

                if (radius > innerRadius && radius < outerRadius) {
                    imageAngle += (Math.abs(fi - lastFi) + 0.25f).toFloat()
                    imageAngle %= 360
                    lastFi = fi.toFloat()
                    invalidate()
                    return true
                }
            }
            MotionEvent.ACTION_DOWN -> {
                imageAngle = 0f
                lastFi = fi.toFloat()
                return true
            }

            MotionEvent.ACTION_UP -> {
                val angle = imageAngle % 360
                var number = (round(angle - 20) / 30).toInt()

                if (number > 0) {
                    if (number == 10) {
                        number = 0
                    }
                    fireDialListener(number)
                }
                imageAngle = 0f
                post {
                    val fromDegrees = angle
                    val animation = RotateAnimation(
                        fromDegrees,
                        0f,
                        Animation.RELATIVE_TO_SELF,
                        .5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                    )
                    animation.setInterpolator(context, android.R.anim.decelerate_interpolator)
                    animation.duration = (angle * 5L).toLong()
                    startAnimation(animation)
                }
                return true
            }
        }

        return super.onTouchEvent(event)


    }

}