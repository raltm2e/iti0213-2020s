package ee.taltech.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import ee.taltech.sudoku.sudokulib.GRID_SIZE
import ee.taltech.sudoku.sudokulib.Level
import ee.taltech.sudoku.sudokulib.Sudoku

class MainActivity : AppCompatActivity() {
    private lateinit var gameStateRepository: GameStateRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameStateRepository = GameStateRepository(this).open()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameStateRepository.close()
    }

    fun generateGameBoard(view: View) {
        val layouts: ConstraintLayout = findViewById(R.id.gameboard)
        val gameBoardMapSmall: MutableMap<Int, MutableList<Button>> = mutableMapOf<Int, MutableList<Button>>()
        for (i in 0 until layouts.childCount) {
            val buttons9: MutableList<Button> = ArrayList()
            // Get elements of single 3x3 square, add them to a list of buttons
            // This is necessary to add values to them obtained by board generator
            val singleSquare = layouts.getChildAt(i) as ConstraintLayout
            for (j in 0 until singleSquare.childCount) {
                buttons9.add(singleSquare.getChildAt(j) as Button)
            }
            // Add buttons into dictionary so they are in order
            gameBoardMapSmall[i + 1] = buttons9
        }

        // Generate a sudoku board
        val generatedSudokuBoard = Sudoku.Builder().setLevel(Level.JUNIOR).build()
        val generatedGameArray = generatedSudokuBoard.grid

        // Add generated values to each buttons text
        // GRID_SIZE + 1 is because button id starts from 11 and ends at 99
        for (i in 1 until GRID_SIZE + 1) {
            for (j in 1 until GRID_SIZE + 1) {
                val buttonList9: MutableList<Button> = gameBoardMapSmall[i]!!
                val buttonToChange: Button = buttonList9[j]
                buttonToChange.text = generatedGameArray[i][j].toString()
//                Log.d("ButtonID", buttonId)
//                buttonId.text = generatedGameArray[i][j].toString()
            }
        }
    }

    private fun getButtonById(id: String, buttons: MutableList<Button>): Button {
        for (i in 0 until buttons.size) {
            Log.d("ButtonID", buttons[i].id.toString())
            if (buttons[i].id.toString() == id) {
                return buttons[i]
            }
        }
        // I don't know how to return empty button
        // This is just a placeholder, it should never go here
        Log.d("ERROR", "getButtonById cant get correct amount of buttons to iterate")
        return buttons[-1]
    }

    fun buttonPressed(view: View) {
        val button1 = view as Button
        button1.text = iterateNumber(button1)
    }

    fun iterateNumber(button: Button): String {
        if (button.text == "") {
            return "1"
        }
        val buttonValue = button.text.toString().toInt() + 1
        if (buttonValue == 10) {
            return ""
        }
        return buttonValue.toString()
    }
}
