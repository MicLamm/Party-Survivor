package uqac.dim.partysurvivor

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filter.FilterResults
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class RecyclerGame(private var listDataGame: List<Game>) : RecyclerView.Adapter<RecyclerGame.ViewHolder>(), Filterable {

    lateinit var context: Context
    var dataGame = Game()
    var dataSet: ArrayList<Game> = listDataGame as ArrayList<Game>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("ginga","je suis BIEN DANS RECYCLER")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.game_layout, parent, false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = listDataGame[position].gameName
        //holder.itemDetail.text = listDataGame[position].details
        Glide.with(context).load(listDataGame[position].imageUrl).into(holder.itemImage)
        dataGame = listDataGame[position]


    }

    override fun getItemCount(): Int {
        return listDataGame.size
    }

    override fun getFilter(): Filter {
        return Searched_Filter
    }

    private val Searched_Filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: ArrayList<Game> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(listDataGame)
            } else {
                val filterPattern = constraint.toString().lowercase().trim { it <= ' ' }
                for (item in listDataGame) {
                    if (item.getGameName().lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            System.out.println(listDataGame.size)
            System.out.println(filteredList.size)
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {

            dataSet.clear()
            dataSet.addAll(results.values as ArrayList<Game>)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        //var itemDetail: TextView

        init {
            dataSet = listDataGame as ArrayList<Game>
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            //itemDetail = itemView.findViewById(R.id.item_detail)

            itemView.setOnClickListener{
                for(game in dataSet){
                    if(game.gameName.equals(itemTitle.text)){
                        dataGame = game
                    }
                }
                val intent = Intent( context, GamePreview::class.java)
                intent.putExtra("position", position)
                intent.putExtra("dataGame", dataGame)

                context.startActivity(intent)
            }
        }
    }
}