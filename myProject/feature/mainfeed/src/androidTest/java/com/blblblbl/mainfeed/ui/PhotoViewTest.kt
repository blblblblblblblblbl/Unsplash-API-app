package com.blblblbl.mainfeed.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.blblblbl.mainfeed.domain.model.photos.Photo
import com.blblblbl.mainfeed.domain.model.photos.User
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoViewTest {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @get: Rule
    val composeTestRule = createComposeRule()   // compose rule is required to get access to the composable component
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
    val nameMatcher = hasTestTag("name")
    val usernameMatcher = hasTestTag("username")
    val likesNumberMatcher = hasTestTag("likes")
    val falseLikeIconMatcher = hasTestTag("likeIconFalse")
    val trueLikeIconMatcher = hasTestTag("likeIconTrue")

    @Test
    fun allElementsIsShown(){
        val photo = Photo(user = User(name = "name", username = "username"), likes = 576)
        composeTestRule.setContent {
            PhotoView(
                photo = photo,
                onClick = {},
                changeLike = {s, b -> {} }
            )
        }

        composeTestRule.onNode(nameMatcher,useUnmergedTree = true).assertExists()
        composeTestRule.onNode(usernameMatcher,useUnmergedTree = true).assertExists()
        composeTestRule.onNode(likesNumberMatcher,useUnmergedTree = true).assertExists()
        composeTestRule.onNode(falseLikeIconMatcher).assertExists()
    }
    @Test
    fun likeWorks(){
        val photo = Photo(user = User(name = "name", username = "username"), likes = 576)
        composeTestRule.setContent {
            PhotoView(
                photo = photo,
                onClick = {},
                changeLike = {s, b -> {} }
            )
        }

        composeTestRule.onNode(nameMatcher,useUnmergedTree = true).assertExists()
        composeTestRule.onNode(usernameMatcher,useUnmergedTree = true).assertExists()
        composeTestRule.onNode(likesNumberMatcher,useUnmergedTree = true).assertExists()
        composeTestRule.onNode(falseLikeIconMatcher).assertExists()
        composeTestRule.onNode(falseLikeIconMatcher).performClick()
        composeTestRule.onNode(trueLikeIconMatcher).assertExists()
    }
}