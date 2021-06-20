package oc.android_exercice.sequence1_todo.data

import android.app.Application
import android.util.Log
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.data.source.local.LocalDataSource
import oc.android_exercice.sequence1_todo.data.source.remote.RemoteDataSource

class ListRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
    ) {

        suspend fun getLists( hash: String = "" ): List<ListeToDo> {
            return try {

                remoteDataSource.getListsFromApi(hash).also {
                    localDataSource.saveOrUpdate(it)
                }

            } catch (e: Exception) {
                localDataSource.getLists()
            }
        }

        suspend fun addList(hash: String, label: String): ListeToDo {
            return remoteDataSource.addListFromApi(hash, label)
        }

        companion object {
            fun newInstance(application: Application): ListRepository {
                return ListRepository(
                    localDataSource = LocalDataSource(application),
                    remoteDataSource = RemoteDataSource()
                )
            }
        }
}