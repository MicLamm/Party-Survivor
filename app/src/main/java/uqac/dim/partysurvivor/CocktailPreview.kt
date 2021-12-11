package uqac.dim.partysurvivor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


import java.util.*

class CocktailPreview() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_preview)

        var buttonIngredient = findViewById<Button>(R.id.ButtonIngredient)
        var buttonRecette = findViewById<Button>(R.id.ButtonRecette)

        var viewIngredient = findViewById<TextView>(R.id.ViewIngredient)
        var viewRecette = findViewById<TextView>(R.id.ViewRecette)

        //à retirer quand deplacement dans GamePreview
        viewIngredient.setMovementMethod(ScrollingMovementMethod())

        buttonIngredient.setOnClickListener {
            viewRecette.visibility = View.GONE;
            viewIngredient.visibility = View.VISIBLE;
        }
        buttonRecette.setOnClickListener {
            viewIngredient.visibility = View.GONE;
            viewRecette.visibility = View.VISIBLE;
        }


        val position: Int = intent.getIntExtra("position", -1)
        val type: String = intent.getStringExtra("type") as String

        if(type.equals("coktail")){
            val data:Coktail = intent.getSerializableExtra("data") as Coktail

            if(position != -1){
                var image: ImageView = findViewById(R.id.imageView)
                Glide.with(this).load(data.imageUrl).into(image)

                var title: TextView = findViewById(R.id.TitleCocktail)
                title.setText(data.coktailName)

                var ingredient: TextView = findViewById(R.id.ViewIngredient)
                ingredient.setText(data.ingredient)
                var recette: TextView = findViewById(R.id.ViewRecette)
                recette.setText(data.recette)
            }
        }
        else{
            val dataGame:Game = intent.getSerializableExtra("dataGame") as Game

            if(position != -1){
                var image: ImageView = findViewById(R.id.imageView)
                Glide.with(this).load(dataGame.imageUrl).into(image)

                var title: TextView = findViewById(R.id.TitleCocktail)
                title.setText(dataGame.gameName)

                var buttonIngredient: Button = findViewById(R.id.ButtonIngredient)
                buttonIngredient.setText("regles")
                var buttonRecette: Button = findViewById(R.id.ButtonRecette)
                buttonRecette.setText("gagner ?")

                var ingredient: TextView = findViewById(R.id.ViewIngredient)
                ingredient.setText(dataGame.regle)
                var recette: TextView = findViewById(R.id.ViewRecette)
                recette.setText(dataGame.gagner)
            }
        }

    }
}