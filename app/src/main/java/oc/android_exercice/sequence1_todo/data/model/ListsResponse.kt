package oc.android_exercice.sequence1_todo.data.model
import com.google.gson.annotations.SerializedName
import oc.android_exercice.sequence1_todo.ListeToDo

data class ListsResponse(
    @SerializedName("lists")
    val lists : List<ListeToDo>)