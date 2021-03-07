package ee.taltech.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var gameStateRepository: GameStateRepository
    private lateinit var adapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameStateRepository = GameStateRepository(this).open()

        recyclerViewPersons.layoutManager = LinearLayoutManager(this)
        adapter = DataRecyclerViewAdapter(this, gameStateRepository)
        recyclerViewPersons.adapter = adapter
    }

    fun buttonAddToDbClicked(view: View) {
        Log.d("db", "buttonAddToDbClicked")
        gameStateRepository.add(
                GameState(editTextTextPersonFirstName.text.toString(),
                        editTextTextPersonLastName.text.toString())
        )

        var persons = gameStateRepository.getAll()

        var iterator = persons.iterator()
        while (iterator.hasNext()){
            var p = iterator.next()
            Log.d("db", p.gameBoard + " " + p.difficulty)
        }

        (adapter as DataRecyclerViewAdapter).refreshData()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameStateRepository.close()
    }
}
