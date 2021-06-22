package oc.android_exercice.sequence1_todo.data.source.local.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.data.model.ProfilListeToDo

interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(profils: List<ProfilListeToDo>)

    @Query("SELECT login FROM ProfileListeToDo WHERE login=:login AND pass=:pass")
    suspend fun authenticate(login: String, pass: String): String
}