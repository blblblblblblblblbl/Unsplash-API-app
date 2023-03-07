package com.blblblbl.search.data.datasource

import com.blblblbl.search.data.model.photo.Photo
import com.blblblbl.search.data.network.SearchApi
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchApi: SearchApi
) :SearchDataSource {
    override suspend fun searchPhotos(page: Int, perPage: Int, query: String): List<Photo> =
        searchApi.searchPhotos(page,perPage,query).results

    override suspend fun like(id: String) =
        searchApi.like(id)

    override suspend fun unlike(id: String) =
        searchApi.unlike(id)
}