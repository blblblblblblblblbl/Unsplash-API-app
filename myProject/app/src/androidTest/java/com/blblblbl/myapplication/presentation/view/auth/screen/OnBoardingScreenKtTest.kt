package com.blblblbl.myapplication.presentation.view.auth.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.blblblbl.auth.ui.OnBoardingScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Assert.*
import com.blblblbl.auth.R

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnBoardingScreenKtTest {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @get: Rule
    val composeTestRule = createComposeRule()   // compose rule is required to get access to the composable component

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
    val buttonSkip = hasText(appContext.getString(R.string.skip)) and hasClickAction()
    val buttonLogIn = hasText(appContext.getString(R.string.log_in)) and hasClickAction()
    @OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
    @Test
    fun checkSkipButtonIsShown(){
        composeTestRule.setContent { OnBoardingScreen {} }
        composeTestRule.onNode(buttonSkip).assertExists()
    }
    @OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
    @Test
    fun checkLogInButtonIsNotShownInitially(){
        composeTestRule.setContent { OnBoardingScreen {} }
        composeTestRule.onNode(buttonLogIn).assertDoesNotExist()
    }
    @OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
    @Test
    fun checkLogInButtonIsShownAfterSkip(){
        composeTestRule.setContent { OnBoardingScreen {} }
        composeTestRule.onNode(buttonSkip).performClick()
        composeTestRule.onNode(buttonLogIn).assertExists()
    }
}