package com.astronout.tmc.modules.admin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.admin.model.GetProfileAdminModel
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.manager.model.GetProfileManagerModel
import com.astronout.tmc.modules.manager.model.GetProfileManagerResponseModel
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdResponseModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ProfileAdminViewModel(application: Application): AndroidViewModel(application) {

    private val _adminModel = MutableLiveData<GetProfileAdminModel>()
    val adminModel: LiveData<GetProfileAdminModel>
        get() = _adminModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getProfile() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.getAdminById(User.adminId, {
            if (it.status) {
                setAdminModel(it.getProfileAdminModel)
            }
        }, {
            logDebug("getProfile: $it")
        }, {
            setLoading(false)
        })
    }

    fun setAdminModel(managerModel: GetProfileAdminModel) {
        _adminModel.value = managerModel
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

}