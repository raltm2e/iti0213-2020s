package ee.taltech.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import ee.taltech.sudoku.sudokulib.LOGTAG
import ee.taltech.sudoku.sudokulib.SudokuBrain
import kotlinx.android.synthetic.main.game_statistics.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var gameStateRepository: GameStateRepository
    private var gameBrain = SudokuBrain()
    private var boardButtons = ArrayList<ArrayList<Button>>()
    private var difficulty = "easy"
    private var timeSpentInSeconds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameStateRepository = GameStateRepository(this).open()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameStateRepository.close()
    }

    private fun resetBoard() {
        if (boardButtons.size <= 0) {
            initButtons()
        }
        for (smallBoard in boardButtons) {
            for (button in smallBoard) {
                button.setOnClickListener(null)
            }
        }
    }

    fun generateGameBoard(view: View) {
        if (boardButtons.size <= 0) {
            initButtons()
        }
        resetBoard()
        Log.d(LOGTAG, "Generating board")
        val generatedBoard = gameBrain.getRandomBoard("solution")
        var i = 0
        var j = 0
        for (smallBoard in boardButtons) {
            for (button in smallBoard) {
                button.text = generatedBoard[j][i].toString()
                if (generatedBoard[j][i].toString() == "-") {
                    button.setOnClickListener {
                        if (button.text == "-") {
                            button.text = "0"
                        }
                        val buttonValue = button.text.toString().toInt() + 1
                        if (buttonValue == 10) {
                            button.text = "-"
                        }
                        button.text = buttonValue.toString()
                    }
                }
                i += 1
            }
            i = 0
            j += 1
        }
    }

    fun validateBoard(view: View) {
        if (boardButtons.size <= 0) {
            initButtons()
        }
        // Generate array of strings to check if solution correct
        val gameBoardStrings = ArrayList<String>()
        var i = 0
        for (smallboard in boardButtons) {
            gameBoardStrings.add("")
            for (button in smallboard) {
                gameBoardStrings[i] = gameBoardStrings[i] + button.text.toString()
            }
            i += 1
        }
        Log.d(LOGTAG, gameBrain.checkIfSolved(gameBoardStrings).toString())
        if (gameBrain.checkIfSolved(gameBoardStrings)) {
            val gameBoardAsSingleString = gameBoardStrings.toString()
            gameStateRepository.add(GameState(0, gameBoardAsSingleString, difficulty, timeSpentInSeconds, 1))
        }
    }

    fun changeDifficulty(view: View) {
        Log.d(LOGTAG, "Changing difficulty from $difficulty")
        if (difficulty == "easy") {
            difficulty = "medium"
        } else if(difficulty == "medium") {
            difficulty = "hard"
        } else if (difficulty == "hard") {
            difficulty = "easy"
        }
        val textViewDifficulty = findViewById<TextView>(R.id.textViewDifficulty)
        textViewDifficulty.text = difficulty
    }

    private fun initButtons() {
        var smallList1 = ArrayList<Button>()
        smallList1.add(findViewById(R.id.button11))
        smallList1.add(findViewById(R.id.button12))
        smallList1.add(findViewById(R.id.button13))
        smallList1.add(findViewById(R.id.button14))
        smallList1.add(findViewById(R.id.button15))
        smallList1.add(findViewById(R.id.button16))
        smallList1.add(findViewById(R.id.button17))
        smallList1.add(findViewById(R.id.button18))
        smallList1.add(findViewById(R.id.button19))
        boardButtons.add(smallList1)

        smallList1 = ArrayList()
        smallList1.add(findViewById(R.id.button21))
        smallList1.add(findViewById(R.id.button22))
        smallList1.add(findViewById(R.id.button23))
        smallList1.add(findViewById(R.id.button24))
        smallList1.add(findViewById(R.id.button25))
        smallList1.add(findViewById(R.id.button26))
        smallList1.add(findViewById(R.id.button27))
        smallList1.add(findViewById(R.id.button28))
        smallList1.add(findViewById(R.id.button29))
        boardButtons.add(smallList1)

        smallList1 = ArrayList()
        smallList1.add(findViewById(R.id.button31))
        smallList1.add(findViewById(R.id.button32))
        smallList1.add(findViewById(R.id.button33))
        smallList1.add(findViewById(R.id.button34))
        smallList1.add(findViewById(R.id.button35))
        smallList1.add(findViewById(R.id.button36))
        smallList1.add(findViewById(R.id.button37))
        smallList1.add(findViewById(R.id.button38))
        smallList1.add(findViewById(R.id.button39))
        boardButtons.add(smallList1)

        smallList1 = ArrayList()
        smallList1.add(findViewById(R.id.button41))
        smallList1.add(findViewById(R.id.button42))
        smallList1.add(findViewById(R.id.button43))
        smallList1.add(findViewById(R.id.button44))
        smallList1.add(findViewById(R.id.button45))
        smallList1.add(findViewById(R.id.button46))
        smallList1.add(findViewById(R.id.button47))
        smallList1.add(findViewById(R.id.button48))
        smallList1.add(findViewById(R.id.button49))
        boardButtons.add(smallList1)

        smallList1 = ArrayList()
        smallList1.add(findViewById(R.id.button51))
        smallList1.add(findViewById(R.id.button52))
        smallList1.add(findViewById(R.id.button53))
        smallList1.add(findViewById(R.id.button54))
        smallList1.add(findViewById(R.id.button55))
        smallList1.add(findViewById(R.id.button56))
        smallList1.add(findViewById(R.id.button57))
        smallList1.add(findViewById(R.id.button58))
        smallList1.add(findViewById(R.id.button59))
        boardButtons.add(smallList1)

        smallList1 = ArrayList()
        smallList1.add(findViewById(R.id.button61))
        smallList1.add(findViewById(R.id.button62))
        smallList1.add(findViewById(R.id.button63))
        smallList1.add(findViewById(R.id.button64))
        smallList1.add(findViewById(R.id.button65))
        smallList1.add(findViewById(R.id.button66))
        smallList1.add(findViewById(R.id.button67))
        smallList1.add(findViewById(R.id.button68))
        smallList1.add(findViewById(R.id.button69))
        boardButtons.add(smallList1)

        smallList1 = ArrayList()
        smallList1.add(findViewById(R.id.button71))
        smallList1.add(findViewById(R.id.button72))
        smallList1.add(findViewById(R.id.button73))
        smallList1.add(findViewById(R.id.button74))
        smallList1.add(findViewById(R.id.button75))
        smallList1.add(findViewById(R.id.button76))
        smallList1.add(findViewById(R.id.button77))
        smallList1.add(findViewById(R.id.button78))
        smallList1.add(findViewById(R.id.button79))
        boardButtons.add(smallList1)

        smallList1 = ArrayList()
        smallList1.add(findViewById(R.id.button81))
        smallList1.add(findViewById(R.id.button82))
        smallList1.add(findViewById(R.id.button83))
        smallList1.add(findViewById(R.id.button84))
        smallList1.add(findViewById(R.id.button85))
        smallList1.add(findViewById(R.id.button86))
        smallList1.add(findViewById(R.id.button87))
        smallList1.add(findViewById(R.id.button88))
        smallList1.add(findViewById(R.id.button89))
        boardButtons.add(smallList1)

        smallList1 = ArrayList()
        smallList1.add(findViewById(R.id.button91))
        smallList1.add(findViewById(R.id.button92))
        smallList1.add(findViewById(R.id.button93))
        smallList1.add(findViewById(R.id.button94))
        smallList1.add(findViewById(R.id.button95))
        smallList1.add(findViewById(R.id.button96))
        smallList1.add(findViewById(R.id.button97))
        smallList1.add(findViewById(R.id.button98))
        smallList1.add(findViewById(R.id.button99))
        boardButtons.add(smallList1)
    }
}
