package oc.android_exercice.sequence1_todo.data

import android.app.Application
import android.util.Log
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

    suspend fun updateCheckItem(
        hash: String,
        idList: String,
        clickedItem : ItemToDo
    ) {
        // dans tous les cas, on màj la base locale
        localDataSource.updateCheckItem(clickedItem)
        try{
            // on met à jour par appel API
            remoteDataSource.updateCheckItemFromApi(
                hash,
                idList,
                clickedItem.id.toString(),
                clickedItem.fait_intValue.toString()
            )

        } catch (e: Exception) {
            // on ajoute l'édit dans la table des edits
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