package oc.android_exercice.sequence1_todo.data.source.remote.api

import com.google.gson.annotations.SerializedName
import oc.android_exercice.sequence1_todo.data.model.ItemToDo
import oc.android_exercice.sequence1_todo.data.model.ItemsResponse
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.data.model.ListsResponse
import retrofit2.http.*


interface ToDoApiService {

    //Requête pour l'authentification
    @FormUrlEncoded
    @POST("authenticate")
    suspend fun authentification(
        @Field("user") pseudo: String,
        @Field("password") password: String
    ): AuthentificationResponse

    //Requête pour récupérer l'ensemble des listes d'un utilisateur connecté
    @GET("lists")
    suspend fun getLists(@Header("hash") hash: String): ListsResponse

    // Requête pour récupérer les items d'une liste d'id connu
    @GET("lists/{idList}/items")
    suspend fun getItems(
        @Path("idList") idList: String,
        @Header("hash") hash: String
    ): ItemsResponse

    // Requête pour modifier l'état d'un item connu (fait ou non)
    @PUT("lists/{idList}/items/{idItem}")
    suspend fun updateCheckItem(
        @Path("idList") idList: String,
        @Path("idItem") idItem: String,
        @Query("check") check: String,
        @Header("hash") hash: String
    )

    data class ItemResponseApi(@SerializedName("item") val item: ItemToDo)

    // Requête pour ajouter un item à une liste
    @FormUrlEncoded
    @POST("lists/{idList}/items?label=Nouvel item")
    suspend fun addItem(
        @Path("idList") idList: String,
        @Field("label") label: String,
        @Header("hash") hash: String
    ): ItemResponseApi

    data class ListResponseApi(@SerializedName("list") val list: ListeToDo)

    // Requête pour ajouter une liste
    @FormUrlEncoded
    @POST("lists")
    suspend fun addList(
        @Field("label") label: String,
        @Header("hash") hash: String
    ): ListResponseApi
}