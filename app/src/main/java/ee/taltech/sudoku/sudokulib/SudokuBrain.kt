package ee.taltech.sudoku.sudokulib

import android.util.Log

class SudokuBrain {
    val gameboardeasy1 = arrayOf("---68-19-", "26--7---4", "7-1-9-5--", "82---4-5-", "1--6-2--3", "-4-9---28", "--9-4-7-3", "3---5--18", "-74-36---")
    val gameboardeasy1solution = arrayOf("435682197", "269571834", "781493562", "826374951", "195682743", "347915628", "519248763", "326957418", "874136259")

    fun getRandomBoard(difficulty: String): Array<String> {
        val easyBoards = arrayOf(gameboardeasy1)
        val randomNumber = (easyBoards.indices).random()
        Log.d(LOGTAG, randomNumber.toString())
        return easyBoards[randomNumber]
    }

    fun checkIfSolved(gameBoard: ArrayList<String>): Boolean {
        val gameBoardSolutions = arrayOf(gameboardeasy1solution)
        var correctCounter = 0
        for (solution in gameBoardSolutions) {
            for (i in 0..9) {
                if (solution[i] == gameBoard[i])
                    correctCounter += 1
            }
            if (correctCounter == 9) {
                return true
            }
            correctCounter = 0
        }
        return false
    }
}