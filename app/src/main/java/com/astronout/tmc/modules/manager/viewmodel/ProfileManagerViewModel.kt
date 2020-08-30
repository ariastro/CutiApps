package com.astronout.tmc.modules.manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.manager.model.GetProfileManagerModel
import com.astronout.tmc.modules.manager.model.GetProfileManagerResponseModel
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdResponseModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ProfileManagerViewModel(application: Application): AndroidViewModel(application) {

    private val _managerModel = MutableLiveData<GetProfileManagerModel>()
    val managerModel: LiveData<GetProfileManagerModel>
        get() = _managerModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getProfile() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.getManagerById(User.managerId, {
            if (it.status) {
                setManagerModel(it.getProfileManagerModel)
            }
        }, {
            logDebug("getProfile: $it")
        }, {
            setLoading(false)
        })
    }

    fun setManagerModel(managerModel: GetProfileManagerModel) {
        _managerModel.value = managerModel
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

}