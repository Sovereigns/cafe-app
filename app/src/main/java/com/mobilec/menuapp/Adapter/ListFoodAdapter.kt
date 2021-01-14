package com.mobilec.menuapp.Adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobilec.menuapp.Model.Food
import com.mobilec.menuapp.R
import de.hdodenhof.circleimageview.CircleImageView

class ListFoodAdapter (private val listFood : ArrayList<Food>) : RecyclerView.Adapter<ListFoodAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked (data : Food)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvId : TextView = itemView.findViewById(R.id.txt_id)
        var tvNama : TextView = itemView.findViewById(R.id.txt_nama)
        var tvHarga : TextView = itemView.findViewById(R.id.txt_harga)
        var tvDeskripsi : TextView = itemView.findViewById(R.id.txt_deskripsi)
        var imgGambar : CircleImageView = itemView.findViewById(R.id.img_food_list)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view : View = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_layout,viewGroup,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val food = listFood[position]

        holder.tvId.text = food.id.toString()
        holder.tvNama.text = food.nama
        holder.tvHarga.text = food.harga.toString()
        holder.tvDeskripsi.text = food.deskripsi

        fun getImage(image: ByteArray?):Bitmap{
            return BitmapFactory.decodeByteArray(image,0, image!!.size)
        }
        holder.imgGambar.setImageBitmap(getImage(food.gambar))

        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listFood[holder.adapterPosition])}
    }

}