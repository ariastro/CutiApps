package com.astronout.tmc.modules.requestleave.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.ANNUAL_YES
import com.astronout.tmc.utils.Constants.NON_ANNUAL_NO
import com.astronout.tmc.utils.logDebug

class AnnualLeaveViewModel(application: Application): AndroidViewModel(application) {

    private val _sisaCuti = MutableLiveData<String>()
    val sisaCuti: LiveData<String>
        get() = _sisaCuti

    private val _leaveRightUsed = MutableLiveData<Int>()
    val leaveRightUsed: LiveData<Int>
        get() = _leaveRightUsed

    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String>
        get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String>
        get() = _endDate

    private val _jenisCuti = MutableLiveData<String>()
    val jenisCuti: LiveData<String>
        get() = _jenisCuti

    val _keterangan = MutableLiveData<String>()
    val keterangan: LiveData<String>
        get() = _keterangan

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun setStartDate(startDate: String) {
        _startDate.value = startDate
    }

    fun setEndDate(endDate: String) {
        _endDate.value = endDate
    }

    fun setLeaveRightUsed(leaveRigthUsed: Int) {
        _leaveRightUsed.value = leaveRigthUsed
    }

    fun setJenisCuti(jenisCuti: String) {
        _jenisCuti.value = jenisCuti
    }

    fun setSisaCuti(sisaCuti: String) {
        _sisaCuti.value = sisaCuti
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun postNewLeave() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postNewLeave(jenisCuti.value!!, startDate.value!!, endDate.value!!,
            leaveRightUsed.value.toString(), ANNUAL_YES, NON_ANNUAL_NO, keterangan.value!!,
            User.idEmployee, {
                if (it.status) {
                    setStatus(true)
                } else {
                    setStatus(false)
                }
            }, {
                logDebug("postNewAnnualLeaveError: $it")
            }, {
                setLoading(false)
            })
    }

    fun getSisaCuti() {
        setLoading(true)
        val remoteRemoteRepository = RemoteRepository(viewModelScope)
        remoteRemoteRepository.getEmployeeById(User.idEmployee, {
            setSisaCuti(it.getEmployeeByIdModel.annualLeaveRights)
        }, {
            logDebug("getSisaCuti: $it")
        }, {
            setLoading(false)
        })
    }

    init {
        setLeaveRightUsed(0)
    }

}