package user

import data.Product
import data.ProductType
import data.Purchase
import repositories.ProductRepository
import repositories.PurchaseRepository
import repositories.UserRepository

object UserSystem{
    val user = UserRepository.getUserLogged()

    fun userPurchases() = PurchaseRepository.get().filter { it.userId == user!!.id }

    fun userProducts(): List<Product> {
        val idProductsUser = userPurchases().map { it.productId }
        val products = ProductRepository.get().filter { it.id in idProductsUser }

        return products
    }

    fun getLastPurchase(): String? = userPurchases().maxOfOrNull { it.createdDate }

    //cantidad de libros
    fun getQuantityBook(): Int = purchaseType(ProductType.BOOK).size

    //cantidad de discos
    fun getQuantityDisc(): Int = purchaseType(ProductType.DISC).size

    //cantidad de productos
    fun getQuantityProducts(): Int = userPurchases().size

    //cantidad gastada en total
    fun getSpent(): Double = userPurchases().sumOf { it.amount }

    //cantidad gastada de discos
    fun getSpentDisc(): Double = purchaseType(ProductType.DISC).sumOf { it.amount }

    //cantidad gastada de libros
    fun getSpentBook(): Double = purchaseType(ProductType.BOOK).sumOf { it.amount }


    fun purchaseType(type: ProductType): List<Purchase> {
        val productType = ProductRepository.get().filter { it.type == type }

        val productId = productType.map { it.id }

        return userPurchases().filter { it.productId in productId }

    }

    fun getPreferredProducts(): List<Product> {
        val all = -1
        val productsBook = 0

        return when (comparator()) {
            all -> ProductRepository.get().take(10)

            productsBook -> ProductRepository.get().filter { it.type == ProductType.BOOK }.take(6)

            else -> ProductRepository.get().filter { it.type == ProductType.DISC }.take(4)
        }
    }

    private fun comparator() : Int{
        val countTypeDisc = userProducts().filter { it.type == ProductType.DISC }.size

        val countTypeBook = userProducts().filter { it.type == ProductType.BOOK }.size

        if (countTypeBook == countTypeDisc) return -1

        if (countTypeBook > countTypeDisc) return 0

        return 1
    }
}