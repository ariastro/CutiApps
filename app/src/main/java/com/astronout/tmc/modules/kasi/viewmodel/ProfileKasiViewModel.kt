package com.astronout.tmc.modules.kasi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.kasi.model.GetProfileKasiModel
import com.astronout.tmc.modules.manager.model.GetProfileManagerModel
import com.astronout.tmc.modules.manager.model.GetProfileManagerResponseModel
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdResponseModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ProfileKasiViewModel(application: Application): AndroidViewModel(application) {

    private val _kasi = MutableLiveData<GetProfileKasiModel>()
    val kasi: LiveData<GetProfileKasiModel>
        get() = _kasi

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getProfile() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.getKasiById(User.kasiId, {
            if (it.status) {
                setKasi(it.getProfileKasiModel)
            }
        }, {
            logDebug("getProfile: $it")
        }, {
            setLoading(false)
        })
    }

    fun setKasi(kasi: GetProfileKasiModel) {
        _kasi.value = kasi
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

}