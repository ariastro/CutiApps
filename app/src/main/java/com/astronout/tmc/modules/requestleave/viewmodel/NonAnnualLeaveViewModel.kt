package com.astronout.tmc.modules.requestleave.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.ANNUAL_NO
import com.astronout.tmc.utils.Constants.NON_ANNUAL_YES
import com.astronout.tmc.utils.logDebug

class NonAnnualLeaveViewModel(application: Application): AndroidViewModel(application) {

    private val _rightsGranted = MutableLiveData<Int>()
    val rightsGranted: LiveData<Int>
        get() = _rightsGranted

    private val _rightsUsed = MutableLiveData<Int>()
    val rightsUsed: LiveData<Int>
        get() = _rightsUsed

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

    fun setRightsGranted(leaveRigthUsed: Int) {
        _rightsGranted.value = leaveRigthUsed
    }

    fun setRightsUsed(rightsUsed: Int) {
        _rightsUsed.value = rightsUsed
    }

    fun setJenisCuti(jenisCuti: String) {
        _jenisCuti.value = jenisCuti
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun postNewLeave() {
//        setLoading(true)
//        val remoteRepository = RemoteRepository(viewModelScope)
//        remoteRepository.postNewLeave(jenisCuti.value!!, startDate.value!!, endDate.value!!,
//            rightsUsed.value.toString(), ANNUAL_NO, NON_ANNUAL_YES, keterangan.value!!,
//            User.idEmployee, {
//                if (it.status) {
//                    setStatus(true)
//                } else {
//                    setStatus(false)
//                }
//            }, {
//                logDebug("postNewNonAnnualLeaveError: $it")
//            }, {
//                setLoading(false)
//            })
    }

    init {
        setRightsUsed(0)
    }

}