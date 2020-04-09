package com.astronout.tmc.modules.leaves.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.SETUJUI
import com.astronout.tmc.utils.Constants.STATUS_DISETUJUI_CODE
import com.astronout.tmc.utils.Constants.STATUS_DITOLAK_CODE
import com.astronout.tmc.utils.defaultDateTimeFormat
import com.astronout.tmc.utils.jodaTimeDefaultFormat
import com.astronout.tmc.utils.logDebug
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DetailAllLeavesViewModel(application: Application): AndroidViewModel(application) {

    private val _keputusan = MutableLiveData<String>()
    val keputusan: LiveData<String>
        get() = _keputusan

    val _adminRemark = MutableLiveData<String>()
    val adminRemark: LiveData<String>
        get() = _adminRemark

    val _adminRemarkDate = MutableLiveData<String>()
    val adminRemarkDate: LiveData<String>
        get() = _adminRemarkDate

    val _leaveGranted = MutableLiveData<String>()
    val leaveGranted: LiveData<String>
        get() = _leaveGranted

    val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    val _employeeId = MutableLiveData<String>()
    val employeeId: LiveData<String>
        get() = _employeeId

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _isLoadingRights = MutableLiveData<Boolean>()
    val isLoadingRights: LiveData<Boolean>
        get() = _isLoadingRights

    val _isAnnual = MutableLiveData<Boolean>()
    val isAnnual: LiveData<Boolean>
        get() = _isAnnual

    val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    val _statusRights = MutableLiveData<Boolean>()
    val statusRights: LiveData<Boolean>
        get() = _statusRights

    fun setId(id: String) {
        _id.value = id
    }

    fun setEmployeeId(employeeId: String) {
        _employeeId.value = employeeId
    }

    fun setLeaveGranted(leaveGranted: String) {
        _leaveGranted.value = leaveGranted
    }

    fun setKeputusan(keputusan: String) {
        _keputusan.value = keputusan
    }

    fun setAdminRemark(adminRemark: String) {
        _adminRemark.value = adminRemark
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setLoadingRights(isLoading: Boolean) {
        _isLoadingRights.value = isLoading
    }

    fun setIsAnnual(isAnnual: Boolean) {
        _isAnnual.value = isAnnual
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun setStatusRights(status: Boolean) {
        _statusRights.value = status
    }

    init {
        val dateNowJodaTime = DateTime.now()
        val dateNow = DateTime.parse(dateNowJodaTime.toString(), DateTimeFormat.forPattern(jodaTimeDefaultFormat))
        _adminRemarkDate.value = dateNow.toString(defaultDateTimeFormat)
    }

    fun postDecision() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postDecision(adminRemark.value!!, adminRemarkDate.value!!,
            keputusan.value!!, id.value!!, {
                if (it.status) {
                    setStatus(true)
                } else {
                    setStatus(false)
                }
            }, {
                logDebug("#postDecisionError : $it")
            }, {
                setLoading(false)
            })
    }

    fun updateAnnualLeaveRights() {
        setLoadingRights(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateLeaveRights(employeeId.value!!, leaveGranted.value!!, {
                if (it.status) {
                    setStatusRights(true)
                } else {
                    setStatusRights(false)
                }
            }, {
                logDebug("#updateAnnualLeaveRightsError : $it")
            }, {
                setLoadingRights(false)
            })
    }

}