package com.blblblbl.detailedphoto.data.utils



import com.blblblbl.detailedphoto.data.model.photo_detailed.Links as DataPhotoDetailedLinks
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Links as DomainPhotoDetailedLinks
import com.blblblbl.detailedphoto.data.model.photo_detailed.Exif as DataExif
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Exif as DomainExif
import com.blblblbl.detailedphoto.data.model.photo_detailed.CurrentUserCollections as DataCurrentUserCollections
import com.blblblbl.detailedphoto.domain.model.photo_detailed.CurrentUserCollections as DomainCurrentUserCollections
import com.blblblbl.detailedphoto.data.model.photo_detailed.Location as DataLocation
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Location as DomainLocation
import com.blblblbl.detailedphoto.data.model.photo_detailed.Position as DataPosition
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Position as DomainPosition
import com.blblblbl.detailedphoto.data.model.photo_detailed.Tags as DataTags
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Tags as DomainTags
import com.blblblbl.detailedphoto.data.model.photo_detailed.Urls as DataUrls
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Urls as DomainUrls
import com.blblblbl.detailedphoto.data.model.photo_detailed.ProfileImage as DataProfileImage
import com.blblblbl.detailedphoto.domain.model.photo_detailed.ProfileImage as DomainProfileImage
import com.blblblbl.detailedphoto.data.model.photo_detailed.User as DataUser
import com.blblblbl.detailedphoto.domain.model.photo_detailed.User as DomainUser
import com.blblblbl.detailedphoto.data.model.photo_detailed.DetailedPhotoInfo as DataDetailedPhotoInfo
import com.blblblbl.detailedphoto.domain.model.photo_detailed.DetailedPhotoInfo as DomainDetailedPhotoInfo




fun DataPhotoDetailedLinks?.mapToDomain():DomainPhotoDetailedLinks?{
    val domainPhotoDetailedLinks: DomainPhotoDetailedLinks =
        DomainPhotoDetailedLinks(
            this?.self,
            this?.html,
            this?.photos,
            this?.likes,
            this?.portfolio
        )
    return domainPhotoDetailedLinks
}
fun DataExif?.mapToDomain():DomainExif?{
    val domainExif: DomainExif =
        DomainExif(
            this?.make,
            this?.model,
            this?.name,
            this?.exposureTime,
            this?.aperture,
            this?.focalLength,
            this?.iso
        )
    return domainExif
}
fun DataCurrentUserCollections?.mapToDomain():DomainCurrentUserCollections?{
    val domainCurrentUserCollections: DomainCurrentUserCollections =
        DomainCurrentUserCollections(
            this?.id,
            this?.title,
            this?.publishedAt,
            this?.lastCollectedAt,
            this?.updatedAt,
            this?.coverPhoto,
            this?.user
        )
    return domainCurrentUserCollections
}
fun DataPosition?.mapToDomain():DomainPosition?{
    val domainPosition: DomainPosition =
        DomainPosition(
            this?.latitude,
            this?.longitude
        )
    return domainPosition
}
fun DataLocation?.mapToDomain():DomainLocation?{
    val domainLocation: DomainLocation =
        DomainLocation(
            this?.city,
            this?.country,
            this?.position.mapToDomain()
        )
    return domainLocation
}
fun DataTags?.mapToDomain():DomainTags?{
    val domainTags: DomainTags =
        DomainTags(
            this?.title
        )
    return domainTags
}
fun DataUrls?.mapToDomain():DomainUrls?{
    val domainUrls: DomainUrls =
        DomainUrls(
            this?.raw,
            this?.full,
            this?.regular,
            this?.small,
            this?.thumb
        )
    return domainUrls
}
fun DataProfileImage?.mapToDomain():DomainProfileImage?{
    val domainProfileImage: DomainProfileImage =
        DomainProfileImage(
            this?.small,
            this?.medium,
            this?.large
        )
    return domainProfileImage
}
fun DataUser?.mapToDomain():DomainUser?{
    val domainUser: DomainUser =
        DomainUser(
            this?.id,
            this?.updatedAt,
            this?.username,
            this?.name,
            this?.portfolioUrl,
            this?.bio,
            this?.location,
            this?.totalLikes,
            this?.totalPhotos,
            this?.totalCollections,
            this?.profileImage.mapToDomain(),
            this?.links.mapToDomain(),
        )
    return domainUser
}
fun DataDetailedPhotoInfo?.mapToDomain():DomainDetailedPhotoInfo?{
    val tags = arrayListOf<DomainTags>()
    this?.tags?.forEach{tag->
        val temp = tag.mapToDomain()
        temp?.let { tags.add(it) }

    }
    val currentUserCollections = arrayListOf<DomainCurrentUserCollections>()
    this?.currentUserCollections?.forEach{currentUserCollection->
        val temp = currentUserCollection.mapToDomain()
        temp?.let { currentUserCollections.add(it) }

    }
    val domainDetailedPhotoInfo: DomainDetailedPhotoInfo =
        DomainDetailedPhotoInfo(
            this?.id,
            this?.createdAt,
            this?.updatedAt,
            this?.width,
            this?.height,
            this?.color,
            this?.blurHash,
            this?.downloads,
            this?.likes,
            this?.likedByUser,
            this?.publicDomain,
            this?.description,
            this?.exif.mapToDomain(),
            this?.location.mapToDomain(),
            tags,
            currentUserCollections,
            this?.urls.mapToDomain(),
            this?.links.mapToDomain(),
            this?.user.mapToDomain(),
        )
    return domainDetailedPhotoInfo
}



