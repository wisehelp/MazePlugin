package maze.maze

class MazeGenerator(val w: Int, val h: Int) {

    val maze = Array(h) { BooleanArray(w) { false } }
    val dx = intArrayOf(0, 2, 0, -2)
    val dy = intArrayOf(-2, 0, 2, 0)


    fun generate() {
        for (y in 0 until h) for (x in 0 until w) maze[y][x] = false
        dfs(1,1)
    }


    fun dfs(x: Int, y: Int) {
        maze[y][x] = true
        val dir = listOf(0,1,2,3).shuffled()
        for (i in dir) {
            val nx = x + dx[i]
            val ny = y + dy[i]
            if (nx in 1 until w && ny in 1 until h && nx % 2 == 1 && ny % 2 == 1 && !maze[ny][nx]) {
                maze[(y + ny) / 2][(x + nx) / 2] = true
                dfs(nx, ny)
            }
        }
    }
}
