package com.blblblbl.collections.data.utils



import com.blblblbl.collections.data.model.collection.Links as DataCollectionsLinks
import com.blblblbl.collections.domain.model.collection.Links as DomainCollectionsLinks
import com.blblblbl.collections.data.model.collection.UserLinks as DataCollectionsUserLinks
import com.blblblbl.collections.domain.model.collection.UserLinks as DomainCollectionsUserLinks
import com.blblblbl.collections.data.model.collection.Urls as DataCollectionsUrls
import com.blblblbl.collections.domain.model.collection.Urls as DomainCollectionsUrls
import com.blblblbl.collections.data.model.collection.ProfileImage as DataCollectionsProfileImage
import com.blblblbl.collections.domain.model.collection.ProfileImage as DomainCollectionsProfileImage
import com.blblblbl.collections.data.model.collection.User as DataCollectionsUser
import com.blblblbl.collections.domain.model.collection.User as DomainCollectionsUser
import com.blblblbl.collections.data.model.collection.CoverPhoto as DataCollectionsCoverPhoto
import com.blblblbl.collections.domain.model.collection.CoverPhoto as DomainCollectionsCoverPhoto
import com.blblblbl.collections.data.model.collection.PhotoCollection as DataCollectionsPhotoCollection
import com.blblblbl.collections.domain.model.collection.PhotoCollection as DomainCollectionsPhotoCollection

import com.blblblbl.collections.data.model.photo.Links as DataPhotosLinks
import com.blblblbl.collections.domain.model.photo.Links as DomainPhotosLinks
import com.blblblbl.collections.data.model.photo.Urls as DataPhotosUrls
import com.blblblbl.collections.domain.model.photo.Urls as DomainPhotosUrls
import com.blblblbl.collections.data.model.photo.ProfileImage as DataPhotosProfileImage
import com.blblblbl.collections.domain.model.photo.ProfileImage as DomainPhotosProfileImage
import com.blblblbl.collections.data.model.photo.CurrentUserCollections as DataPhotosCurrentUserCollections
import com.blblblbl.collections.domain.model.photo.CurrentUserCollections as DomainPhotosCurrentUserCollections
import com.blblblbl.collections.data.model.photo.User as DataPhotosUser
import com.blblblbl.collections.domain.model.photo.User as DomainPhotosUser
import com.blblblbl.collections.data.model.photo.Photo as DataPhotosPhoto
import com.blblblbl.collections.domain.model.photo.Photo as DomainPhotosPhoto


fun DataCollectionsLinks?.mapToDomain():DomainCollectionsLinks?{
    val domainCollectionsLinks: DomainCollectionsLinks =
        DomainCollectionsLinks(
            this?.self,
            this?.html,
            this?.download,
            this?.downloadLocation
        )
    return domainCollectionsLinks
}
fun DataCollectionsUserLinks?.mapToDomain():DomainCollectionsUserLinks?{
    val domainCollectionsUserLinks: DomainCollectionsUserLinks =
        DomainCollectionsUserLinks(
            this?.self,
            this?.html,
            this?.photos,
            this?.related
        )
    return domainCollectionsUserLinks
}
fun DataCollectionsUrls?.mapToDomain():DomainCollectionsUrls?{
    val domainCollectionsUrls: DomainCollectionsUrls =
        DomainCollectionsUrls(
            this?.raw,
            this?.full,
            this?.regular,
            this?.small,
            this?.thumb
        )
    return domainCollectionsUrls
}
fun DataCollectionsProfileImage?.mapToDomain():DomainCollectionsProfileImage?{
    val domainCollectionsProfileImage: DomainCollectionsProfileImage =
        DomainCollectionsProfileImage(
            this?.small,
            this?.medium,
            this?.large
        )
    return domainCollectionsProfileImage
}
fun DataCollectionsUser?.mapToDomain():DomainCollectionsUser?{
    val domainCollectionsUser: DomainCollectionsUser =
        DomainCollectionsUser(
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
            this?.links.mapToDomain()
        )
    return domainCollectionsUser
}
fun DataCollectionsCoverPhoto?.mapToDomain():DomainCollectionsCoverPhoto?{
    val domainCollectionsCoverPhoto: DomainCollectionsCoverPhoto =
        DomainCollectionsCoverPhoto(
            this?.id,
            this?.width,
            this?.height,
            this?.color,
            this?.blurHash,
            this?.likes,
            this?.likedByUser,
            this?.description,
            this?.user.mapToDomain(),
            this?.urls.mapToDomain(),
            this?.links.mapToDomain()
        )
    return domainCollectionsCoverPhoto
}
fun DataCollectionsPhotoCollection?.mapToDomain():DomainCollectionsPhotoCollection?{
    val domainCollectionsPhotoCollection: DomainCollectionsPhotoCollection =
        DomainCollectionsPhotoCollection(
            this?.id,
            this?.title,
            this?.description,
            this?.publishedAt,
            this?.lastCollectedAt,
            this?.updatedAt,
            this?.totalPhotos,
            this?.private,
            this?.shareKey,
            this?.coverPhoto.mapToDomain(),
            this?.user.mapToDomain(),
            this?.links.mapToDomain(),
        )
    return domainCollectionsPhotoCollection
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
