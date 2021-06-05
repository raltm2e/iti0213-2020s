package ee.taltech.sudoku.other

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ee.taltech.sudoku.GameState
import ee.taltech.sudoku.GameStateRepository
import ee.taltech.sudoku.R
import ee.taltech.sudoku.gameutility.GameUtility
import ee.taltech.sudoku.sudokulib.LOAD_GAME
import kotlinx.android.synthetic.main.rowview.view.*

class RecyclerViewAdapterCustom(val context: Context, val repository: GameStateRepository): RecyclerView.Adapter<RecyclerViewAdapterCustom.ViewHolder>() {

    lateinit var dataSet: List<GameState>
    private val gameUtility = GameUtility()

    fun refreshData() {
        dataSet = repository.getAll()
    }

    init {
        refreshData()
    }

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = layoutInflater.inflate(R.layout.rowview, parent, false)
        return ViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val values = dataSet[position]
        holder.itemView.textViewId.text = values.id.toString()
        holder.itemView.textViewDifficultySaved.text = values.difficulty
        val formattedTime = gameUtility.getFormattedStopWatchTime(values.timeSpent * 1000)
        holder.itemView.textViewTimeSpent.text = formattedTime

        // Add onclick to open button
        val openButton = holder.itemView.buttonLoadGame
        val gson = Gson()
//        openButton.setOnClickListener {
//            val intent = Intent(LOAD_GAME)
//            intent.putExtra(MAP_SHOW, true)
//            intent.putExtra(SESSION_DISPLAY, gson.toJson(values))
//            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
//        }

    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}