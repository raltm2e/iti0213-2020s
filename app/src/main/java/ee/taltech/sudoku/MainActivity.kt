package ee.taltech.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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
}
