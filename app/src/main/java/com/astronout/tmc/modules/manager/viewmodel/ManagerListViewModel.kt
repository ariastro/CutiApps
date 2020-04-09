package com.astronout.tmc.modules.manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.admin.model.GetAdminListModel
import com.astronout.tmc.modules.employees.model.GetAllEmployeesModel
import com.astronout.tmc.modules.manager.model.GetManagerListModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.STATUS_AKTIF_CODE
import com.astronout.tmc.utils.Constants.STATUS_NON_AKTIF_CODE
import com.astronout.tmc.utils.logDebug

class ManagerListViewModel(application: Application): AndroidViewModel(application) {

    private val _listManager = MutableLiveData<List<GetManagerListModel>>()
    val listManager: LiveData<List<GetManagerListModel>>
        get() = _listManager

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setListManager(listManager: List<GetManagerListModel>) {
        _listManager.value = listManager
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

    fun getListManager() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.getManagerList({
            setListManager(it.getManagerListModel)
        }, {
            logDebug("#getListManager : $it")
        }, {
            setLoading(false)
        })
    }

    fun nonAktifManager() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateStatusManager(STATUS_NON_AKTIF_CODE, id.value!!, {
           setStatus(it.status)
        }, {
            logDebug("#nonAktifManager : $it")
        }, {
            setLoading(false)
        })
    }

    fun aktifManager() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateStatusManager(STATUS_AKTIF_CODE, id.value!!, {
            setStatus(it.status)
        }, {
            logDebug("#aktifManager : $it")
        }, {
            setLoading(false)
        })
    }

}