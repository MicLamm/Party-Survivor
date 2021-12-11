package uqac.dim.partysurvivor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.Serializable

class RecyclerCoktail(var listData: List<Coktail>): RecyclerView.Adapter<RecyclerCoktail.ViewHolder>(), Filterable {

    //private var titles = arrayOf("Cocktail One", "Cocktail Two", "Cocktail Three", "Cocktail Four", "Cocktail Five","Cocktail Six" )

    //private var details = arrayOf("Detail of cocktail 1", "Detail of cocktail 2", "Detail of cocktail 3", "Detail of cocktail 4", "Detail of cocktail 5", "Detail of cocktail 6")

    //private var images = intArrayOf(R.drawable.blue_lagoon, R.drawable.gin_tonic, R.drawable.margarita, R.drawable.rhum_cola, R.drawable.sex_on_the_beach, R.drawable.mojito)

    lateinit var context: Context

    var data = Coktail()
    var dataSet: ArrayList<Coktail> = listData as ArrayList<Coktail>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        System.out.println("je suis BIEN DANS RECYCLER")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cocktail_layout, parent, false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemTitle.text = listData[position].coktailName
        holder.itemDetail.text = listData[position].detailsCoktail
        Glide.with(context).load(listData[position].imageUrl).into(holder.itemImage)
        data = listData[position]
    //holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getFilter(): Filter {
        return Searched_Filter
    }

    private val Searched_Filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: ArrayList<Coktail> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(listData)
            } else {
                val filterPattern = constraint.toString().lowercase().trim { it <= ' ' }
                for (item in listData) {
                    if (item.coktailName.lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            System.out.println(listData.size)
            System.out.println(filteredList.size)
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {

            dataSet.clear()
            dataSet.addAll(results.values as ArrayList<Coktail>)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView


        init {
            dataSet = listData as ArrayList<Coktail>
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)



            //var bundle : Bundle = Bundle()
            //bundle.putParcelableArrayList("listData", listData)



            itemView.setOnClickListener {
                for (coktail in listData) {
                    if (coktail.coktailName.equals(itemTitle.text)) {
                        data = coktail
                    }
                }
                val intent = Intent(context, CocktailPreview::class.java)
                intent.putExtra("position", position)
                intent.putExtra("data", data)
                intent.putExtra("type", "coktail")

                context.startActivity(intent)


                //val position: Int =adapterPosition

                //Toast.makeText(itemView.context, "you clicked on ${titles[position]}", Toast.LENGTH_LONG).show()
            }

        }
    }
}