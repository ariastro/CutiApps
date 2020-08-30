package com.astronout.tmc.modules.kasi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.admin.model.GetAdminListModel
import com.astronout.tmc.modules.employees.model.GetAllEmployeesModel
import com.astronout.tmc.modules.kasi.model.GetKasiListModel
import com.astronout.tmc.modules.manager.model.GetManagerListModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.STATUS_AKTIF_CODE
import com.astronout.tmc.utils.Constants.STATUS_NON_AKTIF_CODE
import com.astronout.tmc.utils.logDebug

class KasiListViewModel(application: Application): AndroidViewModel(application) {

    private val _listKasi = MutableLiveData<List<GetKasiListModel>>()
    val listKasi: LiveData<List<GetKasiListModel>>
        get() = _listKasi

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setListKasi(listKasi: List<GetKasiListModel>) {
        _listKasi.value = listKasi
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

    fun getListKasi() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.getKasiList({
            setListKasi(it.getKasiListModel)
        }, {
            logDebug("#getListKasi : $it")
        }, {
            setLoading(false)
        })
    }

    fun nonAktifKasi() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateStatusKasi(STATUS_NON_AKTIF_CODE, id.value!!, {
           setStatus(it.status)
        }, {
            logDebug("#nonAktifKasi : $it")
        }, {
            setLoading(false)
        })
    }

    fun aktifKasi() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateStatusKasi(STATUS_AKTIF_CODE, id.value!!, {
            setStatus(it.status)
        }, {
            logDebug("#aktifKasi : $it")
        }, {
            setLoading(false)
        })
    }

}