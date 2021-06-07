package oc.android_exercice.sequence1_todo.data

import android.util.Log
import com.google.gson.annotations.SerializedName
import oc.android_exercice.sequence1_todo.ItemToDo
import oc.android_exercice.sequence1_todo.ListeToDo
import oc.android_exercice.sequence1_todo.data.api.ToDoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DataProvider(BASE_URL: String){

        private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ToDoApiService::class.java)


    suspend fun authentificationFromApi(username:String, password:String): String {
        return service.authentification(username,password).hash
    }

    suspend fun getListsFromApi(hash:String): MutableList<ListeToDo> {
        return service.getLists(hash).lists.toMutableList()
    }

    suspend fun getItemsFromApi(hash:String, idList:String): MutableList<ItemToDo> {
        return service.getItems(idList, hash).items.toMutableList()
    }

    suspend fun updateCheckItemFromApi(hash:String, idList:String, idItem:String, fait_intValue:String) {
        var newFaitIntValue:String = if (fait_intValue == "1") "0" else "1"
        return service.updateCheckItem(idList, idItem, newFaitIntValue, hash)
    }

    suspend fun addItemFromApi(hash:String, idList:String, labelItem:String): ItemToDo{
        return service.addItem(idList,labelItem,hash).item
    }

    suspend fun addListFromApi(hash:String, label:String): ListeToDo{
        return service.addList(label,hash).list
    }

}