package com.bennohan.myfriends.api

import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    //Login
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): String

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): String

    @GET("get-list-friends")
    suspend fun getFriends(
        @Query("users_id")userid: Int?
    ): String

    @FormUrlEncoded
    @POST("update-profile")
    suspend fun updateProfile(
        @Field("id_user") id_user : Int?,
        @Field("name") name: String,
        @Field("school") school: String,
        @Field("description") description: String,
    ): String

    @Multipart
    @POST("update-profile")
    suspend fun updateProfilePhoto(
        @Query("id_user") id_user : Int?,
        @Query("name") name: String,
        @Query("school") school: String,
        @Query("description") description: String,
        @Part photo:MultipartBody.Part?
    ): String

    @FormUrlEncoded
    @POST("like")
    suspend fun postFavourite(
        @Field("users_id")user_id : Int?,
        @Field("user_id_i_like")user_id_like : Int?
    ) : String

}