package com.example.generovanilesa

class Game {
    val hero = Hero(Position(1, 1))
    val enemies = arrayListOf<Enemy>()
    val items = arrayListOf<Item>()
    val gameObjects = arrayListOf<GameObject>()
    val gamePlan = GamePlan()
    var score = 0
    var command = ""
    val possibleCommands = arrayListOf<String>()

    init {
        generateItems()
    }

    fun generateItems() {
        val itemList = listOf(
            Item("Mec", gamePlan.generateFreeRandomPositionOnMeadow(gameObjects), false, 0.0, 4.0, 1.0, 0.0),
            Item("Dyka", gamePlan.generateFreeRandomPositionOnMeadow(gameObjects), false, 0.0, 2.0, 1.0, 0.0),
            Item("Stit", gamePlan.generateFreeRandomPositionOnMeadow(gameObjects), false, 0.0, 0.0, 2.0, 0.0),
            Item("Helma", gamePlan.generateFreeRandomPositionOnMeadow(gameObjects), false, 0.0, 0.0, 1.0, 0.0),
            Item("Brneni", gamePlan.generateFreeRandomPositionOnMeadow(gameObjects), false, 0.0, 0.0, 3.0, 0.0),
            Item("Lekarna", gamePlan.generateFreeRandomPositionOnMeadow(gameObjects), false, 0.0, 0.0, 0.0, 1.0),
        )
        items.addAll(itemList)
        gameObjects.addAll(itemList)
    }

    fun getEnemyOnGameField(pos: Position): Enemy? = enemies.find { it.position == pos }
    fun getItemOnGameField(pos: Position): Item? = items.find { it.position == pos && !it.pickedUp }

    fun allEnemiesDead(): Boolean = enemies.all { it.isDead() }

    fun enemyAttack(): String {
        val enemy = getEnemyOnGameField(hero.position)
        return if (enemy != null && !enemy.isDead()) enemy.attack(hero) else ""
    }

    fun isGameFinished(): String {
        if (hero.isDead()) return "Jsi mrtvý."
        if (allEnemiesDead()) return "Všichni nepřátelé jsou mrtví. Vyhrál jsi. Potřeboval jsi $score tahů."
        if (command.uppercase() == "KONEC") return "Konec hry."
        return ""
    }

    fun setPossibleCommands() {
        possibleCommands.clear()
        possibleCommands.add("konec")

        val enemy = getEnemyOnGameField(hero.position)
        if (enemy != null && !enemy.isDead()) possibleCommands.add("utok")

        for (dir in Direction.values()) {
            val pos = hero.position.moveTo(dir)
            val field = gamePlan.getField(pos)
            if (field.terrain == Terrain.FOREST) possibleCommands.add("kacej${dir.name.lowercase()}")
        }

        val item = getItemOnGameField(hero.position)
        if (item != null) possibleCommands.add(item.name.lowercase())
    }

    fun getSurroundingDescription(): String {
        val desc = StringBuilder()
        val enemy = getEnemyOnGameField(hero.position)
        if (enemy != null && !enemy.isDead()) desc.append("\nPozor ${enemy.name}.")
        if (enemy != null && enemy.isDead()) desc.append("\nNa zemi vidíš mrtvolu ${enemy.name}.")

        val item = getItemOnGameField(hero.position)
        if (item != null) desc.append("\nNa zemi leží předmět ${item.name}.")

        return desc.toString()
    }

    fun runCommand(cmd: String): String {
        command = cmd
        score++
        val enemy = getEnemyOnGameField(hero.position)
        val item = getItemOnGameField(hero.position)

        return when {
            cmd == "utok" && enemy != null -> hero.attack(enemy)
            cmd.startsWith("kacej") -> {
                val dir = Direction.valueOf(cmd.removePrefix("kacej").uppercase())
                hero.cutDown(dir, gamePlan)
            }
            item != null && cmd == item.name.lowercase() -> hero.addItem(item)
            cmd == "konec" -> "Konec hry."
            else -> "Neznámý příkaz."
        }
    }

    fun run() {
        var message: String
        do {
            setPossibleCommands()
            println(getSurroundingDescription())
            println("Možné příkazy: $possibleCommands")
            command = readLine()?.trim() ?: ""
            println(runCommand(command))
            println(enemyAttack())
            message = isGameFinished()
            if (message.isNotEmpty()) {
                println(message)
                break
            }
            // heroHealing() если есть
        } while (true)
    }

    init {
        generateItems()
        items.firstOrNull()?.let {
            hero.addItem(it)
        }
    }

}