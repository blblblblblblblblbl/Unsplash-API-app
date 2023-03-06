package com.blblblbl.profile.data.utils

import com.blblblbl.profile.data.model.user_info.Links as DataMeLinks
import com.blblblbl.profile.domain.model.user_info.Links as DomainMeLinks
import com.blblblbl.profile.data.model.user_info.UserInfo as DataMeInfo
import com.blblblbl.profile.domain.model.user_info.UserInfo as DomainMeInfo



import com.blblblbl.profile.data.model.public_user_info.PublicUserInfoLinks as DataPublicUserInfoLinks
import com.blblblbl.profile.domain.model.public_user_info.PublicUserInfoLinks as DomainPublicUserInfoLinks
import com.blblblbl.profile.data.model.public_user_info.Badge as DataBadge
import com.blblblbl.profile.domain.model.public_user_info.Badge as DomainBadge
import com.blblblbl.profile.data.model.public_user_info.ProfileImage as DataProfileImage
import com.blblblbl.profile.domain.model.public_user_info.ProfileImage as DomainProfileImage
import com.blblblbl.profile.data.model.public_user_info.Social as DataSocial
import com.blblblbl.profile.domain.model.public_user_info.Social as DomainSocial
import com.blblblbl.profile.data.model.public_user_info.PublicUserInfo as DataPublicUserInfo
import com.blblblbl.profile.domain.model.public_user_info.PublicUserInfo as DomainPublicUserInfo



import com.blblblbl.profile.data.model.photo.Links as DataPhotosLinks
import com.blblblbl.profile.domain.model.photo.Links as DomainPhotosLinks
import com.blblblbl.profile.data.model.photo.Urls as DataPhotosUrls
import com.blblblbl.profile.domain.model.photo.Urls as DomainPhotosUrls
import com.blblblbl.profile.data.model.photo.ProfileImage as DataPhotosProfileImage
import com.blblblbl.profile.domain.model.photo.ProfileImage as DomainPhotosProfileImage
import com.blblblbl.profile.data.model.photo.CurrentUserCollections as DataPhotosCurrentUserCollections
import com.blblblbl.profile.domain.model.photo.CurrentUserCollections as DomainPhotosCurrentUserCollections
import com.blblblbl.profile.data.model.photo.User as DataPhotosUser
import com.blblblbl.profile.domain.model.photo.User as DomainPhotosUser
import com.blblblbl.profile.data.model.photo.Photo as DataPhotosPhoto
import com.blblblbl.profile.domain.model.photo.Photo as DomainPhotosPhoto

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


fun DataPhotosLinks?.mapToDomain():DomainPhotosLinks?{
    val domainPhotosLinks: DomainPhotosLinks =
        DomainPhotosLinks(
            this?.self,
            this?.html,
            this?.download,
            this?.downloadLocation
        )
    return domainPhotosLinks
}
fun DataPhotosUrls?.mapToDomain():DomainPhotosUrls?{
    val domainPhotosUrls: DomainPhotosUrls =
        DomainPhotosUrls(
            this?.raw,
            this?.full,
            this?.regular,
            this?.small,
            this?.thumb
        )
    return domainPhotosUrls
}
fun DataPhotosProfileImage?.mapToDomain():DomainPhotosProfileImage?{
    val domainPhotosProfileImage: DomainPhotosProfileImage =
        DomainPhotosProfileImage(
            this?.small,
            this?.medium,
            this?.large
        )
    return domainPhotosProfileImage
}
fun DataPhotosCurrentUserCollections?.mapToDomain():DomainPhotosCurrentUserCollections?{
    val domainPhotosCurrentUserCollections: DomainPhotosCurrentUserCollections =
        DomainPhotosCurrentUserCollections(
            this?.id,
            this?.title,
            this?.publishedAt,
            this?.lastCollectedAt,
            this?.updatedAt,
            this?.coverPhoto,
            this?.user
        )
    return domainPhotosCurrentUserCollections
}
fun DataPhotosUser?.mapToDomain():DomainPhotosUser?{
    val domainPhotosUser: DomainPhotosUser =
        DomainPhotosUser(
            this?.id,
            this?.username,
            this?.name,
            this?.portfolioUrl,
            this?.bio,
            this?.location,
            this?.totalLikes,
            this?.totalPhotos,
            this?.totalCollections,
            this?.instagramUsername,
            this?.twitterUsername,
            this?.profileImage.mapToDomain(),
            this?.links.mapToDomain(),
        )
    return domainPhotosUser
}
fun DataPhotosPhoto?.mapToDomain():DomainPhotosPhoto?{
    val collections = arrayListOf<DomainPhotosCurrentUserCollections>()
    this?.currentUserCollections?.forEach{collection->
        val temp = collection.mapToDomain()
        temp?.let { collections.add(it) }

    }
    val domainPhotosPhoto: DomainPhotosPhoto =
        DomainPhotosPhoto(
            this?.id,
            this?.createdAt,
            this?.updatedAt,
            this?.width,
            this?.height,
            this?.color,
            this?.blurHash,
            this?.likes,
            this?.likedByUser,
            this?.description,
            this?.user.mapToDomain(),
            collections,
            this?.urls.mapToDomain(),
            this?.links.mapToDomain()
        )
    return domainPhotosPhoto
}
