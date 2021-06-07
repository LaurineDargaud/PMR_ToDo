package oc.android_exercice.sequence1_todo.data.api

import com.google.gson.annotations.SerializedName
import oc.android_exercice.sequence1_todo.ItemToDo
import oc.android_exercice.sequence1_todo.ListeToDo
import oc.android_exercice.sequence1_todo.data.model.AuthentificationResponse
import oc.android_exercice.sequence1_todo.data.model.ItemsResponse
import oc.android_exercice.sequence1_todo.data.model.ListsResponse
import retrofit2.http.*


interface ToDoApiService {
    @FormUrlEncoded
    @POST("authenticate")
    suspend fun authentification(
        @Field("user") pseudo: String,
        @Field("password") password: String
    ) : AuthentificationResponse

    @GET("lists")
    suspend fun getLists(@Header("hash") hash: String) : ListsResponse

    @GET("lists/{idList}/items")
    suspend fun getItems(
        @Path("idList") idList: String,
        @Header("hash") hash: String
    ) : ItemsResponse

    @PUT("lists/{idList}/items/{idItem}")
    suspend fun updateCheckItem(
        @Path("idList") idList : String,
        @Path("idItem") idItem : String,
        @Query("check") check : String,
        @Header("hash") hash : String
    )

    data class ItemResponse(@SerializedName("item") val item : ItemToDo)

    @FormUrlEncoded
    @POST("lists/{idList}/items?label=Nouvel item")
    suspend fun addItem(
        @Path("idList") idList : String,
        @Field("label") label : String,
        @Header("hash") hash : String
    ) : ItemResponse

    data class ListResponse(@SerializedName("list") val list : ListeToDo)

    @FormUrlEncoded
    @POST("lists")
    suspend fun addList(
        @Field("label") label : String,
        @Header("hash") hash : String
    ) : ListResponse
}