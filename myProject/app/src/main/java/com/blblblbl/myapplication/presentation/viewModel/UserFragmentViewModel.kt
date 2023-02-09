package com.blblblbl.myapplication.presentation.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.repository.paging_sources.LikedPhotosPagingSource
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import com.blblblbl.myapplication.domain.usecase.ClearStorageUseCase
import com.blblblbl.myapplication.domain.usecase.GetMeInfoUseCase
import com.blblblbl.myapplication.domain.usecase.GetUserInfoUseCase
import com.blblblbl.myapplication.domain.usecase.LikeStateUseCase
import com.blblblbl.myapplication.presentation.view.activities.AuthActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFragmentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val likedPhotosPagingSource: LikedPhotosPagingSource,
    private val persistentStorage: PersistentStorage,
    private val likeStateUseCase: LikeStateUseCase,
    private val getMeInfoUseCase: GetMeInfoUseCase,
    private val clearStorageUseCase: ClearStorageUseCase
):ViewModel() {
    lateinit var pagedPhotos: Flow<PagingData<Photo>>
    private val _privateUserInfo = MutableStateFlow<UserInfo?>(null)
    val privateUserInfo = _privateUserInfo.asStateFlow()
    private val _publicUserInfo = MutableStateFlow<com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo?>(null)
    val publicUserInfo = _publicUserInfo.asStateFlow()
    fun logout(){
        viewModelScope.launch{
            persistentStorage.clear()
            clearStorageUseCase.execute()
            val intent = Intent(context, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent);
        }
    }
    fun changeLike(id: String, bool:Boolean){
        viewModelScope.launch {
            if (bool){
                likeStateUseCase.like(id)
            }
            else{
                likeStateUseCase.unlike(id)
            }
        }
    }
    fun getUserInfo(){
        viewModelScope.launch {
            val privateUser = getMeInfoUseCase.execute()
            privateUser?.username?.let {
                val publicUser = getUserInfoUseCase.execute(it)
                likedPhotosPagingSource.userNameinit(it)
                pagedPhotos = Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = { likedPhotosPagingSource }
                ).flow.cachedIn(viewModelScope)
                _publicUserInfo.value = publicUser
                _privateUserInfo.value = privateUser
            }
        }
    }
}