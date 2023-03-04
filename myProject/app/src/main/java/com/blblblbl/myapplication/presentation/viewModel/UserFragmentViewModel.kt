package com.blblblbl.myapplication.presentation.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import com.blblblbl.myapplication.domain.usecase.*
import com.blblblbl.myapplication.presentation.view.activities.MainActivity
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
    private val likeStateUseCase: LikeStateUseCase,
    private val getMeInfoUseCase: GetMeInfoUseCase,
    private val clearStorageUseCase: ClearStorageUseCase,
    private val getLikedPhotoPagingUseCase: GetLikedPhotoPagingUseCase
):ViewModel() {
    private var _pagedPhotos = MutableStateFlow<Flow<PagingData<Photo>>?>(null)
    var pagedPhotos = _pagedPhotos.asStateFlow()
    private val _privateUserInfo = MutableStateFlow<UserInfo?>(null)
    val privateUserInfo = _privateUserInfo.asStateFlow()
    private val _publicUserInfo = MutableStateFlow<PublicUserInfo?>(null)
    val publicUserInfo = _publicUserInfo.asStateFlow()
    fun logout(){
        viewModelScope.launch{

            clearStorageUseCase.execute()
            val intent = Intent(context, MainActivity::class.java)
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
                _pagedPhotos.value = getLikedPhotoPagingUseCase.execute(it, PAGE_SIZE).cachedIn(viewModelScope)
                _publicUserInfo.value = publicUser
                _privateUserInfo.value = privateUser
            }
        }
    }
    companion object{
        const val PAGE_SIZE = 10
    }
}