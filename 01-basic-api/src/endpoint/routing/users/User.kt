package endpoint.routing.users

data class User (val name : String, val age: Int){
    var id : Int? = null

    //Necesario para que las operación routing se hagan correctamente
    override fun equals(other: Any?): Boolean =
        when(other is User) {
            true -> other.name == name
            else -> super.equals(other)
        }

    //Necesario para que las operación routing se hagan correctamente
    override fun hashCode() =
        name.hashCode()
}