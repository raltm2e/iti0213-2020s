package ee.taltech.sudoku

class GameState(
    var id: Int,
    var gameBoard: String,
    var difficulty: String,
    var timeSpent: Long,
    var gameFinished: Int
)
