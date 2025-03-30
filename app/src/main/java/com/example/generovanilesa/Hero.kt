package com.example.generovanilesa

class Hero(
    position: Position
) : Character("Hrdina", position, 100.0, 1.2, 1.0, 0.5) {
    var kills: Int = 0
    var items = arrayListOf<Item>()

    override var attack: Double = 1.2
        get() {
            var a = field
            for (item in items) a += item.attack
            return a
        }

    override var defense: Double = 1.0
        get() {
            var d = field
            for (item in items) d += item.defense
            return d
        }

    override var healing: Double = 0.5
        get() {
            var h = field
            for (item in items) h += item.healing
            return h
        }

    override fun attack(enemy: Character): String {
        val result = super.attack(enemy)
        if (enemy.isDead()) kills += 1
        return result
    }

    fun cutDown(direction: Direction, gamePlan: GamePlan): String {
        val newPos = position.moveTo(direction)
        val field = gamePlan.getField(newPos)
        if (field.terrain == Terrain.FOREST) {
            field.terrain = Terrain.MEADOW
            return "Les v směru $direction byl pokácen."
        }
        return "V tomto směru není les."
    }

    fun addItem(item: Item): String {
        items.add(item)
        item.pickedUp = true
        return "Předmět ${item.name} sebrán."
    }

    override fun toString(): String {
        val desc = StringBuilder()
        desc.append("Zdraví: %.2f\n".format(health))
        desc.append("Útok: %.2f\n".format(attack))
        desc.append("Obrana: %.2f\n".format(defense))
        desc.append("Uzdravování: %.2f\n".format(healing))
        desc.append("Zabití: $kills\n")
        if (items.isNotEmpty()) {
            desc.append("Předměty: ${items.joinToString { it.name }}\n")
        }
        return desc.toString()
    }
}