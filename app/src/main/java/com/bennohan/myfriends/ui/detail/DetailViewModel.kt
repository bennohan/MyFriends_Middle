package com.bennohan.myfriends.ui.detail

import androidx.lifecycle.viewModelScope
import com.bennohan.myfriends.api.ApiService
import com.bennohan.myfriends.base.BaseViewModel
import com.bennohan.myfriends.database.User
import com.bennohan.myfriends.database.UserDao
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toList
import com.crocodic.core.extension.toObject
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val session: CoreSession,
) : BaseViewModel() {
    //Favourite

    fun favourite(userIdLike: Int) = viewModelScope.launch {
        val userId = userDao.getId().id
        ApiObserver({ apiService.postFavourite(userId,userIdLike) },
            false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.send(ApiResponse().responseSuccess("Favourite Added"))

                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.send(ApiResponse().responseError())

                }
            })
    }
}