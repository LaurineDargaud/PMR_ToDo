package oc.android_exercice.sequence1_todo.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["login", "password"], unique = true)])
data class ProfilListeToDo(

    @PrimaryKey(autoGenerate = true)
    val idUser: Int? = null,
    val login: String = "login inconnu",
    val password: String
){
    override fun toString():String{
        return "ProfilListeToDo($idUser, $login, $password)"
    }
}