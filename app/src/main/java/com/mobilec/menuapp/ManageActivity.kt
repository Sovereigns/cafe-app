package com.mobilec.menuapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mobilec.menuapp.DBHelper.DBHelper
import com.mobilec.menuapp.Model.Food
import kotlinx.android.synthetic.main.activity_manage.*
import java.io.ByteArrayOutputStream

class ManageActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_GAMBAR = "extra_gambar"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_HARGA = "extra_harga"
        const val EXTRA_DESKRIPSI = "extra_deskripsi"

        val IMAGE_PICK_CODE = 1000
        val PERMISSION_CODE = 1001
        }

    internal lateinit var db: DBHelper
    internal var lstFoods:List<Food> = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        val btnKembali : Button = findViewById(R.id.btn_kembali)
        btnKembali.setOnClickListener(this)
        val btnAddImage : ImageButton = findViewById(R.id.btn_add_image)
        btnAddImage.setOnClickListener(this)

        val mUpdate : Button = findViewById(R.id.btn_update)
        mUpdate.setOnClickListener(this)
        val mDelete : Button = findViewById(R.id.btn_delete)
        mDelete.setOnClickListener(this)

        showEditMenu()

        db = DBHelper(this)
       // refreshData()

    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_kembali -> {
              finish()
            }

            R.id.btn_update->{
                val food = Food (
                    Integer.parseInt(edt_id.text.toString()),
                    edt_nama.text.toString(),
                    Integer.parseInt(edt_harga.text.toString()),
                    edt_deskripsi.text.toString(),
                    imageConvert()
                )
                db.updateFood(food)
             //   refreshData()
                clearText()
            }
            R.id.btn_delete->{
                val food = Food (
                    Integer.parseInt(edt_id.text.toString()),
                    edt_nama.text.toString(),
                    Integer.parseInt(edt_harga.text.toString()),
                    edt_deskripsi.text.toString(),
                    imageConvert()
                )
                db.deleteFood(food)
             //   refreshData()
                clearText()
            }
            R.id.btn_add_image-> {
               permissionChecker()
            }

        }
    }

    private fun showEditMenu() {
        edt_id.setText(intent.getIntExtra(EXTRA_ID,0).toString())

        edt_nama.setText(intent.getStringExtra(EXTRA_NAMA))
        edt_harga.setText(intent.getIntExtra(EXTRA_HARGA,0).toString())
        edt_deskripsi.setText(intent.getStringExtra(EXTRA_DESKRIPSI))
        getImage()



       // img_image_view.setImageDrawable(getImage(imageConvert()))
    }

    private fun clearText(){
        edt_id.text.clear()
        edt_nama.text.clear()
        edt_harga.text.clear()
        edt_deskripsi.text.clear()


        Toast.makeText(this,"Sukses", Toast.LENGTH_SHORT).show()
    }

    private fun refreshData() {
        lstFoods = db.allFood
        //     val adapter = ListFoodAdapter(this, lstFoods)
        //    list_foods.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode==IMAGE_PICK_CODE){
            img_image_view.setImageURI(data?.data)
        }
    }

    fun permissionChecker(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission (Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else{
                pickImageFromGallery()
            }
        }
        else{
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray){
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                    //permission from popup granted
                }
                else{
                    //permision from popup denied
                    Toast.makeText(this, "Permission denied",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun imageConvert(): ByteArray {
        val mImageConvert : ImageView = findViewById(R.id.img_image_view)
        val bitmap = (mImageConvert.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
    fun getImage() {
        val getByteArray : ByteArray? = intent.getByteArrayExtra(EXTRA_GAMBAR)
        val bmp = BitmapFactory.decodeByteArray(getByteArray,0, getByteArray!!.size)
        val image: ImageView = findViewById(R.id.img_image_view)

       // Glide.with(this).load(bmp).into(R.id.img_image_view)
        image.setImageBitmap(Bitmap.createScaledBitmap(bmp,270,250,false))
    }

}


