package com.astronout.tmc.modules.leaves.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.leaves.model.GetLeaveByIdModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class LeaveViewModel(application: Application) : AndroidViewModel(application) {

    private val _listLeaveById = MutableLiveData<List<GetLeaveByIdModel>>()
    val listLeaveById: LiveData<List<GetLeaveByIdModel>>
        get() = _listLeaveById

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setListLeaveById(listLeaveById: List<GetLeaveByIdModel>) {
        _listLeaveById.value = listLeaveById
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun getListLeaveById() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.getLeaveById(User.idEmployee, {
            setStatus(it.status)
            setListLeaveById(it.getLeaveByIdModel)
        }, {
            logDebug("ErrorGetLeaveById: $it")
        }, {
            setLoading(false)
        })
    }

}