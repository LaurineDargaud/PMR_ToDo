package oc.android_exercice.sequence1_todo.data

import android.app.Application
import oc.android_exercice.sequence1_todo.data.model.ItemToDo
import oc.android_exercice.sequence1_todo.data.source.local.LocalDataSource
import oc.android_exercice.sequence1_todo.data.source.remote.RemoteDataSource

class ItemRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource){

    suspend fun getItems( hash: String, idList: String): List<ItemToDo> {
        return try {
            val items = remoteDataSource.getItemsFromApi(hash, idList)
            items.map{
                it.idList = idList.toInt()
            }
            localDataSource.saveOrUpdateItems(items)
            items
        } catch (e: Exception) {
            localDataSource.getItems(idList)
        }
    }

    companion object {
        fun newInstance(application: Application): ItemRepository {
            return ItemRepository(
                localDataSource = LocalDataSource(application),
                remoteDataSource = RemoteDataSource()
            )
        }
    }

}