package oc.android_exercice.sequence1_todo.data

import android.app.Application
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.data.source.local.LocalDataSource
import oc.android_exercice.sequence1_todo.data.source.remote.RemoteDataSource

class ListRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
    ) {

        suspend fun getLists(): List<ListeToDo> {
            return try {

                remoteDataSource.getListsFromApi().also {
                    localDataSource.saveOrUpdate(it)
                }

            } catch (e: Exception) {
                localDataSource.getLists()
            }
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