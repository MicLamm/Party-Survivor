package uqac.dim.partysurvivor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView

class CocktailPreview : AppCompatActivity() {
    private var titles = arrayOf("Cocktail One", "Cocktail Two", "Cocktail Three", "Cocktail Four", "Cocktail Five","Cocktail Six" )

    private var details = arrayOf("Detail of cocktail 1", "Detail of cocktail 2", "Detail of cocktail 3", "Detail of cocktail 4", "Detail of cocktail 5", "Detail of cocktail 6")

    private var images = intArrayOf(R.drawable.blue_lagoon, R.drawable.gin_tonic, R.drawable.margarita, R.drawable.rhum_cola, R.drawable.sex_on_the_beach, R.drawable.mojito)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_preview)


        val position: Int = intent.getIntExtra("position", -1)

        if(position != -1){
            var image: ImageView = findViewById(R.id.imageView)
            image.setImageResource(images[position])
            var title: TextView = findViewById(R.id.TitleCocktail)
            title.setText(titles[position])
        }

        val Ingredient: Button = findViewById(R.id.ButtonIngredient)
        val Ingredientview: TableLayout = findViewById(R.id.TableIngredient)
        val Recette: Button = findViewById(R.id.ButtonRecette)
        val RecetteView: TextView = findViewById(R.id.ViewRecette)
        Ingredient.setOnClickListener {
            RecetteView.visibility = View.GONE;
            Ingredientview.visibility = View.VISIBLE;
        }
        Recette.setOnClickListener {
            Ingredientview.visibility = View.GONE;
            RecetteView.visibility = View.VISIBLE;
        }
    }





}