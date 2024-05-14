package com.example.lab4_sem2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4_sem2.db.UserEntity

// RecyclerViewAdapter class extending RecyclerView.Adapter<MyViewHolder>
class RecyclerViewAdapter(val listener: RowClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    // List to hold the data of UserEntity objects
    var items = ArrayList<UserEntity>()

    // Function to set the list of UserEntity objects
    fun setListData(data: ArrayList<UserEntity>) {
        this.items = data
    }

    // Function to create ViewHolder objects
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for RecyclerView row
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)
        // Return a new instance of MyViewHolder
        return MyViewHolder(inflater, listener)
    }

    // Function to get the count of items in the RecyclerView
    override fun getItemCount(): Int {
        return items.size
    }

    // Function to bind data to the ViewHolder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Set click listener for the item view
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }
        // Bind data to the ViewHolder
        holder.bind(items[position])
    }

    // MyViewHolder class representing each item in the RecyclerView
    class MyViewHolder(view: View, val listener: RowClickListener) : RecyclerView.ViewHolder(view) {

        // Initialize views
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        val deleteUserID: ImageView = view.findViewById(R.id.deleteUserID) // Change this to ImageView

        // Function to bind data to views
        fun bind(data: UserEntity) {
            tvName.text = data.name
            tvEmail.text = data.email
            tvPhone.text = data.phone

            // Set click listener for delete button
            deleteUserID.setOnClickListener {
                listener.onDeleteUserClickListener(data)
            }
        }
    }

    // Interface for handling row click events
    interface RowClickListener {
        fun onDeleteUserClickListener(user: UserEntity)
        fun onItemClickListener(user: UserEntity)
    }
}
