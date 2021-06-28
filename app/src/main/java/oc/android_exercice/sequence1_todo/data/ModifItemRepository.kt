package oc.android_exercice.sequence1_todo.data

import android.app.Application
import android.util.Log
import oc.android_exercice.sequence1_todo.data.source.local.LocalDataSource
import oc.android_exercice.sequence1_todo.data.source.remote.RemoteDataSource

class ModifItemRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun pushModifItem(hash:String){
        var modifs = localDataSource.getAllModifItem()
        Log.d("ModifItemRepo","modifs = {$modifs}")
        for (modif in modifs){
            remoteDataSource.updateCheckItemFromApi(hash, modif.idList.toString(), modif.idItem.toString(), modif.newCheckValue.toString())
        }
        localDataSource.deleteAllModifItem()
    }

    companion object {
        fun newInstance(application: Application): ModifItemRepository {
            return ModifItemRepository(
                localDataSource = LocalDataSource(application),
                remoteDataSource = RemoteDataSource()
            )
        }
    }
}