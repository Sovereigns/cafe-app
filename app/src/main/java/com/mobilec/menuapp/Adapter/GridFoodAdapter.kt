package com.mobilec.menuapp.Adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobilec.menuapp.Model.Food
import com.mobilec.menuapp.R
import de.hdodenhof.circleimageview.CircleImageView

class GridFoodAdapter(private val listFood : ArrayList<Food>) : RecyclerView.Adapter<GridFoodAdapter.GridViewHolder>() {
    private lateinit var onItemClickCallback: GridFoodAdapter.OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked (data : Food)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
     //   var tvId : TextView = itemView.findViewById(R.id.txt_id)
        var tvNama : TextView = itemView.findViewById(R.id.txt_nama_user_food)
        var tvHarga : TextView = itemView.findViewById(R.id.txt_harga_user_food)
        var tvDeskripsi : TextView = itemView.findViewById(R.id.txt_deskripsi_user_food)
        var imgGambar : ImageView = itemView.findViewById(R.id.img_user_food)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): GridFoodAdapter.GridViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.grid_layout, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    override fun onBindViewHolder(holder: GridFoodAdapter.GridViewHolder, position: Int) {
        val food = listFood[position]

       // holder.tvId.text = food.id.toString()
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