package oc.android_exercice.sequence1_todo.data.api

import oc.android_exercice.sequence1_todo.data.model.AuthentificationResponse
import oc.android_exercice.sequence1_todo.data.model.ItemsResponse
import oc.android_exercice.sequence1_todo.data.model.ListsResponse
import retrofit2.http.*


interface ToDoApiService {
    @FormUrlEncoded
    @POST("authenticate")
    suspend fun authentification(@Field("user") pseudo: String, @Field("password") password: String) : AuthentificationResponse

    @GET("lists")
    suspend fun getLists(@Query("hash") hash: String) : ListsResponse

    @GET("lists/{idList}/items")
    suspend fun getItems(@Path("idList") idList: String, @Query("hash") hash: String) : ItemsResponse
}