package com.wolt.DeliveryFeeCalculator.service

import com.wolt.DeliveryFeeCalculator.entities.DeliveryFee
import com.wolt.DeliveryFeeCalculator.entities.DeliveryFeeCalc
import org.springframework.stereotype.Service

@Service
class DeliveryService {

    fun calculateDeliveryFee(request: DeliveryFeeCalc): DeliveryFee {
        var deliveryFee = 0

        // Convert cart value to euros
        val cartValueEuros = request.cartValue / 100.0

        // Calculate small order surcharge
        val smallOrderSurcharge = if (cartValueEuros < 10) 10 - cartValueEuros else 0.0

        // Calculate base delivery fee for the first 1 km
        deliveryFee += 200 // 2€ in cents

        // Calculate additional fee for distance beyond 1 km
        val additionalDistance = request.deliveryDistance - 1000
        deliveryFee += (additionalDistance / 500 + if (additionalDistance % 500 > 0) 1 else 0) * 100 // 1€ per 500 meters

        // Calculate surcharge for number of items
        val itemSurcharge = if (request.numberOfItems >= 5) (request.numberOfItems - 4) * 50 else 0

        // Calculate bulk item surcharge
        val bulkItemSurcharge = if (request.numberOfItems > 12) ((request.numberOfItems - 12) * 50) + 120 else 0

        // Total surcharge
        val totalSurcharge = (smallOrderSurcharge + itemSurcharge + bulkItemSurcharge).coerceAtMost(1500.0) // Max 15€

        // Apply surcharges
        deliveryFee += totalSurcharge.toInt()

        // Ensure delivery fee does not exceed maximum
        deliveryFee = deliveryFee.coerceAtMost(1500)

        return DeliveryFee(deliveryFee = deliveryFee)
    }



}
