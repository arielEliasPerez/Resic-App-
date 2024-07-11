package prices

import price.PriceCalculator
import java.time.LocalTime

class SilverPrice(productID: Long, originPrice: Double) : PriceCalculator(productID, originPrice) {
    override var commission = 3.0

    override fun calculateTotalPrice(): Double {
        if (isValidTime()) this.commission = 1.0

        val commissionPrice = super.calculateCommissionPrice()

        return originPrice + commissionPrice
    }


    private fun isValidTime(): Boolean {
        val currentTime = LocalTime.now()
        val minimumTime = LocalTime.of(15, 0)
        val maximumTime = LocalTime.of(22, 30)
        val isTimeValid = currentTime in minimumTime..maximumTime

        return isTimeValid
    }
}