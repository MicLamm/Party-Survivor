package uqac.dim.partysurvivor

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ChoixCategorie : AppCompatActivity() {
    lateinit var Game:Button
    lateinit var Drink:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_categorie)
        Drink = findViewById(R.id.DrinkBtn)
        Game = findViewById(R.id.GameBtn)
    }

    fun onClick(view: View){
        val intent = Intent()
        when(view){
            Drink -> intent.putExtra("Type",2)
            Game -> intent.putExtra("Type",1)
        }
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

}