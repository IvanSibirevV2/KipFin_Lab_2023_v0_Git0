package ru.maximus.playzer.SnakeGame

class Snake {
    // по умолчанию: одна часть тела
    var headX = 0f
    var headY = 0f


    var direction = "right";
    var alive = false;


    fun possibleMove(): Boolean {
        if (headX < 0f || headX > 1000f || headY < 0f || headY > 1000f)
            return false
        return true
    }

    fun reset() {
        headX = 0f;
        headY = 0f;
        bodyParts = mutableListOf(arrayOf(0f, 0f))
        direction = "right";
    }

    companion object {
        var bodyParts =
            mutableListOf(arrayOf(0f, 0f))
    }
}