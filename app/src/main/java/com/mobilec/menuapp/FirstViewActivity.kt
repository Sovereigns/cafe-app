package com.mobilec.menuapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_first_view.*

class FirstViewActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_view)

        val btnTxtlogin : TextView = findViewById(R.id.txt_login)
        btnTxtlogin.setOnClickListener(this)
        val btnMasuk : Button = findViewById(R.id.btn_masuk)
        btnMasuk.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_login -> {
                val toLoginActivity = Intent(this,LoginActivity::class.java )
                startActivity(toLoginActivity)
            }
            R.id.btn_masuk -> {
                val toLoginActivity = Intent(this,UserMenuActivity::class.java )
                startActivity(toLoginActivity)
            }
        }
    }
}