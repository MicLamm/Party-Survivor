package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.children

class ChoixJeu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_jeu)
        var view = findViewById<LinearLayout>(R.id.MainLayout)
        val lInflater:LayoutInflater = LayoutInflater.from(applicationContext)
        for(i in 1..5){
            val row: View = lInflater.inflate(R.layout.row,view,false)
            val rowbtn: Button = row.findViewById(R.id.GameBtn)
            val rowIndex: TextView = row.findViewById(R.id.Index)
            rowIndex.text = "1"
            rowbtn.setOnClickListener {
                //val intent = Intent(v.context, ItemProfiler::class.java)
                //val tv1 = row.findViewById<View>(R.id.item_hash) as TextView
                //intent.putExtra("item", tv1.text.toString())
                //startActivity(intent)
            }
            rowbtn.text = "Pige dans le lac"

            view.addView(row,0)
        }
        view = findViewById<LinearLayout>(R.id.OptionalLayout)
        for(i in 1..5){
            val row: View = lInflater.inflate(R.layout.row,view,false)
            val text: Button = row.findViewById(R.id.GameBtn)
            text.text = "Pige dans le lac"

            view.addView(row,0)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)

        val search: MenuItem? = menu?.findItem(R.id.nav_search)
        val searchView: SearchView = search?.actionView as SearchView
        searchView.queryHint = "Search something"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var view:LinearLayout = findViewById(R.id.MainLayout)
                for (childView in view.children){
                    val btn: Button = childView.findViewById(R.id.GameBtn)
                    if (!btn.text.startsWith(newText.toString())){
                        btn.visibility =View.GONE
                    }
                    else{
                        btn.visibility =View.VISIBLE
                    }

                }
                view = findViewById(R.id.OptionalLayout)
                for (childView in view.children){
                    val btn: Button = childView.findViewById(R.id.GameBtn)
                    if (!btn.text.startsWith(newText.toString())){
                        btn.visibility =View.GONE
                    }
                    else{
                        btn.visibility =View.VISIBLE
                    }

                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}