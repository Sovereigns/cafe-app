package com.mobilec.menuapp

import android.Manifest
import android.app.Activity
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
import com.mobilec.menuapp.DBHelper.DBHelper
import com.mobilec.menuapp.Model.Food
import kotlinx.android.synthetic.main.activity_manage.*
import java.io.ByteArrayOutputStream

class AddManageActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_GAMBAR = "extra_gambar"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_HARGA = "extra_harga"
        const val EXTRA_DESKRIPSI = "extra_deskripsi"


        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    internal lateinit var db: DBHelper
    internal var lstFoods:List<Food> = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_manage)

        val btnKembali : Button = findViewById(R.id.btn_kembali)
        btnKembali.setOnClickListener(this)
        val btnAddImage : ImageButton = findViewById(R.id.btn_add_image)
        btnAddImage.setOnClickListener(this)

        val mAdd : Button = findViewById(R.id.btn_add)
        mAdd.setOnClickListener(this)



        db = DBHelper(this)
        // refreshData()
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_kembali -> {
                finish()
            }
            R.id.btn_add->{
                val food = Food (
                    Integer.parseInt(edt_id.text.toString()),
                    edt_nama.text.toString(),
                    Integer.parseInt(edt_harga.text.toString()),
                    edt_deskripsi.text.toString(),
                    imageConvert()
                )
                db.addFood(food)
                //     refreshData()
                clearText()
            }
            R.id.btn_add_image-> {
                permissionChecker()
            }

        }
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
        if (resultCode== Activity.RESULT_OK && requestCode== ManageActivity.IMAGE_PICK_CODE){
            img_image_view.setImageURI(data?.data)
        }
    }

    fun permissionChecker(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission (Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, ManageActivity.PERMISSION_CODE)
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
            startActivityForResult(intent, ManageActivity.IMAGE_PICK_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray){
        when(requestCode){
            ManageActivity.PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                    //permission from popup granted
                }
                else{
                    //permision from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun imageConvert(): ByteArray {
        val mImageConvert : ImageView = findViewById(R.id.img_image_view)
        val bitmap = (mImageConvert.getDrawable() as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        val resultImage = stream.toByteArray()
        return resultImage
    }
    fun getImage(image: ByteArray?): Bitmap {
        return BitmapFactory.decodeByteArray(image,0, image!!.size)
    }

}