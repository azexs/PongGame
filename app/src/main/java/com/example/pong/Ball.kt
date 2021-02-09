package com.example.pong
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class Ball(var x: Float, var y: Float, var size: Float) {

    var ballX = x
    var ballY = y
    var xVel = 10f
    var yVel = 10f
    var dir = true

    init {
        reset()
    }

    fun reset() {
        ballX = x
        ballY = y
        yVel = if (dir) 10f else -10f
        xVel = 10f
        changeDirection()
    }

    fun stop() {
        dir = !dir
        xVel = 0f
        yVel = 0f
        ballX = x
        ballY = y
    }

    fun draw(canvas: Canvas) {
        val white = Paint()
        white.setARGB(255, 255, 255, 255)

        canvas.drawOval(RectF(ballX, ballY, ballX + size, ballY + size), white)
    }

    fun changeDirection() {
        yVel = -yVel
    }

    fun update() {
        ballX += xVel
        ballY += yVel

        if (ballX <= 0 || ballX + size >= Resources.getSystem().displayMetrics.widthPixels) {
            xVel = -xVel // change horizon
            if (xVel > 0) xVel += 1 else xVel -= 1
            if (yVel > 0) yVel += 1 else yVel -= 1
        }

    }
}
