package com.example.generovanilesa

class GamePlan(val width: Int = 10, val height: Int = 10) {
    val map: Array<Array<Field>> = Array(height) { Array(width) { Field(Terrain.MEADOW) } }

    fun getField(pos: Position): Field {
        return map.getOrNull(pos.y)?.getOrNull(pos.x) ?: Field(Terrain.MEADOW)
    }

    fun isFree(pos: Position, gameObjects: List<GameObject>): Boolean {
        return getField(pos).terrain == Terrain.MEADOW &&
                gameObjects.none { it.position == pos }
    }

    fun generateFreeRandomPositionOnMeadow(gameObjects: List<GameObject>): Position {
        while (true) {
            val x = (0 until width).random()
            val y = (0 until height).random()
            val pos = Position(x, y)
            if (isFree(pos, gameObjects)) return pos
        }
    }
}
