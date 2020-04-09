package com.astronout.tmc.modules.admin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.admin.model.GetAdminListModel
import com.astronout.tmc.modules.employees.model.GetAllEmployeesModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.STATUS_AKTIF_CODE
import com.astronout.tmc.utils.Constants.STATUS_NON_AKTIF_CODE
import com.astronout.tmc.utils.logDebug

class AdminListViewModel(application: Application): AndroidViewModel(application) {

    private val _listAdmin = MutableLiveData<List<GetAdminListModel>>()
    val listAdmin: LiveData<List<GetAdminListModel>>
        get() = _listAdmin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setListAdmin(listAdmin: List<GetAdminListModel>) {
        _listAdmin.value = listAdmin
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setId(id: String) {
        _id.value = id
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun getListAdmin() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.getAdminList({
            setListAdmin(it.getAdminListModel)
        }, {
            logDebug("#getListAdmin : $it")
        }, {
            setLoading(false)
        })
    }

    fun nonAktifAdmin() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateStatusAdmin(STATUS_NON_AKTIF_CODE, id.value!!, {
           setStatus(it.status)
        }, {
            logDebug("#nonAktifAdmin : $it")
        }, {
            setLoading(false)
        })
    }

    fun aktifAdmin() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateStatusAdmin(STATUS_AKTIF_CODE, id.value!!, {
            setStatus(it.status)
        }, {
            logDebug("#aktifAdmin : $it")
        }, {
            setLoading(false)
        })
    }

}