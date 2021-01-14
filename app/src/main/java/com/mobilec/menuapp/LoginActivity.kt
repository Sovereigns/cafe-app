package com.mobilec.menuapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLoginAdmin : Button = findViewById(R.id.btn_login_admin)
        btnLoginAdmin.setOnClickListener(this)
        val btnLoginUser : TextView = findViewById(R.id.txt_kembali)
        btnLoginUser.setOnClickListener(this)

    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login_admin -> {
                val mLoginAdmin = Intent(this,MainActivity::class.java )
                startActivity(mLoginAdmin)
            }R.id.txt_kembali -> {
                finish()
            }
        }
    }
}