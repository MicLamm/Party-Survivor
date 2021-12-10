package uqac.dim.partysurvivor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class FeaturedDrink : AppCompatActivity() {
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerCoktail.ViewHolder>? = null
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_featured)


        val database = FirebaseDatabase.getInstance()
        val refAlcool = database.getReference("coktail")
        refAlcool.get().addOnCompleteListener { task ->
            var coktails : ArrayList<Coktail> = ArrayList()
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
            val game_details: List<Game> = Arrays.asList()
            recyclerView = findViewById(R.id.recyclerView)
            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            adapter = RecyclerCoktail(coktail_details, "coktail", game_details)
            recyclerView.adapter = adapter

        }

    }
}