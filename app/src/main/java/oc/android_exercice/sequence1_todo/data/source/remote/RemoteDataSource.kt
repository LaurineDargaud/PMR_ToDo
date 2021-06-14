package oc.android_exercice.sequence1_todo.data.source.remote

import oc.android_exercice.sequence1_todo.data.model.ItemToDo
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.data.source.remote.api.ListResponse
import oc.android_exercice.sequence1_todo.data.source.remote.api.ToDoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RemoteDataSource {
    //Initialisation des variables
    /*var sp : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    var sp_editor : SharedPreferences.Editor = sp?.edit()
    var BASE_URL : String? = sp?.getString("baseURL","http://tomnab.fr/todo-api/")
    Erreur de contexte : comment récupérer le contexte dans une classe object ... */

    var BASE_URL: String = "http://tomnab.fr/todo-api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(
        ToDoApiService::class.java)


    suspend fun authentificationFromApi(username: String, password: String): String {
        return service.authentification(username, password).hash
    }

    suspend fun getListsFromApi(hash: String): MutableList<ListeToDo> {
        return service.getLists(hash).lists.toLists().toMutableList()
    }

    suspend fun getItemsFromApi(hash: String, idList: String): MutableList<ItemToDo> {
        return service.getItems(idList, hash).items.toMutableList()
    }

    suspend fun updateCheckItemFromApi(
        hash: String,
        idList: String,
        idItem: String,
        fait_intValue: String
    ) {
        var newFaitIntValue: String = if (fait_intValue == "1") "0" else "1"
        return service.updateCheckItem(idList, idItem, newFaitIntValue, hash)
    }

    // No local mode
    suspend fun addItemFromApi(hash: String, idList: String, labelItem: String): ItemToDo {
        return service.addItem(idList, labelItem, hash).item
    }

    // No local mode
    suspend fun addListFromApi(hash: String, label: String): ListeToDo {
        return service.addList(label, hash).list
    }

    private fun List<ListResponse>.toLists() = this.map { listResponse ->
        ListeToDo(
            id = listResponse.id,
            titreListeToDo = listResponse.titreListeToDo,
            items = listResponse.items
        )
    }
}