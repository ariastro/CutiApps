package com.astronout.tmc.modules.profile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.department.model.GetDepartmentModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ChooseDepartmentViewModel(application: Application): AndroidViewModel(application) {

    private val _listDepartment = MutableLiveData<List<GetDepartmentModel>>()
    val listDepartment: LiveData<List<GetDepartmentModel>>
        get() = _listDepartment

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setListDepartment(listDepartment: List<GetDepartmentModel>) {
        _listDepartment.value = listDepartment
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
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

}