package com.wolt.DeliveryFeeCalculator.service

import com.wolt.DeliveryFeeCalculator.entities.DeliveryFee
import com.wolt.DeliveryFeeCalculator.entities.DeliveryFeeCalc
import org.springframework.stereotype.Service

@Service
class DeliveryService {
    fun calculateDeliveryFee(request: DeliveryFeeCalc): DeliveryFee {
    val cartValueEuros = convertToEuros(request.cartValue)

    val smallOrderSurcharge = calculateSmallOrderSurcharge(cartValueEuros)

    var deliveryFee = calculateBaseDeliveryFee()

    val additionalDistance = request.deliveryDistance - 1000
    deliveryFee += calculateAdditionalDistanceFee(additionalDistance)

    val itemSurcharge = calculateItemSurcharge(request.numberOfItems)

    val bulkItemSurcharge = calculateBulkItemSurcharge(request.numberOfItems)

    val totalSurcharge = calculateTotalSurcharge(smallOrderSurcharge, itemSurcharge, bulkItemSurcharge)

    deliveryFee += applySurcharges(deliveryFee, totalSurcharge)

    return DeliveryFee(deliveryFee = deliveryFee)
}

private fun convertToEuros(cartValue: Int): Double {
    return cartValue / 100.0
}

private fun calculateSmallOrderSurcharge(cartValueEuros: Double): Double {
    return if (cartValueEuros < 10) {
        10 - cartValueEuros
    } else {
        0.0
    }
}

private fun calculateBaseDeliveryFee(): Int {
    return 200 // 2€ in cents
}

private fun calculateAdditionalDistanceFee(additionalDistance: Int): Int {
    return (additionalDistance / 500 + if (additionalDistance % 500 > 0) 1 else 0) * 100 // 1€ per 500 meters
}

private fun calculateItemSurcharge(numberOfItems: Int): Int {
    return if (numberOfItems >= 5) {
        (numberOfItems - 4) * 50
    } else {
        0
    }
}

private fun calculateBulkItemSurcharge(numberOfItems: Int): Int {
    return if (numberOfItems > 12) {
        ((numberOfItems - 12) * 50) + 120
    } else {
        0
    }
}

private fun calculateTotalSurcharge(
    smallOrderSurcharge: Double,
    itemSurcharge: Int,
    bulkItemSurcharge: Int
): Double {
    return (smallOrderSurcharge + itemSurcharge + bulkItemSurcharge).coerceAtMost(1500.0) // Max 15€
}

private fun applySurcharges(deliveryFee: Int, totalSurcharge: Double): Int {
    return (deliveryFee + totalSurcharge).coerceAtMost(1500)
}

}
