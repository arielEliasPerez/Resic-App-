package prices

import price.PriceCalculator
import java.time.DayOfWeek
import java.time.LocalDate

class PlatinumPrice(productID: Long, originPrice: Double) : PriceCalculator(productID, originPrice) {
    override var commission = 0.75

    override fun calculateTotalPrice(): Double {
        if (isValidDate()) this.commission = 3.0

        val commissionPrice = super.calculateCommissionPrice()

        return this.originPrice + commissionPrice
    }

    private fun isValidDate(): Boolean {
        val currentDay = LocalDate.now().dayOfWeek
        val saturday = DayOfWeek.SATURDAY
        val sunday = DayOfWeek.SUNDAY

        return currentDay == saturday || currentDay == sunday
    }
}