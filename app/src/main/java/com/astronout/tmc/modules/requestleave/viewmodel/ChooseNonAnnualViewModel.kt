package com.astronout.tmc.modules.requestleave.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.leavetype.annual.model.GetAnnualModel
import com.astronout.tmc.modules.leavetype.nonannual.model.GetNonAnnualModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ChooseNonAnnualViewModel(application: Application): AndroidViewModel(application) {

    private val _listNonAnnual = MutableLiveData<List<GetNonAnnualModel>>()
    val listNonAnnual: LiveData<List<GetNonAnnualModel>>
        get() = _listNonAnnual

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setListNonAnnual(listAnnual: List<GetNonAnnualModel>) {
        _listNonAnnual.value = listAnnual
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
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

}