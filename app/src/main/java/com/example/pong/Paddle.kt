package com.example.pong

import android.graphics.Canvas
import android.graphics.Paint


class Paddle(private var x: Float, private var y: Float, var size: Int) {

    var paddleX = x - size / 2
    var paddleY = y

    var pHeight: Float = 50f

    fun setX(x: Float) {
        paddleX = x - size / 2
    }

    fun reset() {
        paddleX = x - size / 2
    }

    fun draw(canvas: Canvas) {
        val white = Paint().also { it.setARGB(255,255,255,255) }
        canvas.drawRoundRect(paddleX, paddleY, paddleX + size, paddleY + pHeight, 10f, 10f, white)
    }
}