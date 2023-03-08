package com.bennohan.myfriends.ui.home

import androidx.lifecycle.MutableLiveData
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
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val session: CoreSession,
    ) : BaseViewModel() {

    var friends = MutableLiveData<List<User>>()


    fun getfriendsList (userId: Int?) = viewModelScope.launch {
            ApiObserver({ apiService.getFriends(userId) },
                false, object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {
                        val data = response.getJSONArray(ApiCode.DATA)
                            val friendlist = data.toList<User>(gson)
                        friends.postValue(friendlist)
                        _apiResponse.send(ApiResponse().responseSuccess("Sukses"))

                    }
                    override suspend fun onError(response: ApiResponse) {
                        super.onError(response)
                        _apiResponse.send(ApiResponse().responseError())
                    }

                }
            )
        }

}