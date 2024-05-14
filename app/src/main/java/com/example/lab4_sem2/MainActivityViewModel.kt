// MainActivityViewModel.kt

package com.example.lab4_sem2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lab4_sem2.db.RoomAppDb
import com.example.lab4_sem2.db.UserDao
import com.example.lab4_sem2.db.UserEntity

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    // Declaring variables for accessing UserDao and LiveData
    private val userDao: UserDao = RoomAppDb.getAppDatabase(application).userDao()
    // Your ViewModel methods


    private val allUsers = MutableLiveData<List<UserEntity>>()

    // Initialize by retrieving all users from the database
    init {
        getAllUsers()
    }

    // Expose LiveData to observe user data changes
    fun getAllUsersObservers(): MutableLiveData<List<UserEntity>> {
        return allUsers
    }


    // Function to fetch all users from the database and update LiveData
    fun getAllUsers() {
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        val list = userDao?.getAllUserInfo()
        allUsers.postValue(list)
    }

    // Function to insert user data into the database
    fun insertUserInfo(entity: UserEntity) {
        userDao.insertUser(entity) // Insert user into the database using UserDao
        getAllUsers() // Refresh the user list after insertion
    }

    // Function to update user data in the database
    fun updateUserInfo(entity: UserEntity) {
        userDao.updateUser(entity) // Update user in the database using UserDao
        getAllUsers() // Refresh the user list after update
    }

    // Function to delete user data from the database
    fun deleteUserInfo(entity: UserEntity) {
        userDao.deleteUser(entity) // Delete user from the database using UserDao
        getAllUsers() // Refresh the user list after deletion
    }
}