package com.wolt.deliveryFeeCalculator.service

import com.wolt.deliveryFeeCalculator.entities.DeliveryFeeCalc
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DeliveryServiceTest {


    @Test
    fun testCalculateDeliveryFee() {
        // Create a sample DeliveryFeeCalc object for testing
        val deliveryFeeCalc = DeliveryFeeCalc(
            cartValue = 800,           // 8€
            deliveryDistance = 1500,   // 1.5 km
            numberOfItems = 7,
            time = ""
        )


        // Create an instance of the class containing calculateDeliveryFee
        val calculator = DeliveryService()
        // Call the function to calculate the delivery fee
        val result = calculator.calculateDeliveryFee(deliveryFeeCalc)

        // Assert the expected delivery fee based on the provided input
        assertEquals(350, result.deliveryFee) // Explanation below
    }
}