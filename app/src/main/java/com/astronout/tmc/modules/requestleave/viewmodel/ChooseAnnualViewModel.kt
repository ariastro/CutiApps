package com.astronout.tmc.modules.requestleave.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.leavetype.annual.model.GetAnnualModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ChooseAnnualViewModel(application: Application): AndroidViewModel(application) {

    private val _listAnnual = MutableLiveData<List<GetAnnualModel>>()
    val listAnnual: LiveData<List<GetAnnualModel>>
        get() = _listAnnual

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setListAnnual(listAnnual: List<GetAnnualModel>) {
        _listAnnual.value = listAnnual
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
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

}