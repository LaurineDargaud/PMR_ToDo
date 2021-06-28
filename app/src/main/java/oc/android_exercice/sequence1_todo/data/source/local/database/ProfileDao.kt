package oc.android_exercice.sequence1_todo.data.source.local.database

import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import oc.android_exercice.sequence1_todo.data.model.ProfilListeToDo

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(profils: List<ProfilListeToDo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProfile(profil: ProfilListeToDo)

    @Query("SELECT idUser FROM ProfilListeToDo WHERE login=:login AND password=:pass")
    suspend fun authenticate(login:String, pass:String):List<Int>

}