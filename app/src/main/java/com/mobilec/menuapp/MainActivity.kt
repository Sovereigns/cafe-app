package com.mobilec.menuapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobilec.menuapp.Adapter.ListFoodAdapter
import com.mobilec.menuapp.DBHelper.DBHelper
import com.mobilec.menuapp.ManageActivity.Companion.EXTRA_GAMBAR
import com.mobilec.menuapp.Model.Food
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_manage.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    internal lateinit var db: DBHelper
    internal var lstFoods:ArrayList<Food> = arrayListOf()
    internal var mShowImageFood:ArrayList<Food> = arrayListOf()
    private lateinit var rvFoods: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambahMenu : Button = findViewById(R.id.btn_add_menu)
        btnTambahMenu.setOnClickListener(this)
        val btnLogOut : Button = findViewById(R.id.btn_logout)
        btnLogOut.setOnClickListener(this)

        rvFoods = findViewById(R.id.list_foods)
        rvFoods.setHasFixedSize(true)

        db = DBHelper(this)
        refreshData()

    }

    override fun onResume() {
        super.onResume()
        refreshData()

    }

    private fun refreshData() {
        lstFoods = db.allFood
        rvFoods.layoutManager = LinearLayoutManager(this)
        val listFoodAdapter = ListFoodAdapter(lstFoods)
        rvFoods.adapter = listFoodAdapter
        listFoodAdapter.setOnItemClickCallback(object : ListFoodAdapter.OnItemClickCallback{

            override fun onItemClicked(data: Food) {
                showSelectedProduct(data)
                //val mEdtId : EditText = findViewById(R.id.edt_id)
                val manageDetailIntent = Intent(this@MainActivity, ManageActivity::class.java)
                    .apply {
                        putExtra(ManageActivity.EXTRA_ID, data.id)
                        putExtra(ManageActivity.EXTRA_NAMA, data.nama)
                        putExtra(ManageActivity.EXTRA_HARGA, data.harga)
                        putExtra(ManageActivity.EXTRA_DESKRIPSI, data.deskripsi)
                        putExtra(ManageActivity.EXTRA_GAMBAR,data.gambar)


                    }
                startActivity(manageDetailIntent)
            }
        })

    }

    private fun showSelectedProduct(data: Food) {
        Toast.makeText(this, " " + data.nama, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_add_menu -> {
               val manageAdd = Intent(this@MainActivity,AddManageActivity::class.java )
                startActivity(manageAdd)
            } R.id.btn_logout-> {
                finish()
            }
        }
    }

}