package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.children
import com.google.android.material.bottomnavigation.BottomNavigationView

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
                val intent = Intent(this, CocktailPreview::class.java)
                var view:LinearLayout = findViewById(R.id.MainLayout)
                for (childView in view.children){
                    val btn: Button = childView.findViewById(R.id.GameBtn)
                    intent.putExtra("Jeu",1)

                }
                startActivity(intent)
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

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_2
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@ChoixJeu, ChoixCategorie::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@ChoixJeu, ChoixTypeJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@ChoixJeu, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@ChoixJeu, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@ChoixJeu, ChoixCategorie::class.java)
                    startActivity(b)
                }
            }
            false
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