package ee.taltech.sudoku

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ee.taltech.sudoku.gameutility.GameUtility
import ee.taltech.sudoku.sudokulib.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var gameStateRepository: GameStateRepository
    private var gameBrain = SudokuBrain()
    private var gameUtility = GameUtility()
    private var boardButtons = ArrayList<ArrayList<Button>>()
    private var difficulty = "easy"
    private var timeRunInSeconds = 0L
    private var timeRunInMillis = 0L
    private var gameActive = false
    private var lapTime = 0L

    private var showPreviousSession = false
    private lateinit var sessionToDraw: GameState
    private var gson = Gson()


    private fun startTimer() {
        var lastSecondTimestamp = 0L
        var previousTimeInterval = 0L
        val timeStarted = System.currentTimeMillis()
        gameActive = true
        Log.d(LOGTAG, "Timer started")
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(LOGTAG, timeRunInSeconds.toString())
            while(gameActive) {
                lapTime = System.currentTimeMillis() - timeStarted
                timeRunInMillis += lapTime - previousTimeInterval
                previousTimeInterval = lapTime
                if(timeRunInMillis >= lastSecondTimestamp + 1000L) {
                    timeRunInSeconds += 1
                    lastSecondTimestamp += 1000L
                }
                val textViewTime = findViewById<TextView>(R.id.textViewTime)
                val formattedTime = gameUtility.getFormattedStopWatchTime(timeRunInMillis)
                textViewTime.text = formattedTime
                delay(TIMER_UPDATE_INTERVAL)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameStateRepository = GameStateRepository(this).open()
        updateHighscore()

        LocalBroadcastManager.getInstance(this).registerReceiver(previousSessionBroadcastReceiver, IntentFilter(
            UPDATE_GAME_INTENT)
        )

        // Set navigation to loading
        val loadButton = findViewById<Button>(R.id.buttonNavigateLoading)
        loadButton.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)
        }
    }

    var previousSessionBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            showPreviousSession = intent.getBooleanExtra(LOAD_GAME, false)
            sessionToDraw = gson.fromJson(intent.getStringExtra(SESSION_DISPLAY), GameState::class.java)
            Log.d(LOGTAG, "Should Show Previous Session: ${showPreviousSession}")
            drawGameFromSession()
        }
    }

    private fun drawGameFromSession() {
        resetBoard()
        Log.d(LOGTAG, "Drawing game from session: ${sessionToDraw.gameBoard}")
        val gameBoardAsList = sessionToDraw.gameBoard.split(", ")
        val gameBoardAsArrayList = ArrayList<String>(gameBoardAsList)
        for (i in 0 until gameBoardAsArrayList.size) {
            gameBoardAsArrayList[i] = gameBoardAsArrayList[i].replace("[", "").replace("]", "")
        }
        Log.d(LOGTAG, "Session as array of strings: $gameBoardAsArrayList")

        drawGameBoard(gameBoardAsArrayList)
        timeRunInMillis = sessionToDraw.timeSpent * 1000
        timeRunInSeconds = sessionToDraw.timeSpent
        startTimer()
    }

    private fun updateHighscore(): GameState {
        val allGames = gameStateRepository.getAll()
        var smallestTime = 999999999999999L
        var smallestGame = GameState(0, "", "placeholder", 0L, 1)
        if (allGames.isNotEmpty()) {
            for (game in allGames) {
                if (game.timeSpent < smallestTime) {
                    smallestTime = game.timeSpent
                    smallestGame = game
                }
            }
        }
        return smallestGame
    }

    override fun onDestroy() {
        super.onDestroy()
        gameStateRepository.close()
    }

    private fun resetBoard() {
        gameActive = false
        if (boardButtons.size <= 0) {
            initButtons()
        }
        for (smallBoard in boardButtons) {
            for (button in smallBoard) {
                button.setOnClickListener(null)
            }
        }
    }

    fun startGame(view: View) {
        if (boardButtons.size <= 0) {
            initButtons()
        }
        Thread.sleep(1000)
        resetBoard()
        generateGameBoard()
        timeRunInMillis = 0L
        timeRunInSeconds = 0L
        startTimer()
    }

    private fun generateGameBoard() {
        Log.d(LOGTAG, "Generating board")
        val generatedBoard = gameBrain.getRandomBoard("solution")
        drawGameBoard(generatedBoard)
    }

    private fun drawGameBoard(generatedBoard: ArrayList<String>) {
        Log.d(LOGTAG, "Drawing gameboard")
        if (boardButtons.size <= 0) {
            initButtons()
        }
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
        val gameBoardStrings = generateCurrentGameBoard()

        Log.d(LOGTAG, gameBrain.checkIfSolved(gameBoardStrings).toString())
        if (gameBrain.checkIfSolved(gameBoardStrings)) {
            val gameBoardAsSingleString = gameBoardStrings.toString()
            gameStateRepository.add(GameState(0, gameBoardAsSingleString, difficulty, timeRunInSeconds, 1))
            endGame()
        }
    }

    private fun generateCurrentGameBoard(): ArrayList<String> {
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
        return gameBoardStrings
    }

    private fun endGame() {
        gameActive = false
        updateHighscore()
    }

    fun saveGame(view: View) {
        if (gameActive) {
            val gameBoardStrings = generateCurrentGameBoard()
            gameStateRepository.add(GameState(0, gameBoardStrings.toString(), difficulty, timeRunInSeconds, 0))
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
