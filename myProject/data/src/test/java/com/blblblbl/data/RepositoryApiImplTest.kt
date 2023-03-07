package com.blblblbl.data

import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApiImpl
import com.blblblbl.myapplication.data.repository.repository_api.utils.MockRequestInterceptor
import com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces.LikeApi
import okhttp3.ResponseBody
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit


class RepositoryApiImplTest {
    /*private lateinit var repositoryApi: RepositoryApiImpl
    private lateinit var testApis: LikeApi
    private lateinit var mockWebServer: MockWebServer

    val serverDispatcher: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            when (request.path) {
                "https://api.unsplash.com/photos" -> return MockResponse()
                    .setBody("aaaaa")
                    .setResponseCode(200)
                "/api-url-two" -> return MockResponse()
                    .setHeader("x-header-name", "header-value")
                    .setResponseCode(200)
                    .setBody("<response />")
                "/api-url-three" -> return MockResponse()
                    .setResponseCode(500)
                    .setBodyDelay(5000, TimeUnit.SECONDS)
                    .setChunkedBody("<error-response />", 5)
                "/api-url-four" -> return MockResponse()
                    .setResponseCode(200)
                    .setBody("{\"data\":\"\"}")
                    .throttleBody(1024, 5, TimeUnit.SECONDS)
            }
            return MockResponse().setResponseCode(404)
        }

    }
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = serverDispatcher
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPhotosPage() {
    }

    @Test
    fun testGetPhotosPage() {
    }

    @Test
    fun searchPhotos() {
    }

    @Test
    fun getCollectionPage() {
    }

    @Test
    fun getUserInfo() {
    }

    @Test
    fun getPublicUserInfo() {
    }

    @Test
    fun getLikedPhotosPage() {
    }

    @Test
    fun getPhotoById() {
    }

    @Test
    fun getCollectionPhotoList() {
    }

    @Test
    fun like() {
    }

    @Test
    fun unlike() {
    }*/
}