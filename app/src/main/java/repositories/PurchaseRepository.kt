package repositories

import data.Purchase
import data.User
import java.time.LocalDate

object PurchaseRepository {

    private val purchases = mutableListOf<Purchase>()

    init {
        purchases.add(Purchase(1L,  1504L,  1L,  151500.0, "01/01/2023"))
        purchases.add(Purchase(2L,  1504L,  4L,  35000.0,  "01/01/2023"))
        purchases.add(Purchase(3L,  1504L,  7L,  49490.0,  "01/01/2023"))
        purchases.add(Purchase(4L,  1504L,  10L, 489.6,    "01/01/2023"))
        purchases.add(Purchase(5L,  1504L,  3L,  56100.0,  "01/01/2023"))
        purchases.add(Purchase(6L,  2802L,  2L,  1545.0,   "01/01/2023"))
        purchases.add(Purchase(7L,  2802L,  9L,  650.0,    "01/01/2023"))
        purchases.add(Purchase(8L,  2802L,  2L,  1545.0,   "01/01/2023"))
        purchases.add(Purchase(9L,  1510L,  6L,  53040.0,  "01/01/2023"))
        purchases.add(Purchase(10L, 1510L,  5L,  64800.0,  "01/01/2023"))
    }

    fun processPurchase(priceProduct: Double, user: User , idProduct : Long): Boolean {

        if (priceProduct > user.money) return false
//
        val idPurchase = nextId()
        val userId: Long = user.id
        val productId: Long = idProduct
        val createdData = "${LocalDate.now()}"

        val purchase = Purchase(idPurchase, userId, productId, priceProduct, createdData)

        user.money -= priceProduct
        this.add(purchase)

        return true
    }

    private fun nextId(): Long {
        return this.purchases.maxOf { it.id }+1
    }

    private fun add(purchase: Purchase) {
        this.purchases.add(0, purchase)
    }

    fun get(): List<Purchase> {
        return this.purchases
    }

    fun getId(id : Long): Purchase {
        return purchases.find{ it.id == id }!!
    }
}