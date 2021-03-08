package ee.taltech.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import ee.taltech.sudoku.sudokulib.GRID_SIZE
import ee.taltech.sudoku.sudokulib.Level
import ee.taltech.sudoku.sudokulib.Sudoku

class MainActivity : AppCompatActivity() {
    private lateinit var gameStateRepository: GameStateRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameStateRepository = GameStateRepository(this).open()


        var generatedSudokuBoard = Sudoku.Builder().setLevel(Level.JUNIOR).build()
        Log.d("BOARD", generatedSudokuBoard.grid[0].contentToString())
        generateGameBoard(generatedSudokuBoard.grid)
    }

    override fun onDestroy() {
        super.onDestroy()
        gameStateRepository.close()
    }

    private fun generateGameBoard(generatedGameArray: Array<IntArray>) {


        for (i in 0 until GRID_SIZE) {
            for (j in 0 until GRID_SIZE) {
                Log.d("ELEMENT", generatedGameArray[i][j].toString())
            }
        }
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
