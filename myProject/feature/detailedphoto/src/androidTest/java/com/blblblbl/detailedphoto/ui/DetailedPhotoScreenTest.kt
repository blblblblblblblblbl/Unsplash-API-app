package com.blblblbl.detailedphoto.ui

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.blblblbl.detailedphoto.domain.model.photo_detailed.DetailedPhotoInfo
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Exif
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Location
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailedPhotoScreenTest {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @get: Rule
    val composeTestRule = createComposeRule()   // compose rule is required to get access to the composable component

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
    val buttonLocation = hasTestTag("locationButton") and hasClickAction()
    val buttonDownload = hasTestTag("downloadButton") and hasClickAction()
    val buttonShare = hasTestTag("shareButton") and hasClickAction()
    val exifDescription = hasTestTag("exifDescription")

    @Test
    fun allButtonIsShown(){
        val detailedPhotoInfo = DetailedPhotoInfo(location = Location(city = "city", country = "country"))
        composeTestRule.setContent { PhotoDescription(detailedPhotoInfo,true,{},{},{},{s,b->{}}) }
        composeTestRule.onNode(buttonLocation).assertExists()
        composeTestRule.onNode(buttonDownload).assertExists()
        composeTestRule.onNode(buttonShare).assertExists()
    }
    @Test
    fun locationButtonIsNotShown(){
        val detailedPhotoInfo = DetailedPhotoInfo(location = Location(city = "city", country = "country"))
        composeTestRule.setContent { PhotoDescription(detailedPhotoInfo,false,{},{},{},{s,b->{}}) }
        composeTestRule.onNode(buttonLocation).assertDoesNotExist()
        composeTestRule.onNode(buttonDownload).assertExists()
        composeTestRule.onNode(buttonShare).assertExists()
    }
    @Test
    fun exifIsNotShown(){
        val detailedPhotoInfo = DetailedPhotoInfo(location = Location(city = "city", country = "country"), exif = null)
        composeTestRule.setContent { PhotoDescription(detailedPhotoInfo,true,{},{},{},{s,b->{}}) }
        composeTestRule.onNode(buttonLocation).assertExists()
        composeTestRule.onNode(buttonDownload).assertExists()
        composeTestRule.onNode(buttonShare).assertExists()
        composeTestRule.onNode(exifDescription).assertDoesNotExist()
    }
    @Test
    fun exifIsShown(){
        val detailedPhotoInfo = DetailedPhotoInfo(location = Location(city = "city", country = "country"), exif = Exif())
        composeTestRule.setContent { PhotoDescription(detailedPhotoInfo,true,{},{},{},{s,b->{}}) }
        composeTestRule.onNode(buttonLocation).assertExists()
        composeTestRule.onNode(buttonDownload).assertExists()
        composeTestRule.onNode(buttonShare).assertExists()
        composeTestRule.onNode(exifDescription).assertExists()
    }
}