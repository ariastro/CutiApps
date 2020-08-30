package com.astronout.tmc.modules.leaves.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.leaves.model.GetAccLeavesModel
import com.astronout.tmc.modules.leaves.model.GetAllLeavesModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.USER_KASI
import com.astronout.tmc.utils.logDebug

class AccLeavesViewModel(application: Application): AndroidViewModel(application) {

    private val _listLeaves = MutableLiveData<List<GetAccLeavesModel>?>()
    val listLeaves: LiveData<List<GetAccLeavesModel>?>
        get() = _listLeaves

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setAccLeaves(listLeaves: List<GetAccLeavesModel>) {
        _listLeaves.value = listLeaves
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getAccLeaves() {
        setLoading(true)
        val kasiJabatan = if (User.userType == USER_KASI) User.kasiJabatan else ""
        val repository = RemoteRepository(viewModelScope)
        repository.postGetAccLeaves(kasiJabatan, {
            setStatus(it.status)
            setAccLeaves(it.getAccLeavesModel)
        }, {
            logDebug("ErrorGetAccLeaves: $it")
        }, {
            setLoading(false)
        })
    }

}