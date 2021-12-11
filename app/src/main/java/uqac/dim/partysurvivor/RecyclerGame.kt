package uqac.dim.partysurvivor

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerGame( var listDataGame: List<Game>) : RecyclerView.Adapter<RecyclerGame.ViewHolder>() {

    lateinit var context: Context
    var dataGame = Game()

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

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        //var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            //itemDetail = itemView.findViewById(R.id.item_detail)

            itemView.setOnClickListener{
                for(game in listDataGame){
                    if(game.gameName.equals(itemTitle.text)){
                        dataGame = game
                    }
                }
                val intent = Intent( context, CocktailPreview::class.java)
                intent.putExtra("position", position)
                intent.putExtra("dataGame", dataGame)

                context.startActivity(intent)
            }
        }
    }
}