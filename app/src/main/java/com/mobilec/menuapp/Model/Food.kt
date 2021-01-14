package com.mobilec.menuapp.Model

class Food {
    var id : Int=0
    var nama : String? = ""
    var harga : Int = 0
    var deskripsi : String? =""
    var gambar : ByteArray? = null

    constructor(){}

    constructor(id: Int, nama: String, harga: Int, deskripsi: String, gambar: ByteArray){
        this.id = id
        this.nama = nama
        this.harga = harga
        this.deskripsi = deskripsi
        this.gambar = gambar
    }
}