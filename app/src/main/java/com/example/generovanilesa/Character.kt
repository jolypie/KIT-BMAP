package com.example.generovanilesa

open class Character(
    val name: String,
    var position: Position,
    var health: Double,
    open var attack: Double,
    open var defense: Double,
    open var healing: Double
) {
    open fun isDead(): Boolean = health <= 0

    open fun attack(enemy: Character): String {
        var realAttack = attack - enemy.defense
        if (realAttack < 0) realAttack = 0.0
        enemy.health -= realAttack
        if (enemy.isDead()) return "${enemy.name} je mrtvý."
        return "$name zaútočil silou %.2f. Zdraví soupeře je %.2f.".format(realAttack, enemy.health)
    }
}