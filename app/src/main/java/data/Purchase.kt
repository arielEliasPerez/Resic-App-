package data

data class Purchase(
    val id: Long,
    var userId: Long,
    val productId: Long,
    val amount: Double,
    val createdDate: String,

    )
