package ee.taltech.sudoku.sudokulib

import android.util.Log

class SudokuBrain {
    val gameboardeasy1 = arrayListOf("---68-19-", "26--7---4", "7-1-9-5--", "82---4-5-", "1--6-2--3", "-4-9---28", "--9-4-7-3", "3---5--18", "-74-36---")
    val gameboardeasy1solution = arrayListOf("435682197", "269571834", "781493562", "826374951", "195682743", "347915628", "519248763", "326957418", "874136259")
    val gameboardmedium1 = arrayListOf("1--73----", "489-----1", "--6-4-295", "--75----6", "12-7-3-95", "6----87--", "914-2-8--", "6-----512", "----37--4")
    val gameboardmedium1solution = arrayListOf("152739468", "489256371", "376841295", "387591246", "124763895", "659428713", "914625873", "637948512", "582137964")
    val gameboardhard1 = arrayListOf("---7-----", "6----3-91", "4--6---8-", "----5----", "---18-3-6", "-----3-45", "-4-9-3-2-", "2--------", "-6----1--")
    val gameboardhard1solution = arrayListOf("581792364", "672843591", "439651782", "438256179", "957184326", "216973845", "845913627", "219768435", "367524198")

    fun getRandomBoard(difficulty: String): ArrayList<String> {
        if (difficulty == "easy") {
            return gameboardeasy1
        } else if (difficulty == "medium") {
            return gameboardmedium1
        } else if (difficulty == "hard") {
            return gameboardhard1
        }
        return gameboardeasy1solution
    }

    fun checkIfSolved(gameBoard: ArrayList<String>): Boolean {
        val gameBoardSolutions = arrayOf(gameboardeasy1solution, gameboardmedium1solution, gameboardhard1solution)
        Log.d(LOGTAG, "Checking")
        Log.d(LOGTAG, gameBoard.toString())
        for (solution in gameBoardSolutions) {
            Log.d(LOGTAG, solution.toString())
            if (solution == gameBoard) {
                return true
            }
        }
        return false
    }
}