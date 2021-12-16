package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class GamePreview  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_preview)

        val buttonRegle = findViewById<Button>(R.id.ButtonRègle)
        val buttonNothing = findViewById<Button>(R.id.ButtonNothing)

        val viewRegle = findViewById<TextView>(R.id.ViewRegle)
        val viewNothing = findViewById<TextView>(R.id.ViewNothing)

        //à retirer quand deplacement dans GamePreview
        viewRegle.setMovementMethod(ScrollingMovementMethod())

        buttonNothing.setOnClickListener {
            viewRegle.visibility = View.GONE;
            viewNothing.visibility = View.VISIBLE;
        }
        buttonRegle.setOnClickListener {
            viewNothing.visibility = View.GONE;
            viewRegle.visibility = View.VISIBLE;
        }

        val position: Int = intent.getIntExtra("position", -1)
        val dataGame:Game = intent.getSerializableExtra("dataGame") as Game

        if(position != -1){
            //var image: ImageView = findViewById(R.id.imageView)
            //Glide.with(this).load(dataGame.imageUrl).into(image)
            var title: TextView = findViewById(R.id.TitleGame)
            title.setText(dataGame.gameName)
            viewRegle.setText(dataGame.regle)
            viewNothing.setText(dataGame.gagner)
        }

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_2
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@GamePreview, ChoixFavoris::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@GamePreview, ChoixJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@GamePreview, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@GamePreview, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@GamePreview, TestAddImage::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }
}