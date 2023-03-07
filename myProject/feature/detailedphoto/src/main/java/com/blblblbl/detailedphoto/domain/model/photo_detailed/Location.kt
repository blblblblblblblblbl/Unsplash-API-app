package com.blblblbl.detailedphoto.domain.model.photo_detailed




data class Location(

    var city: String? = null,
    var country: String? = null,
    var position: Position? = Position()

)