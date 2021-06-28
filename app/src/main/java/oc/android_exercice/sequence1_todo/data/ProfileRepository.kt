package oc.android_exercice.sequence1_todo.data

import android.app.Application
import android.util.Log
import oc.android_exercice.sequence1_todo.data.model.ProfilListeToDo
import oc.android_exercice.sequence1_todo.data.source.local.LocalDataSource
import oc.android_exercice.sequence1_todo.data.source.remote.RemoteDataSource
import java.lang.Exception

class ProfileRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
){
    suspend fun authenticate(username: String, password: String): String{
        return try{
            remoteDataSource.authentificationFromApi(username,password)
        } catch (e:Exception){
            getIdUser(username,password)
        }
    }

    suspend fun getIdUser(username:String, password: String): String{
        var idUser = localDataSource.authenticate(username, password)
        Log.d("Test","{$idUser}")
        return idUser
    }

    suspend fun addProfileToLocal(username: String, password: String){
        return localDataSource.addProfile(ProfilListeToDo(login = username,password = password))
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