package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ChoixTypeJeu : AppCompatActivity() {
    lateinit var dice: View
    lateinit var card: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_type_jeu)
        dice = findViewById(R.id.DiceBtn)
        card = findViewById(R.id.CardBtn)
    }

    fun onClick(view: View) {
        //val intent = Intent(this, ChoixJeu::class.java)
        val intent = Intent(this, ChoixJeu::class.java)
        when(view){
            dice -> intent.putExtra("Type","diceGame")
            card -> intent.putExtra("Type","cardGame")
        }
        startActivity(intent)
    }
}