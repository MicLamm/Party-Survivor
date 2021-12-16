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
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

class RecyclerCoktail(var listData: List<Coktail>): RecyclerView.Adapter<RecyclerCoktail.ViewHolder>(), Filterable {
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

        fun isFavoris(coktail: Coktail, buttonAddFavori: Button) {
            val auth: FirebaseAuth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser?.uid
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("favoris/" + currentUser.toString())

            ref.get().addOnCompleteListener { task ->
                var coktails: java.util.ArrayList<Coktail> = java.util.ArrayList()
                if (!task.isSuccessful) {

                    println("firebase" + "Error getting data" + task.exception)

                } else {

                    val snapshotResult = task.result
                    for (snapshot in snapshotResult!!.children) {
                        var coktail = snapshot.getValue(Coktail::class.java)
                        if (coktail != null) {
                            coktails.add(coktail)
                        }
                    }
                }
                val coktail_details: List<Coktail> = coktails;
                for (item in coktails) {
                    System.out.println("COKTAIL NAME : " + item.coktailName)
                    if (coktail.coktailName.equals(item.coktailName)) {
                        System.out.println("LE COKTAIL EST UN FAVORIS ? true")
                        buttonAddFavori.setText(R.string.RemoveFavoriButton)
                    }
                }
            }
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView


        init {
            dataSet = listData as ArrayList<Coktail>
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
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

                context.startActivity(intent)

            }

        }
    }
}