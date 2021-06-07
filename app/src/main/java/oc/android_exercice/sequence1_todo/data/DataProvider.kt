package oc.android_exercice.sequence1_todo.data

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import oc.android_exercice.sequence1_todo.data.api.ToDoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DataProvider {
    //Initialisation des variables
    /*var sp : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    var sp_editor : SharedPreferences.Editor = sp?.edit()
    var BASE_URL : String? = sp?.getString("baseURL","http://tomnab.fr/todo-api/")*/

    //var BASE_URL : String = "http://tomnab.fr/todo-api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service = retrofit.create(ToDoApiService::class.java)


    suspend fun authentificationFromApi(username:String, password:String): String {
        return service.authentification(username,password).hash
    }

}