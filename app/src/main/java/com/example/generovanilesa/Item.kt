package com.example.generovanilesa

class Item(
    name: String,
    position: Position,
    var pickedUp: Boolean = false,
    val health: Double,
    val attack: Double,
    val defense: Double,
    val healing: Double
) : GameObject(name, position)