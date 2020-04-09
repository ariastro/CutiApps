package com.astronout.tmc.modules.leavetype.nonannual.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.leavetype.nonannual.model.GetNonAnnualModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class GetNonAnnualViewModel(application: Application): AndroidViewModel(application) {

    private val _listNonAnnual = MutableLiveData<List<GetNonAnnualModel>>()
    val listNonAnnual: LiveData<List<GetNonAnnualModel>>
        get() = _listNonAnnual

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    private val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    fun setListNonAnnual(listAnnual: List<GetNonAnnualModel>) {
        _listNonAnnual.value = listAnnual
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun setId(id: String) {
        _id.value = id
    }

    fun getListNonAnnual() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.getNonAnnualList("", {
            setListNonAnnual(it.getNonAnnualModel)
        }, {
            logDebug("#getListAnnual : $it")
        }, {
            setLoading(false)
        })
    }

    fun deleteNonAnnual() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postDeleteNonAnnual(id.value!!, {
            setStatus(it.status)
        }, {
            logDebug(it)
        }, {
            setLoading(false)
        })
    }

}