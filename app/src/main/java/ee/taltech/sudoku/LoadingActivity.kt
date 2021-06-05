package ee.taltech.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ee.taltech.sudoku.other.RecyclerViewAdapterCustom
import kotlinx.android.synthetic.main.activity_loading.*

class LoadingActivity : AppCompatActivity() {

    private lateinit var adapterCustom: RecyclerView.Adapter<*>
    private lateinit var repository: GameStateRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        repository = GameStateRepository(this).open()
        savedGames.layoutManager = LinearLayoutManager(this)
        adapterCustom = RecyclerViewAdapterCustom(this, repository)
        savedGames.adapter = adapterCustom
    }
}