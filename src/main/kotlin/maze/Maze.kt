package maze

import maze.command.Command
import org.bukkit.plugin.java.JavaPlugin

class Maze : JavaPlugin() {
    override fun onEnable() {
        getCommand("미로생성")?.setExecutor(Command())
    }
}
