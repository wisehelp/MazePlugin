package maze.command

import maze.maze.MazeGenerator
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.Material

class Command : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("플레이어만 사용할 수 있습니다.")
            return true
        }

        val player = sender

        var width = 5
        var height = 5

        var blockType = Material.STONE

        if (args.size >= 2) {
            try {
                width = args[0].toInt()
                height = args[1].toInt()

                if (width < 5) width = 5
                if (height < 5) height = 5

                if (width % 2 == 0) width += 1
                if (height % 2 == 0) height += 1

            }
            catch (e: NumberFormatException) {
                player.sendMessage("가로와 세로는 숫자로 입력해주세요.")
                return true
            }
        }

        if (args.size >= 3) {
            val mat = try {
                Material.valueOf(args[2].uppercase())
            }
            catch (e: IllegalArgumentException) {
                player.sendMessage("블록 이름이 잘못되었습니다! (Stone으로 대체합니다)")
                Material.STONE
            }
            blockType = mat
        }

        val mazeGen = MazeGenerator(width, height)
        mazeGen.generate()

        val baseLoc = player.location.block.location


        for (y in 0 until height) {
            for (x in 0 until width) {
                val baseX = baseLoc.blockX + x
                val baseY = baseLoc.blockY
                val baseZ = baseLoc.blockZ + y

                val block = baseLoc.world?.getBlockAt(baseX, baseY, baseZ)
                val block2 = baseLoc.world?.getBlockAt(baseX, baseY + 1, baseZ)

                if (block != null && block2 != null) {
                    if (mazeGen.maze[y][x]) {
                        block.type = Material.AIR
                        block2.type = Material.AIR
                    }
                    else {
                        block.type = blockType
                        block2.type = blockType
                    }
                }
            }
        }


        player.sendMessage("미로가 생성되었습니다 크기: $width x $height, 블록: ${blockType.name}")

        return true
    }
}
