package com.astronout.tmc.modules.department.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.department.model.GetDepartmentModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class DepartmentViewModel(application: Application): AndroidViewModel(application) {

    private val _listDepartment = MutableLiveData<List<GetDepartmentModel>>()
    val listDepartment: LiveData<List<GetDepartmentModel>>
        get() = _listDepartment

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLoadingDelete = MutableLiveData<Boolean>()
    val isLoadingDelete: LiveData<Boolean>
        get() = _isLoadingDelete

    val _departmentId = MutableLiveData<String>()
    val departmentId: LiveData<String>
        get() = _departmentId

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setListDepartment(listDepartment: List<GetDepartmentModel>) {
        _listDepartment.value = listDepartment
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setLoadingDelete(isLoadingDelete: Boolean) {
        _isLoadingDelete.value = isLoadingDelete
    }

    fun setDepartmentId(departmentId: String) {
        _departmentId.value = departmentId
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun getListDepartment() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.getDepartmentList("", {
            setListDepartment(it.getDepartmentModel)
        }, {
            logDebug("#getListDepartment : $it")
        }, {
            setLoading(false)
        })
    }

    fun deleteDepartment() {
        setLoadingDelete(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postDeleteDepartment(departmentId.value!!, {
            if (it.status) {
                setStatus(true)
            } else {
                setStatus(false)
            }
        }, {
            logDebug("#DeleteDepartmentError : $it")
        }, {
            setLoadingDelete(false)
        })
    }

}