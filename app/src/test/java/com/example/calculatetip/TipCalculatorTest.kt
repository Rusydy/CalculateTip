package com.example.calculatetip

import org.junit.Test
import org.junit.Assert.assertEquals

class TipCalculatorTest {
    @Test
    fun testCalculateTip_15PercentNoRoundUp() {
        val billAmount = 100.0
        val tipPercentage = 15
        val roundUp = false

        val actualTip = calculateTip(billAmount, tipPercentage, roundUp)
        val expectedTip = 15.0

        assertEquals(expectedTip, actualTip, 0.0)
    }
}