package oc.android_exercice.sequence1_todo.data.api

import oc.android_exercice.sequence1_todo.data.model.AuthentificationResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ToDoApiService {
    @FormUrlEncoded
    @POST("authenticate")
    suspend fun authentification(@Field("user") pseudo: String, @Field("password") password: String) : AuthentificationResponse
}
