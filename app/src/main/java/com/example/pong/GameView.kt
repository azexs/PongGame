package com.example.pong

import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.graphics.*
import android.view.MotionEvent
import java.util.*
import kotlin.concurrent.schedule


class GameView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {

    private lateinit var ball: Ball
    private lateinit var player1: Paddle
    private lateinit var player2: Paddle
    private val thread: GameThread
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("gameInfo", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

        ball = Ball(width / 2f, height / 2f, 50f)
        player1 = Paddle(width / 2f, 20f, width / 4)
        player2 = Paddle(width / 2f, height - 45f, width / 4)

        if (sharedPreferences.getBoolean("pause", false)) {
            ball!!.x = sharedPreferences.getFloat("ballX", 0F)
            ball!!.y = sharedPreferences.getFloat("ballY", 0F)
            ball!!.xVel = sharedPreferences.getFloat("ballVelX", 0F)
            ball!!.yVel = sharedPreferences.getFloat("ballVelY", 0F)
            player1.paddleX = sharedPreferences.getFloat("p1pos", 0F)
            player2.paddleX = sharedPreferences.getFloat("p2pos", 0F)
    }
        thread.running = true
        thread.start()
    }





    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        editor.putFloat("ballX", ball.x)
        editor.putFloat("ballY", ball.y)
        editor.putFloat("ballVelX", ball.xVel)
        editor.putFloat("ballVelY", ball.yVel)
        editor.putFloat("p1pos", player1.paddleX)
        editor.putFloat("p2pos", player2.paddleX)
        editor.apply()
        thread.running = false
        thread.join()
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas != null) {
            ball.draw(canvas)
            player1.draw(canvas)
            player2.draw(canvas)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        for (i in 0 until event.pointerCount) {
            if (event.y < height / 2) {     //top
               movePaddle(player1, event)
            } else {                        //bottom
                movePaddle(player2, event)
            }
        }
        return true
    }

    private fun movePaddle(paddle: Paddle, event: MotionEvent) {
        when {
            event.x + paddle.size / 2 > width -> paddle.setX((width - paddle.size/2).toFloat())
            event.x - paddle.size / 2 < 0f -> paddle.setX(0f + paddle.size / 2)
            else -> paddle.setX(event.x)
        }
    }


    fun update() {
        bounce()
        checkWin()
        ball.update()
    }

    fun bounce() {
        val ballRect = RectF(ball.ballX, ball.ballY, ball.ballX + ball.size, ball.ballY + ball.size)
        val p1Rect =
            RectF(player1.paddleX, player1.paddleY, player1.paddleX + player1.size, player1.paddleY + player1.pHeight)
        val p2Rect =
            RectF(player2.paddleX, player2.paddleY, player2.paddleX + player2.size, player2.paddleY + player2.pHeight)

        if (ballRect.intersect(p1Rect) || ballRect.intersect(p2Rect)) {
            ball.changeDirection()
        }
    }

    private fun checkWin() {
        if ((ball.ballY < -ball.size / 2) || (ball.ballY + ball.size > player2.paddleY + player2.pHeight + 5f + ball.size / 2)) {
            player1.reset()
            player2.reset()
            ball.stop()

            Timer().schedule(1000) {
                ball.reset()
            }
        }
    }




}


