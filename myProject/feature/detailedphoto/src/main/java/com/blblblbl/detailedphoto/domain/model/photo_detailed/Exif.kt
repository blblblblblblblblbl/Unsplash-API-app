package com.blblblbl.detailedphoto.domain.model.photo_detailed



data class Exif(

    var make: String? = null,
    var model: String? = null,
    var name: String? = null,
    var exposureTime: String? = null,
    var aperture: String? = null,
    var focalLength: String? = null,
    var iso: Int? = null

)