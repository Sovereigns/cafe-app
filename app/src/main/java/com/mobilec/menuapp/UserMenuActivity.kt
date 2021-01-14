package com.mobilec.menuapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobilec.menuapp.Adapter.GridFoodAdapter
import com.mobilec.menuapp.Adapter.ListFoodAdapter
import com.mobilec.menuapp.DBHelper.DBHelper
import com.mobilec.menuapp.Model.Food
import kotlinx.android.synthetic.main.activity_user_menu.*

class UserMenuActivity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var db: DBHelper
    internal var lstFoods:ArrayList<Food> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)

        db = DBHelper(this)
        list_grid_foods.setHasFixedSize(true)

        refreshData()

        val btnKembali2 : Button = findViewById(R.id.btn_kembali2)
        btnKembali2.setOnClickListener(this)
    }

    private fun refreshData() {
        lstFoods = db.allFood
        list_grid_foods.layoutManager = GridLayoutManager(this, 2)
        val gridFoodAdapter = GridFoodAdapter(lstFoods)
        list_grid_foods.adapter = gridFoodAdapter

        gridFoodAdapter.setOnItemClickCallback(object : GridFoodAdapter.OnItemClickCallback{

            override fun onItemClicked(data: Food) {
                showSelectedProduct(data)
                //val mEdtId : EditText = findViewById(R.id.edt_id)
            }
        })

    }
    private fun showSelectedProduct(data: Food) {
        Toast.makeText(this, " " + data.nama, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_kembali2 -> {
                finish()
            }
        }
    }
}