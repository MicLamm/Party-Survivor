package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CocktailPreview : AppCompatActivity() {
    private var titles = arrayOf("Cocktail One", "Cocktail Two", "Cocktail Three", "Cocktail Four", "Cocktail Five","Cocktail Six" )

    private var details = arrayOf("Detail of cocktail 1", "Detail of cocktail 2", "Detail of cocktail 3", "Detail of cocktail 4", "Detail of cocktail 5", "Detail of cocktail 6")

    private var images = intArrayOf(R.drawable.blue_lagoon, R.drawable.gin_tonic, R.drawable.margarita, R.drawable.rhum_cola, R.drawable.sex_on_the_beach, R.drawable.mojito)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_preview)


        val position: Int = intent.getIntExtra("position", -1)
        val jeu: Int = intent.getIntExtra("Jeu", -1)

        if(position != -1){
            var image: ImageView = findViewById(R.id.imageView)
            image.setImageResource(images[position])
            var title: TextView = findViewById(R.id.TitleCocktail)
            title.setText(titles[position])
        }
        if(jeu != -1){
            var image: ImageView = findViewById(R.id.imageView)
            image.visibility = View.GONE
            var title: TextView = findViewById(R.id.TitleCocktail)
            title.setText("Jeu")
        }

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_3
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@CocktailPreview, ChoixCategorie::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@CocktailPreview, ChoixTypeJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@CocktailPreview, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@CocktailPreview, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@CocktailPreview, ChoixCategorie::class.java)
                    startActivity(b)
                }
            }
            false
        }

    }





}