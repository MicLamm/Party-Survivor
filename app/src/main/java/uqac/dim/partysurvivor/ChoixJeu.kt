package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ChoixJeu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_jeu)
        var view = findViewById<LinearLayout>(R.id.MainLayout)
        val lInflater:LayoutInflater = LayoutInflater.from(applicationContext)
        for(i in 1..5){
            val row: View = lInflater.inflate(R.layout.row,view,false)
            val rowbtn: Button = row.findViewById(R.id.GameBtn)
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
}