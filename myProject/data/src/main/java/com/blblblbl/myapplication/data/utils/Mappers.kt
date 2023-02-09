package com.blblblbl.myapplication.data.utils

import com.blblblbl.myapplication.data.data_classes.user_info.Links as DataMeLinks
import com.blblblbl.myapplication.domain.models.user_info.Links as DomainMeLinks
import com.blblblbl.myapplication.data.data_classes.user_info.UserInfo as DataMeInfo
import com.blblblbl.myapplication.domain.models.user_info.UserInfo as DomainMeInfo



import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfoLinks as DataPublicUserInfoLinks
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfoLinks as DomainPublicUserInfoLinks
import com.blblblbl.myapplication.data.data_classes.public_user_info.Badge as DataBadge
import com.blblblbl.myapplication.domain.models.public_user_info.Badge as DomainBadge
import com.blblblbl.myapplication.data.data_classes.public_user_info.ProfileImage as DataProfileImage
import com.blblblbl.myapplication.domain.models.public_user_info.ProfileImage as DomainProfileImage
import com.blblblbl.myapplication.data.data_classes.public_user_info.Social as DataSocial
import com.blblblbl.myapplication.domain.models.public_user_info.Social as DomainSocial
import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo as DataPublicUserInfo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo as DomainPublicUserInfo

import com.blblblbl.myapplication.data.data_classes.photo_detailed.Links as DataPhotoDetailedLinks
import com.blblblbl.myapplication.domain.models.photo_detailed.Links as DomainPhotoDetailedLinks
import com.blblblbl.myapplication.data.data_classes.photo_detailed.Exif as DataExif
import com.blblblbl.myapplication.domain.models.photo_detailed.Exif as DomainExif
import com.blblblbl.myapplication.data.data_classes.photo_detailed.CurrentUserCollections as DataCurrentUserCollections
import com.blblblbl.myapplication.domain.models.photo_detailed.CurrentUserCollections as DomainCurrentUserCollections
import com.blblblbl.myapplication.data.data_classes.photo_detailed.Location as DataLocation
import com.blblblbl.myapplication.domain.models.photo_detailed.Location as DomainLocation
import com.blblblbl.myapplication.data.data_classes.photo_detailed.Position as DataPosition
import com.blblblbl.myapplication.domain.models.photo_detailed.Position as DomainPosition
import com.blblblbl.myapplication.data.data_classes.photo_detailed.Tags as DataTags
import com.blblblbl.myapplication.domain.models.photo_detailed.Tags as DomainTags
import com.blblblbl.myapplication.data.data_classes.photo_detailed.Urls as DataUrls
import com.blblblbl.myapplication.domain.models.photo_detailed.Urls as DomainUrls
import com.blblblbl.myapplication.data.data_classes.photo_detailed.User as DataUser
import com.blblblbl.myapplication.domain.models.photo_detailed.User as DomainUser
import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo as DataDetailedPhotoInfo
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo as DomainDetailedPhotoInfo

fun DataMeLinks?.mapToDomain(): DomainMeLinks? {
    val domainMeLinks: DomainMeLinks =
        DomainMeLinks(
            this?.self,
            this?.html,
            this?.photos,
            this?.likes
        )
    return domainMeLinks
}

fun DataMeInfo?.mapToDomain(): DomainMeInfo? {
    val domainMeInfo: DomainMeInfo =
        DomainMeInfo(
            this?.id,
            this?.updatedAt,
            this?.username,
            this?.firstName,
            this?.lastName,
            this?.twitterUsername,
            this?.portfolioUrl,
            this?.bio,
            this?.location,
            this?.totalLikes,
            this?.totalPhotos,
            this?.totalCollections,
            this?.followedByUser,
            this?.downloads,
            this?.uploadsRemaining,
            this?.instagramUsername,
            this?.email,
            this?.links.mapToDomain()
        )
    return domainMeInfo
}

fun DataPublicUserInfoLinks?.mapToDomain(): DomainPublicUserInfoLinks? {
    val domainPublicUserInfoLinks: DomainPublicUserInfoLinks =
        DomainPublicUserInfoLinks(
            this?.self,
            this?.html,
            this?.photos,
            this?.likes,
            this?.portfolio
        )
    return domainPublicUserInfoLinks
}
fun DataBadge?.mapToDomain():DomainBadge?{
    val domainBadge: DomainBadge =
        DomainBadge(
            this?.title,
            this?.primary,
            this?.slug,
            this?.link
        )
    return domainBadge
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
fun DataSocial?.mapToDomain():DomainSocial?{
    val domainSocial: DomainSocial =
        DomainSocial(
            this?.instagramUsername,
            this?.portfolioUrl,
            this?.twitterUsername
        )
    return domainSocial
}
fun DataPublicUserInfo?.mapToDomain():DomainPublicUserInfo?{
    val domainPublicUserInfo: DomainPublicUserInfo =
        DomainPublicUserInfo(
            this?.id,
            this?.updatedAt,
            this?.username,
            this?.name,
            this?.firstName,
            this?.lastName,
            this?.instagramUsername,
            this?.twitterUsername,
            this?.portfolioUrl,
            this?.bio,
            this?.location,
            this?.totalLikes,
            this?.totalPhotos,
            this?.totalCollections,
            this?.followedByUser,
            this?.followersCount,
            this?.followingCount,
            this?.downloads,
            this?.social.mapToDomain(),
            this?.profileImage.mapToDomain(),
            this?.badge.mapToDomain(),
            this?.links.mapToDomain()
        )
    return domainPublicUserInfo
}

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