package com.astronout.tmc.modules.leaves.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.leaves.model.GetAllLeavesModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class AllLeavesViewModel(application: Application): AndroidViewModel(application) {

    private val _listLeaves = MutableLiveData<List<GetAllLeavesModel>>()
    val listLeaves: LiveData<List<GetAllLeavesModel>>
        get() = _listLeaves

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setAllLeaves(listLeaves: List<GetAllLeavesModel>) {
        _listLeaves.value = listLeaves
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getAllLeaves() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.getAllLeaves({
            setAllLeaves(it.getAllLeavesModel)
        }, {
            logDebug("ErrorGetAllLeaves: $it")
        }, {
            setLoading(false)
        })
    }

}