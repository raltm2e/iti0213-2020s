package ee.taltech.sudoku

class GameState {
    var id: Int = 0
    var gameBoard: String = ""
    var difficulty: String = ""
    var timeSpent: Int = 0
    var gameFinished: Boolean = false

    constructor(gameBoard: String, scoreBoard: String, timeSpent: Int, gameFinished: Boolean): this(0, gameBoard, scoreBoard, timeSpent, gameFinished)

    constructor(id: Int, gameBoard: String, difficulty: String, timeSpent: Int, gameFinished: Boolean) {
        this.id = id
        this.gameBoard = gameBoard
        this.difficulty = difficulty
        this.timeSpent = timeSpent
        this.gameFinished = gameFinished
    }
}
