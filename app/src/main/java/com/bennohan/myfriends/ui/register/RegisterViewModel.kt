package com.bennohan.myfriends.ui.register

import androidx.lifecycle.viewModelScope
import com.bennohan.myfriends.api.ApiService
import com.bennohan.myfriends.base.BaseViewModel
import com.bennohan.myfriends.database.UserDao
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val session: CoreSession,
) : BaseViewModel() {

    //Register Function
    fun register (name : String , phone : String , password : String ) = viewModelScope.launch{
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({ apiService.register(name,phone, password) },
            false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.send(ApiResponse().responseSuccess("Register Berhasil"))
                }
                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.send(ApiResponse().responseError())
                }

            }
        )
    }

}