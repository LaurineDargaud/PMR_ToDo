package oc.android_exercice.sequence1_todo

class ItemToDo (
    var description: String = "Nouvel Item",
    var fait: Boolean = false) {
    override fun toString(): String {
        return "description='$description'"
    }


}