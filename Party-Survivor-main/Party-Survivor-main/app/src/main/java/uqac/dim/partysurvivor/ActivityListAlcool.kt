package uqac.dim.partysurvivor

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class ActivityListAlcool : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        renameActionBar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        //val jameson = findViewById<ImageView>(android.R.id.jameson.png)
        //jameson.setImageResource(android.R.drawable.jameson.png)

        //val image1: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.jameson, null)
        //var alcool1 = Alcool("Whisky", "Jameson", image1)


        val image_details: List<Alcool> = getListData(getIdButton())
        val gridView = findViewById<View>(R.id.gridView) as GridView
        gridView.adapter = CustomGridAdapterMenuAlcool(this, image_details)

        //quand l'user click sur un gridItem
        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { a, v, position, id ->
                val o = gridView.getItemAtPosition(position)
                val alcool: Alcool = o as Alcool
                /*Toast.makeText(
                    this@MainActivity, ""
                            , Toast.LENGTH_LONG
                ).show()*/
            }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun renameActionBar(){
        intent = getIntent()
        when(getIdButton()){
            "scotchAndWhisky" -> supportActionBar?.setTitle("Scotch and Whisky")
            "liqueurAndCream" -> supportActionBar?.setTitle("Liqueur and Cream")
            "gin" -> supportActionBar?.setTitle("Gin")
            "rum" -> supportActionBar?.setTitle("Rum")
            "vodka" -> supportActionBar?.setTitle("Vodka")
            "cognacAndBrandy" -> supportActionBar?.setTitle("Cognac and Brandy")
            "aniseFlavouredSpirit" -> supportActionBar?.setTitle("Anise Flavoured Spirit")
            "absinth" -> supportActionBar?.setTitle("Absinth")
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
        var ballantine = Alcool("scotchAndWhisky", "Ballantine", "ballantine")
        var crownRoyal = Alcool("scotchAndWhisky", "Crown Royal", "crown_royale")
        var grantsFamily = Alcool("scotchAndWhisky", "Grant's Family", "grants_family")
        var jameson = Alcool("scotchAndWhisky", "Jameson", "jameson")
        var johnnieWalker = Alcool("scotchAndWhisky", "Johnnnie Walker", "johnnie_walker")
        var bacardi = Alcool("rum", "Bacardi", "bacardi")
        var captainMorgan = Alcool("rum", "Captain Morgan", "captain_morgan")
        var sourPuss = Alcool("liqueurAndCream", "Sour Puss", "sour_puss")
        var sortilege = Alcool("liqueurAndCream", "Sortilège", "sortilege")
        var beefeater = Alcool("gin", "Beefeater", "beefeater")
        var bombay = Alcool("gin", "Bombay", "bombay")
        var meaghers = Alcool("liqueurAndCream", "Meaghers", "meaghers")



        list.add(ballantine)
        list.add(crownRoyal)
        list.add(grantsFamily)
        list.add(jameson)
        list.add(johnnieWalker)
        list.add(bacardi)
        list.add(captainMorgan)
        list.add(sourPuss)
        list.add(sortilege)
        list.add(beefeater)
        list.add(bombay)
        list.add(meaghers)



        for(alcool in list){
            if(alcool.type == idButton){
                finalList.add(alcool)
            }
        }

        return finalList

    }
}