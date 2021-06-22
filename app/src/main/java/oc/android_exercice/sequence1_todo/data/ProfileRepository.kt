package oc.android_exercice.sequence1_todo.data

import android.app.Application
import oc.android_exercice.sequence1_todo.data.source.local.LocalDataSource
import oc.android_exercice.sequence1_todo.data.source.remote.RemoteDataSource

class ProfileRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
){
    suspend fun authenticate(username: String, password: String): String{
        return remoteDataSource.authentificationFromApi(username,password)
    }
    companion object {
        fun newInstance(application: Application): ProfileRepository {
            return ProfileRepository(
                localDataSource = LocalDataSource(application),
                remoteDataSource = RemoteDataSource()
            )
        }
    }
}