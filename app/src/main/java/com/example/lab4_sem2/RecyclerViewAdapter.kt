package com.example.lab4_sem2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4_sem2.db.UserEntity

class RecyclerViewAdapter(val listener: RowClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items = ArrayList<UserEntity>()

    fun setListData(data: ArrayList<UserEntity>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)
        return MyViewHolder(inflater, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }
        holder.bind(items[position])
    }

    class MyViewHolder(view: View, val listener: RowClickListener) : RecyclerView.ViewHolder(view) {

        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        val deleteUserID: ImageView = view.findViewById(R.id.deleteUserID) // Change this to ImageView

        fun bind(data: UserEntity) {
            tvName.text = data.name
            tvEmail.text = data.email
            tvPhone.text = data.phone

            deleteUserID.setOnClickListener {
                listener.onDeleteUserClickListener(data)
            }
        }
    }


    interface RowClickListener {
        fun onDeleteUserClickListener(user: UserEntity)
        fun onItemClickListener(user: UserEntity)
    }
}