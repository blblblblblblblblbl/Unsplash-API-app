package com.blblblbl.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.blblblbl.search.data.datasource.SearchDataSource
import com.blblblbl.search.data.utils.mapToDomain
import com.blblblbl.search.domain.model.photo.Photo
import com.blblblbl.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
):SearchRepository {
    override fun searchImgByTags(query: String,pageSize: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                SearchPagingSource(searchDataSource = searchDataSource, query = query)
            }
        ).flow.map { pagingData ->
            pagingData.map { photo->photo.mapToDomain()?:Photo()
            }
        }
    }

    override suspend fun like(id: String){
        searchDataSource.like(id)
    }
    override suspend fun unlike(id: String){
        searchDataSource.unlike(id)
    }

}