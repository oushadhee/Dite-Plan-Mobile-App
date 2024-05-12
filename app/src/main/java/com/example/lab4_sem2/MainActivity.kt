// MainActivity.kt

package com.example.lab4_sem2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4_sem2.db.UserEntity

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var saveButton: Button
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        saveButton = findViewById(R.id.saveButton)
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainActivityViewModel::class.java)
        viewModel.getAllUsersObservers().observe(this, Observer {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })

        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()
            val userId = nameInput.getTag(nameInput.id) as? Int ?: 0

            if (saveButton.text == "Save") {
                val user = UserEntity(0, name, email, phone)
                viewModel.insertUserInfo(user)
            } else {
                val user = UserEntity(userId, name, email, phone)
                viewModel.updateUserInfo(user)
                saveButton.text = "Save"
            }

            // Clear input fields after saving or updating
            nameInput.setText("")
            emailInput.setText("")
            phoneInput.setText("")
        }
    }

    override fun onDeleteUserClickListener(user: UserEntity) {
        viewModel.deleteUserInfo(user)
    }

    override fun onItemClickListener(user: UserEntity) {
        nameInput.setText(user.name)
        emailInput.setText(user.email)
        phoneInput.setText(user.phone)
        nameInput.setTag(nameInput.id, user.id)
        saveButton.text = "Update"
    }
}
