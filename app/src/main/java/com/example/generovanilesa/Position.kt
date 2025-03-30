package com.example.generovanilesa

data class Position(val x: Int, val y: Int) {
    fun moveTo(direction: Direction): Position = when (direction) {
        Direction.NORTH -> copy(y = y - 1)
        Direction.SOUTH -> copy(y = y + 1)
        Direction.EAST  -> copy(x = x + 1)
        Direction.WEST  -> copy(x = x - 1)
    }
}
