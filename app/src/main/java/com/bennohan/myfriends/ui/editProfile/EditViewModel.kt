package com.bennohan.myfriends.ui.editProfile

import androidx.lifecycle.viewModelScope
import com.bennohan.myfriends.api.ApiService
import com.bennohan.myfriends.base.BaseViewModel
import com.bennohan.myfriends.database.User
import com.bennohan.myfriends.database.UserDao
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toObject
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val session: CoreSession,
) : BaseViewModel() {

    //function Update
    fun updateProfile(name: String, school: String, description : String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        val idUser =userDao.getId().id
        ApiObserver({ apiService.updateProfile(idUser ,name , school , description ) },
            false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    _apiResponse.send(ApiResponse().responseSuccess("Sukses"))
                    userDao.insert(data.copy(idRoom = 1))

                    _apiResponse.send(ApiResponse().responseSuccess("Profile Updated"))
                }
                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.send(ApiResponse().responseError())
                }

            }
        )

    }

    //function Update
    fun updateProfilePhoto(name: String, school: String, description : String , photo : File) = viewModelScope.launch {
        val fileBody = photo.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("photo", photo.name, fileBody)
        _apiResponse.send(ApiResponse().responseLoading())
        val idUser =userDao.getId().id
        ApiObserver({ apiService.updateProfilePhoto(idUser ,name , school , description , filePart ) },
            false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    _apiResponse.send(ApiResponse().responseSuccess("Sukses"))
//                    userDao.insert(data.copy(idRoom = 1))

                    _apiResponse.send(ApiResponse().responseSuccess("Profile Updated"))
                    userDao.insert(data.copy(idRoom = 1))

                }
                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.send(ApiResponse().responseError())
                }

            }
        )

    }

    fun logout () = viewModelScope.launch {
        userDao.deleteAll()

    }

}