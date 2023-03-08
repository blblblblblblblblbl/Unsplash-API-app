package com.blblblbl.auth.data.model

data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)
