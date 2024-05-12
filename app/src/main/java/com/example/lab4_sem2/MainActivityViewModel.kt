// MainActivityViewModel.kt

package com.example.lab4_sem2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lab4_sem2.db.RoomAppDb
import com.example.lab4_sem2.db.UserDao
import com.example.lab4_sem2.db.UserEntity

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao = RoomAppDb.getAppDatabase(application).userDao()
    // Your ViewModel methods


    private val allUsers = MutableLiveData<List<UserEntity>>()

    init {
        getAllUsers()
    }

    fun getAllUsersObservers(): MutableLiveData<List<UserEntity>> {
        return allUsers
    }

    fun getAllUsers() {
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        val list = userDao?.getAllUserInfo()
        allUsers.postValue(list)
    }

    fun insertUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.insertUser(entity)
        getAllUsers()
    }

    fun updateUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.updateUser(entity)
        getAllUsers()
    }

    fun deleteUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.deleteUser(entity)
        getAllUsers()
    }
}
