package ru.maximus.playzer.SnakeGame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val snakeHead = Paint()
        snakeHead.setColor(Color.GREEN)
        val snakeBody = Paint()
        snakeBody.setColor(Color.YELLOW)

        val food = Paint()
        food.setColor(Color.RED)
        val level = Paint()
        level.setColor(Color.DKGRAY)

        // Задняя часть картинки
        canvas?.drawRect(0f,0f,1050f,1050f,level)

        // Змея из массива
        // left x, top y, right x+50, bottom y +50

        for (i in Snake.bodyParts){
            canvas?.drawRect(i[0], i[1], i[0]+45, i[1]+45, snakeBody)
        }
        canvas?.drawRect(Snake.bodyParts[Snake.bodyParts.size-1][0], Snake.bodyParts[Snake.bodyParts.size-1][1], Snake.bodyParts[Snake.bodyParts.size-1][0]+45, Snake.bodyParts[Snake.bodyParts.size-1][1]+45, snakeHead)
        // еда из массива
        // left x, top y, right x+50, bottom y +50
        canvas?.drawRect(Food.posX, Food.posY, Food.posX +45, Food.posY +45,food)


    }
}