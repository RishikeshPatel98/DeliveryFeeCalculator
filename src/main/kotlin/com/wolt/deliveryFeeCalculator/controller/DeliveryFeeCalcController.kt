package com.wolt.deliveryFeeCalculator.controller

import com.wolt.deliveryFeeCalculator.entities.DeliveryFee
import com.wolt.deliveryFeeCalculator.entities.DeliveryFeeCalc
import com.wolt.deliveryFeeCalculator.service.DeliveryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DeliveryFeeCalcController(val deliveryService: DeliveryService) {

    @GetMapping("/hello")
    fun hello() : String{
        return "hello-world"
    }

    @PostMapping("/calculateDeliveryFee")
    fun calculateDeliveryFee(@RequestBody request: DeliveryFeeCalc): ResponseEntity<DeliveryFee> {
        val response = deliveryService.calculateDeliveryFee(request)
        return ResponseEntity(response, HttpStatus.OK)

    }
}