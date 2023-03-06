package com.blblblbl.search.data.utils



import com.blblblbl.search.data.model.photo.Links as DataPhotosLinks
import com.blblblbl.search.domain.model.photo.Links as DomainPhotosLinks
import com.blblblbl.search.data.model.photo.Urls as DataPhotosUrls
import com.blblblbl.search.domain.model.photo.Urls as DomainPhotosUrls
import com.blblblbl.search.data.model.photo.ProfileImage as DataPhotosProfileImage
import com.blblblbl.search.domain.model.photo.ProfileImage as DomainPhotosProfileImage
import com.blblblbl.search.data.model.photo.CurrentUserCollections as DataPhotosCurrentUserCollections
import com.blblblbl.search.domain.model.photo.CurrentUserCollections as DomainPhotosCurrentUserCollections
import com.blblblbl.search.data.model.photo.User as DataPhotosUser
import com.blblblbl.search.domain.model.photo.User as DomainPhotosUser
import com.blblblbl.search.data.model.photo.Photo as DataPhotosPhoto
import com.blblblbl.search.domain.model.photo.Photo as DomainPhotosPhoto



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
