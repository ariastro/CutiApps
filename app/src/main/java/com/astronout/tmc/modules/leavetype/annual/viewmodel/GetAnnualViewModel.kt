package com.astronout.tmc.modules.leavetype.annual.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.leavetype.annual.model.GetAnnualModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class GetAnnualViewModel(application: Application): AndroidViewModel(application) {

    private val _listAnnual = MutableLiveData<List<GetAnnualModel>>()
    val listAnnual: LiveData<List<GetAnnualModel>>
        get() = _listAnnual

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _annualId = MutableLiveData<String>()
    val annualId: LiveData<String>
        get() = _annualId

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setListAnnual(listAnnual: List<GetAnnualModel>) {
        _listAnnual.value = listAnnual
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setAnnualId(annualId: String) {
        _annualId.value = annualId
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun getListAnnual() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.getAnnualList("", {
            setListAnnual(it.getAnnualModel)
        }, {
            logDebug("#getListAnnual : $it")
        }, {
            setLoading(false)
        })
    }

    fun deleteAnnual() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postDeleteAnnual(annualId.value!!,{
            setStatus(it.status)
        }, {
            logDebug("deleteAnnual # Error : $it")
        }, {
            setLoading(false)
        })
    }

}