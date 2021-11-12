package uqac.dim.partysurvivor

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var Game: Button
    lateinit var Drink: Button
    lateinit var Featured: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_temp)
        //val intent = Intent(this, ChoixTypeJeu::class.java)
        //startActivity(intent)
        Game = findViewById(R.id.Games)
        Featured = findViewById(R.id.Cocktails)
        Drink = findViewById(R.id.HelpShake)
    }

    fun onClick(view: View){
        var intent:Intent

        intent = Intent(this, MainActivity::class.java)
        when(view){
            Featured -> intent = Intent(this, FeaturedDrink::class.java)
            Game -> intent = Intent(this, ChoixTypeJeu::class.java)
            Drink -> intent = Intent(this, MainActivityAlcoolMenu::class.java)
        }
        startActivity(intent)
    }

}