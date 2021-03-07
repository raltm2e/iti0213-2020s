package ee.taltech.sudoku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DataRecyclerViewAdapter(val context: Context, val repo: GameStateRepository) : RecyclerView.Adapter<DataRecyclerViewAdapter.ViewHolder>() {
    lateinit var dataSet: List<GameState>

    fun refreshData() {
        dataSet = repo.getAll()
    }

    init {
        refreshData()
    }

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = layoutInflater.inflate(R.layout.row_view, parent, false)
        return ViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = dataSet.get(position)

        holder.itemView.textViewId.text = person.id.toString()
        holder.itemView.textViewFirstName.text = person.gameBoard
        holder.itemView.textViewLastName.text = person.difficulty
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}
