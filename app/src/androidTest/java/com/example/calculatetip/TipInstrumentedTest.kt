package com.example.calculatetip

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.onSiblings
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TipInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testInitialState() {
        composeTestRule.onNodeWithText("Calculate Tip").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bill Amount").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tip Percentage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Round Up Tip?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tip Amount: $0.00").assertIsDisplayed()
    }

    @Test
    fun testCalculateTip() {
        composeTestRule.onNodeWithText("Bill Amount").performTextInput("100")
        composeTestRule.onNodeWithText("Tip Amount: $15.00").assertIsDisplayed()
    }

    @Test
    fun testCalculateTipWithPercentage() {
        composeTestRule.onNodeWithText("Bill Amount").performTextInput("100")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20")
        composeTestRule.onNodeWithText("Tip Amount: $20.00").assertIsDisplayed()
    }

    @Test
    fun testRoundUpTip() {
        composeTestRule.onNodeWithText("Bill Amount").performTextInput("2389")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("18")
        composeTestRule.onNodeWithContentDescription("Round Up Tip Switch").performClick()
        composeTestRule.onNodeWithText("Tip Amount: $431.00").assertIsDisplayed()
    }

    @Test
    fun testUIElementsPresence() {
        composeTestRule.onNodeWithText("Bill Amount").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tip Percentage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Round Up Tip?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tip Amount: $0.00").assertIsDisplayed()
    }
}