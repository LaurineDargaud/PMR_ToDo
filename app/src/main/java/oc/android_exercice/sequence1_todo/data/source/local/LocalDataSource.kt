package oc.android_exercice.sequence1_todo.data.source.local

import android.app.Application
import androidx.room.Room
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.data.source.local.database.ToDoRoomDatabase

class LocalDataSource(application: Application) {

    private val roomDatabase =
        Room.databaseBuilder(application, ToDoRoomDatabase::class.java, "room-database").build()

    private val listDao = roomDatabase.listDao()

    suspend fun getLists() = listDao.getLists()
    suspend fun saveOrUpdate(lists: List<ListeToDo>) = listDao.saveOrUpdate(lists)
}