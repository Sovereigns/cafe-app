package com.mobilec.menuapp.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mobilec.menuapp.Model.Food

class DBHelper (context : Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER) {
    companion object{
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "foodmenudb.db"

        //table
        private val TABLE_NAME ="menu"
        private val COL_ID = "id"
        private val COL_NAME = "namamakanan"
        private val COL_HARGA = "harga"
        private val COL_DESKRIPSI = "deskripsi"
        private val COL_GAMBAR = "gambar"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY: String = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY, " +
                "$COL_NAME TEXT, $COL_HARGA TEXT, $COL_DESKRIPSI TEXT, $COL_GAMBAR BLOB)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    //CRUD
    val allFood:ArrayList<Food>
        get() {
            val lstFoods = ArrayList<Food>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery,null)
            if (cursor.moveToFirst()){
                do {
                    val food = Food()
                    food.id = cursor.getInt( cursor.getColumnIndex(COL_ID))
                    food.nama = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    food.harga = cursor.getInt(cursor.getColumnIndex(COL_HARGA))
                    food.deskripsi = cursor.getString(cursor.getColumnIndex(COL_DESKRIPSI))
                    food.gambar = cursor.getBlob(cursor.getColumnIndex(COL_GAMBAR))

                    lstFoods.add(food)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstFoods
        }
    val imageFood:ArrayList<Food>
        get() {
            val lstFoods = ArrayList<Food>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery,null)
            if (cursor.moveToFirst()){
                do {
                    val food = Food()
                    food.gambar = cursor.getBlob(cursor.getColumnIndex(COL_GAMBAR))

                    lstFoods.add(food)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstFoods
        }

    fun addFood(food: Food){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,food.id)
        values.put(COL_NAME,food.nama)
        values.put(COL_HARGA,food.harga)
        values.put(COL_DESKRIPSI,food.deskripsi)
        values.put(COL_GAMBAR,food.gambar)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun updateFood(food: Food):Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,food.id)
        values.put(COL_NAME,food.nama)
        values.put(COL_HARGA,food.harga)
        values.put(COL_DESKRIPSI,food.deskripsi)
        values.put(COL_GAMBAR,food.gambar)

        return db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(food.id.toString()))
    }
    fun deleteFood(food: Food) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(food.id.toString()))
        db.close()
    }
}