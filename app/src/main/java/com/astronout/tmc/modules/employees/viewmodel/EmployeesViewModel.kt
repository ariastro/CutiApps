package com.astronout.tmc.modules.employees.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.employees.model.GetAllEmployeesModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class EmployeesViewModel(application: Application): AndroidViewModel(application) {

    private val _listEmployees = MutableLiveData<List<GetAllEmployeesModel>>()
    val listEmployees: LiveData<List<GetAllEmployeesModel>>
        get() = _listEmployees

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setListEmployees(listEmployees: List<GetAllEmployeesModel>) {
        _listEmployees.value = listEmployees
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

    fun getListEmployees() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.getAllEmployees({
            setListEmployees(it.getAllEmployeesModel)
        }, {
            logDebug("#getListEmployees : $it")
        }, {
            setLoading(false)
        })
    }

    fun deleteEmployee() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postDeleteEmployee(id.value!!, {
           setStatus(it.status)
        }, {
            logDebug("#deleteDepartment : $it")
        }, {
            setLoading(false)
        })
    }

}