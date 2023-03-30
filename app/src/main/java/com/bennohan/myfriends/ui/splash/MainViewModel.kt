package com.bennohan.myfriends.ui.splash

import androidx.lifecycle.viewModelScope
import com.bennohan.myfriends.base.BaseViewModel
import com.bennohan.myfriends.database.AppDatabase
import com.bennohan.myfriends.database.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val user: AppDatabase,
    private val userDao: UserDao,
) : BaseViewModel() {

    fun getUser(isLogin : (Boolean) -> Unit){
        viewModelScope.launch {
            isLogin(userDao.isLogin())
        }
    }

}