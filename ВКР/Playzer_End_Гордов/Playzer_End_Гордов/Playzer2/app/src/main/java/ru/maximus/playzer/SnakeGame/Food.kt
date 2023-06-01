package ru.maximus.playzer.SnakeGame

class Food {
    companion object {
        var posX = 400f
        var posY = 400f

        fun generate() {
            posX = (1..20).random().toFloat() * 50
            posY = (1..20).random().toFloat() * 50
        }
    }
}