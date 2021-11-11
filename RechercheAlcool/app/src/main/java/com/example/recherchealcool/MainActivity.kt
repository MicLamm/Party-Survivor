package com.example.recherchealcool

import com.example.recherchealcool.R

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener


import android.widget.GridView
import android.widget.Toast







class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val jameson = findViewById<ImageView>(android.R.id.jameson.png)
        //jameson.setImageResource(android.R.drawable.jameson.png)

        //val image1: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.jameson, null)
        //var alcool1 = Alcool("Whisky", "Jameson", image1)


        val image_details: List<Alcool> = getListData(getIdButton())
        val gridView = findViewById<View>(R.id.gridView) as GridView
        gridView.adapter = CustomGridAdapteur2(this, image_details)

        //quand l'user click sur un gridItem
        gridView.onItemClickListener =
            OnItemClickListener { a, v, position, id ->
                val o = gridView.getItemAtPosition(position)
                val alcool: Alcool = o as Alcool
                /*Toast.makeText(
                    this@MainActivity, ""
                            , Toast.LENGTH_LONG
                ).show()*/
            }

    }

    fun getIdButton():String{
        intent = getIntent()
        var idButton = ""
        if(intent != null){
            if(intent.hasExtra("idButton")){
                idButton = intent.getStringExtra("idButton").toString()
            }
        }
        return idButton
    }



    fun getListData(idButton:String): List<Alcool>{
        var list = ArrayList<Alcool>()
        var finalList = ArrayList<Alcool>()
        var ballantine = Alcool("Whiskey", "Ballantine", "ballantine")
        var crownRoyal = Alcool("Whiskey", "Crown Royal", "crown_royale")
        var grantsFamily = Alcool("Whiskey", "Grant's Family", "grants_family")
        var jameson = Alcool("Whiskey", "Jameson", "jameson")
        var johnnieWalker = Alcool("Whiskey", "Johnnnie Walker", "johnnie_walker")

        list.add(ballantine)
        list.add(crownRoyal)
        list.add(grantsFamily)
        list.add(jameson)
        list.add(johnnieWalker)

        for(alcool in list){
            if(alcool.type == idButton){
                finalList.add(alcool)
            }
        }

        return finalList

    }
}