package uqac.dim.partysurvivor.addCoktailToBdd

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import uqac.dim.partysurvivor.R
import java.io.IOException
import java.util.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import uqac.dim.partysurvivor.Coktail
import kotlin.collections.HashMap


class TestAddImage : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 123
    //lateinit var imageView: ImageView
    lateinit var buttonTakePicture: Button
    lateinit var addIngredient: Button
    lateinit var submit: Button
    lateinit var editName: EditText
    lateinit var editRecette: EditText
    lateinit var editDetails: EditText
    lateinit var mCurrentPicturePath: String
    lateinit var progressdialog: ProgressDialog
    var uploadImageTerminate: Boolean = false

    var btnSelect: Button? = null
    var btnUpload: Button? = null

    // view for image view
    private var imageView: ImageView? = null

    private var imageLink: String? = null

    // Uri indicates, where the image will be picked from
    private var filePath: Uri? = null

    private var cheminImageName: String? = null

    // request code
    private val PICK_IMAGE_REQUEST = 22

    // instance for firebase storage and StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_add_image)


        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(
            Color.parseColor("#0F9D58")
        )
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(colorDrawable)
        }

        // initialise views
        btnSelect = findViewById(R.id.btnChoose)
        btnUpload = findViewById(R.id.btnUpload)
        imageView = findViewById(R.id.imgView)

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        // on pressing btnSelect SelectImage() is called
        btnSelect?.setOnClickListener(View.OnClickListener { SelectImage() })

        // on pressing btnUpload uploadImage() is called
        btnUpload?.setOnClickListener(View.OnClickListener {  })


        //formulaire
        imageView = findViewById(R.id.imageView2)



        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            buttonTakePicture.setEnabled(false)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )
        }


        //Formulaire d'ajout de coktail
        submit = findViewById(R.id.addCoktail)
        addIngredient = findViewById(R.id.addIngredient)
        editName = findViewById(R.id.editName)
        editRecette = findViewById(R.id.editRecette)
        editDetails = findViewById(R.id.editDetails)
        var ingredientContainer: LinearLayout = findViewById(R.id.editTextContainer)

        var listEditIngredient: ArrayList<EditText> = ArrayList()
        var listIngredient: ArrayList<String> = ArrayList()

        addIngredient.setOnClickListener({
            var edit = EditText(this)
            ingredientContainer.addView(edit)
            listEditIngredient.add(edit)
        })

        submit.setOnClickListener {

            uploadImage()

            if(uploadImageTerminate){
                //récupération et validation des champs du formulaire
                var notGood: Boolean = false
                var name: String = editName.getText().toString()
                var recette: String = editRecette.getText().toString()
                var details: String = editDetails.getText().toString()

                for (edit in listEditIngredient) {
                    var ingredient: String = edit.getText().toString()
                    listIngredient.add(ingredient)
                }

                for (ingredient in listIngredient) {
                    System.out.println("Ingredient : " + ingredient)
                    if (name.equals("") || name == null || recette.equals("") || recette == null || ingredient.equals(
                            ""
                        ) || ingredient == null || details.equals("") || details == null
                    ) {
                        notGood = true
                    }
                }

                System.out.println("Name : " + name)
                System.out.println("Details : " + details)
                System.out.println("recette : " + recette)

                if (!notGood) {
                    writeNewCoktail(name, details, recette, listIngredient, "url")
                }
                else{
                    Toast
                        .makeText(
                            this,
                            "Attention il faut remplir tout les champs du formulaire avec au moins 1 ingrédient !",
                            Toast.LENGTH_LONG
                        )
                        .show()
                }

                reinitialisation(
                    editName,
                    editDetails,
                    editRecette,
                    listEditIngredient,
                    ingredientContainer
                )
            }



        }

    }

    fun writeNewCoktail(name: String, details: String, recette: String, listIngredient: ArrayList<String>, imageUrl: String){

        //récupération de l'objet coktail
        var ingredients: String = ""
        for(ingredient in listIngredient){
            ingredients = ingredients + ingredient + "\n"
        }
        System.out.println(ingredients)
        var newCoktail: Coktail = Coktail(name, recette, details, imageUrl, ingredients)

        //ajout du coktail à la bdd
        val database = FirebaseDatabase.getInstance()
        val ref1 = database.getReference("coktail")

        var key = ref1.push().key



        //recupération de l'url de l'image et ajout à la bdd

        val ref = storageReference
            ?.child(
                "imageCoktail/"
                        + cheminImageName
            )

        if (ref != null) {
            if(filePath!=null){
                ref.putFile(filePath!!)
                    .addOnSuccessListener {
                        val result = it.metadata!!.reference!!.downloadUrl;
                        result.addOnSuccessListener {
                            imageLink = it.toString()
                            newCoktail.imageUrl = imageLink

                            var coktailMap: HashMap<String, Coktail> = HashMap<String, Coktail>()
                            coktailMap.put(UUID.randomUUID().toString(), newCoktail)
                            ref1.updateChildren(coktailMap as Map<String, Any>)
                        }
                    }
            }
            else{
                Toast
                    .makeText(
                        this,
                        "Attention il faut uploader une image pour votre coktail",
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        }



    }

    fun reinitialisation(name: EditText, details: EditText, recette: EditText, listEditIngredient: ArrayList<EditText>, layout: LinearLayout){
        name.setText("")
        details.setText("")
        recette.setText("")

        for(edit in listEditIngredient){
            layout.removeView(edit)
        }
    }

    // Select Image method
    private fun SelectImage() {

        // Defining Implicit Intent to mobile gallery
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Image from here..."
            ),
            PICK_IMAGE_REQUEST
        )
    }

    // Override onActivityResult method
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data
            try {

                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                //imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }

        }
    }

    // UploadImage method
    private fun uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            cheminImageName = UUID.randomUUID().toString()

            // Defining the child of storageReference
            val ref = storageReference
                ?.child(
                    "imageCoktail/"
                            + cheminImageName
                )

            // adding listeners on upload
            // or failure of image
            if (ref != null) {
                ref.putFile(filePath!!)
                    .addOnSuccessListener {
                        //System.out.println("resultat DANS ONSUCCESS LA : "+imageUri)
                        //var snapshot = taskSnaphot.result
                        //var download: UploadTask.TaskSnapshot? = snapshot
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss()
                        Toast
                            .makeText(
                                this@TestAddImage,
                                "Image Uploaded!!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                    .addOnFailureListener { e -> // Error, Image not uploaded
                        progressDialog.dismiss()
                        Toast
                            .makeText(
                                this@TestAddImage,
                                "Failed " + e.message,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                    .addOnProgressListener { taskSnapshot ->

                        // Progress Listener for loading
                        // percentage on the dialog box
                        val progress = ((100.0
                                * taskSnapshot.bytesTransferred
                                / taskSnapshot.totalByteCount))
                        progressDialog.setMessage(
                            ("Uploaded "
                                    + progress.toInt() + "%")
                        )
                    }
                uploadImageTerminate = true
            }


        }
    }

    fun getImageUrl(){

        if(cheminImageName!=null){
            val ref = storageReference
                ?.child(
                    "imageCoktail/"
                            + cheminImageName
                )

            if (ref != null) {
                ref.putFile(filePath!!)
                    .addOnSuccessListener {
                        val result = it.metadata!!.reference!!.downloadUrl;
                        result.addOnSuccessListener {

                            imageLink = it.toString()
                            System.out.println("DANS LE ONSUCCESS : "+imageLink)
                        }
                    }
            }
        }

    }
}