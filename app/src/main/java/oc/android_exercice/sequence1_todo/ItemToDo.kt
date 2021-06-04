package oc.android_exercice.sequence1_todo

class ItemToDo(
        var description: String = "Nouvel Item",
        var fait: Boolean = false) {
    fun changeFait(){
        if (fait){
            fait = false
        }
        else{
            fait = true
        }
    }
    override fun toString(): String {
        return "description='$description'"
    }
}