package ru.maximus.playzer.SnakeGame

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.maximus.playzer.FirebaseAuthentication
import ru.maximus.playzer.R
import kotlin.math.abs


class SnakeGa : AppCompatActivity() {

    private lateinit var scorebar: TextView
    private lateinit var stroyscorebar: TextView
    var score: Long = 0
    var best_score: Long = 0
    var time: Long = 150
    private lateinit var firebase: FirebaseAuthentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake_ga)

        scorebar = findViewById<TextView>(R.id.scorebar)
        stroyscorebar = findViewById<TextView>(R.id.storyscorebar)
        firebase = FirebaseAuthentication.get()
        firebase.scoreResult.observe(this,
        Observer {
            it?.let {
                best_score = it
                stroyscorebar.text = getString(R.string.best_score_text, best_score)
            }
        })
        lifecycleScope.launch(Dispatchers.IO) {
            firebase.readSnakeBestScore()
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()
        var snake: Snake = Snake()
        //возможность управлять жестами
        open class OnSwipeTouchListener : View.OnTouchListener {

            private val gestureDetector = GestureDetector(GestureListener())

            fun onTouch(event: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(event)
            }

            private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

                private val SWIPE_THRESHOLD = 100
                private val SWIPE_VELOCITY_THRESHOLD = 100

                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    onTouch(e)
                    return true
                }

                override fun onFling(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    val result = false
                    try {
                        val diffY = e2.y - e1.y
                        val diffX = e2.x - e1.x
                        if (abs(diffX) > abs(diffY)) {
                            if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffX > 0) {
                                    onSwipeRight()
                                } else {
                                    onSwipeLeft()
                                }
                            }
                        } else {
                            // this is either a bottom or top swipe.
                            if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffY > 0) {
                                    onSwipeTop()
                                } else {
                                    onSwipeBottom()
                                }
                            }
                        }
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                    return result
                }
            }

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(event)
            }

            open fun onSwipeRight() {}
            open fun onSwipeLeft() {}
            open fun onSwipeTop() {}
            open fun onSwipeBottom() {}
        }


        var canvas = findViewById<CanvasView>(R.id.canvas)

        canvas.setOnTouchListener(object : OnSwipeTouchListener() {
            override fun onSwipeLeft() {
                snake.alive = true
                if (snake.direction != "right")
                    snake.direction = "left"
            }

            override fun onSwipeRight() {
                snake.alive = true
                if (snake.direction != "left")
                    snake.direction = "right"
            }

            override fun onSwipeTop() {
                snake.alive = true
                if (snake.direction != "up")
                    snake.direction = "down"
            }

            override fun onSwipeBottom() {
                snake.alive = true
                if (snake.direction != "down")
                    snake.direction = "up"
            }
        })
////////////////////////////////////////////////////////////////////////////////////////

        // движение змеи
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                while (snake.alive) {
                    when (snake.direction) {
                        "up" -> {

                            snake.headY -= 50
                            if (!snake.possibleMove()) {
                                snake.alive = false
                                snake.reset()
                                lifecycleScope.launch(Dispatchers.Main) {
                                    showScoreBar()
                                }
                            }
                        }

                        "down" -> {

                            snake.headY += 50
                            if (!snake.possibleMove()) {
                                snake.alive = false
                                snake.reset()
                                lifecycleScope.launch(Dispatchers.Main) {
                                    showScoreBar()
                                }

                            }
                        }

                        "left" -> {

                            snake.headX -= 50
                            if (!snake.possibleMove()) {
                                snake.alive = false
                                snake.reset()

                                lifecycleScope.launch(Dispatchers.Main) {
                                    showScoreBar()
                                }
                            }

                        }

                        "right" -> {

                            snake.headX += 50
                            if (!snake.possibleMove()) {
                                snake.alive = false
                                snake.reset()
                                lifecycleScope.launch(Dispatchers.Main) {
                                    showScoreBar()
                                }

                            }
                        }
                    }
                    // преобразование головы в тело
                    Snake.bodyParts.add(arrayOf(snake.headX, snake.headY))


                    if (snake.headX == Food.posX && snake.headY == Food.posY){
                        Food.generate()

                        lifecycleScope.launch(Dispatchers.Main) {
                            scorebar.text = getString(R.string.score_text,++score)

                            if (score.toInt() % 3 == 0)
                            {
                                time -= 2 // усорение скорости змеи
                            }


                        }
                    }
                    else
                        Snake.bodyParts.removeAt(0)


                    lifecycleScope.launch(Dispatchers.Main){
                        canvas.invalidate()
                    }

                    delay(time)
                }
            }
        }
        val button_up = findViewById<ImageButton>(R.id.button_up)
        button_up.setOnClickListener {
            snake.alive = true
            if (snake.direction != "down")
                snake.direction = "up"
        }
        val button_down = findViewById<ImageButton>(R.id.button_down)
        button_down.setOnClickListener {
            snake.alive = true
            if (snake.direction != "up")
                snake.direction = "down"
        }
        val button_left = findViewById<ImageButton>(R.id.button_left)
        button_left.setOnClickListener {
            snake.alive = true
            if (snake.direction != "right")
                snake.direction = "left"
        }
        val button_right = findViewById<ImageButton>(R.id.button_right)
        button_right.setOnClickListener {
            snake.alive = true
            if (snake.direction != "left")
                snake.direction = "right"
        }
    }

    private fun showScoreBar() {
        if (best_score < score) {
            stroyscorebar.text = getString(R.string.best_score_text, score)
            best_score = score
            lifecycleScope.launch(Dispatchers.IO) {
                firebase.writeSnakeBestScore(best_score)
            }

        }

        scorebar.text =  getString(R.string.score_text,0)
        score = 0
        time = 150
    }
}