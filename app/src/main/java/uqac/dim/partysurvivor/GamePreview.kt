package uqac.dim.partysurvivor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

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
    }
}