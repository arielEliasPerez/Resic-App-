package data

data class User(
    val id: Long,
    var nickName: String,
    val password: String,
    val name: String,
    val surname: String,
    var money: Double ,
    val createdDate: String,
    var logged: Boolean = false

)
