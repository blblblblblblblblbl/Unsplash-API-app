package com.blblblbl.myapplication.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow

interface Repository {

    fun getSavedBearerToken():String?

}