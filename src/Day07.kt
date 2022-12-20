fun main() {
    data class File(
        val name: String,
        val size: Int
    )

    data class Dir(
        val parent: Dir?,
        val name: String,
        val isDeleted: Boolean = false,
        val dirs: MutableList<Dir> = mutableListOf(),
        val files: MutableList<File> = mutableListOf()
    ) {
        val size: Int get() = files.sumOf { it.size } + dirs.sumOf { it.size }

        fun getSumDirSizesAtMost(atMost: Int): Int {
            var sumSize = 0

            if (size < atMost) {
                sumSize += size
            }

            sumSize += dirs.sumOf { it.getSumDirSizesAtMost(atMost) }

            return sumSize
        }

        fun getAllDirs(): List<Dir> {
            val allDirs = mutableListOf<Dir>()

            allDirs.addAll(dirs)

            dirs.forEach {
                allDirs.addAll(it.getAllDirs())
            }

            return allDirs
        }
    }

    fun parseRoot(input: List<String>): Dir {
        val root = Dir(null, "/")
        var currentDir = root

        // $ cd ..
        // $ cd tfwgg
        // $ ls
        // dir ddnfmsjc
        // 252616 fvj

        input
            .filter { it != "" }
            .forEach { command ->
                when {
                    command == "$ cd /" -> Unit
                    command == "$ ls" -> Unit
                    command == "$ cd .." -> {
                        currentDir = currentDir.parent!!
                    }

                    command.startsWith("$ cd ") -> {
                        val dirName = command.substring(5)
                        currentDir = currentDir.dirs.first { it.name == dirName } // hashmaps would be better
                    }

                    command.startsWith("dir ") -> {
                        val dirName = command.substring(4)
                        currentDir.dirs.add(Dir(currentDir, dirName))
                    }

                    else -> {
                        val (size, fileName) = command.split(" ")
                        currentDir.files.add(File(fileName, size.toInt()))
                    }
                }
            }

        return root
    }

    fun part1(input: List<String>): Int = parseRoot(input)
        .getSumDirSizesAtMost(100000)

    fun part2(input: List<String>): Int {
        val root = parseRoot(input)
        val spaceLeft = 70000000 - root.size
        val needMoreSpace = 30000000 - spaceLeft

        return root
            .getAllDirs()
            .sortedBy { it.size }
            .filter { it.size >= needMoreSpace }
            .first()
            .size
    }

    val testInput = readInput("Day07_test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
