package com.blblblbl.myapplication.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow

interface Repository {

    fun authorize(code: String, authSuccess: MutableStateFlow<Boolean?>)

    fun getSavedBearerToken():String?

}