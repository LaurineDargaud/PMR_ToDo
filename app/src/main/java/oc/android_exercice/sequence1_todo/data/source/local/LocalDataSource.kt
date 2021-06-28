package oc.android_exercice.sequence1_todo.data.source.local

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import oc.android_exercice.sequence1_todo.data.model.ItemToDo
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.data.model.ModifItem
import oc.android_exercice.sequence1_todo.data.model.ProfilListeToDo
import oc.android_exercice.sequence1_todo.data.source.local.database.ToDoRoomDatabase

class LocalDataSource(application: Application) {

    private val roomDatabase =
        Room.databaseBuilder(application, ToDoRoomDatabase::class.java, "room-database").build()

    // ProfileListeToDo
    private val profilDao = roomDatabase.profileDao()

    suspend fun authenticate(username:String, password: String):String{
        val authRep = profilDao.authenticate(username, password)
        Log.d("Authenticate rep","{$authRep}")
        return authRep.firstOrNull().toString()
    }

    suspend fun addProfile(profile:ProfilListeToDo){
        Log.d("LocalDataSource","Ajout du profil : {$profile}")
        profilDao.addProfile(profile)
    }

    // ListeToDo
    private val listDao = roomDatabase.listDao()

    suspend fun getLists(idUser:String) = listDao.getLists(idUser)
    suspend fun saveOrUpdateLists(lists: List<ListeToDo>) = listDao.saveOrUpdate(lists)

    // ItemToDo
    private val itemDao = roomDatabase.itemDao()

    suspend fun getItems(idList:String) = itemDao.getItems(idList)
    suspend fun saveOrUpdateItems(items: List<ItemToDo>) = itemDao.saveOrUpdate(items)
    suspend fun updateCheckItem(item:ItemToDo) = itemDao.updateCheckItem(item)

    // ModifItem
    private val modifItemDao = roomDatabase.modifItemDao()

    suspend fun addLocalItemCheckUpdate(idList:String, item:ItemToDo) = modifItemDao.addModifItem(
        ModifItem(idList = idList.toInt(), idItem = item.id, newCheckValue = item.fait_intValue)
    )
    suspend fun getAllModifItem() = modifItemDao.getAll()




}