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

class DetailAccLeavesViewModel(application: Application): AndroidViewModel(application) {

    private val _keputusanKasi = MutableLiveData<String>()
    val keputusanKasi: LiveData<String>
        get() = _keputusanKasi

    private val _keputusanManager = MutableLiveData<String>()
    val keputusanManager: LiveData<String>
        get() = _keputusanManager

    private val _keputusanKasubag = MutableLiveData<String>()
    val keputusanKasubag: LiveData<String>
        get() = _keputusanKasubag

    val _kasiRemark = MutableLiveData<String>()
    val kasiRemark: LiveData<String>
        get() = _kasiRemark

    val _kasubagRemark = MutableLiveData<String>()
    val kasubagRemark: LiveData<String>
        get() = _kasubagRemark

    val _managerRemark = MutableLiveData<String>()
    val managerRemark: LiveData<String>
        get() = _managerRemark

    val _leaveGranted = MutableLiveData<String>()
    val leaveGranted: LiveData<String>
        get() = _leaveGranted

    val _nomorCuti = MutableLiveData<String>()
    val nomorCuti: LiveData<String>
        get() = _nomorCuti

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

    val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    val _sisaCuti = MutableLiveData<String>()
    val sisaCuti: LiveData<String>
        get() = _sisaCuti

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

    fun setNomorCuti(nomorCuti: String) {
        _nomorCuti.value = nomorCuti
    }

    fun setKeputusanKasi(keputusan: String) {
        _keputusanKasi.value = keputusan
    }

    fun setKeputusanKasubag(keputusan: String) {
        _keputusanKasubag.value = keputusan
    }

    fun setKeputusanManager(keputusan: String) {
        _keputusanManager.value = keputusan
    }

    fun setKasiRemark(kasiRemark: String) {
        _kasiRemark.value = kasiRemark
    }

    fun setSisaCuti(sisaCuti: String) {
        _sisaCuti.value = sisaCuti
    }

    fun setManagerRemark(managerRemark: String) {
        _managerRemark.value = managerRemark
    }

    fun setKasubagRemark(kasubagRemark: String) {
        _kasubagRemark.value = kasubagRemark
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setLoadingRights(isLoading: Boolean) {
        _isLoadingRights.value = isLoading
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun setStatusRights(status: Boolean) {
        _statusRights.value = status
    }

//    fun postDecision() {
//        setLoading(true)
//        val remoteRepository = RemoteRepository(viewModelScope)
//        remoteRepository.postDecision(adminRemark.value!!, adminRemarkDate.value!!,
//            keputusan.value!!, id.value!!, {
//                if (it.status) {
//                    setStatus(true)
//                } else {
//                    setStatus(false)
//                }
//            }, {
//                logDebug("#postDecisionError : $it")
//            }, {
//                setLoading(false)
//            })
//    }

    fun postAccKasi() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postAccKasi(kasiRemark.value!!, keputusanKasi.value!!, id.value!!, {
                if (it.status) {
                    setStatus(true)
                } else {
                    setStatus(false)
                }
            }, {
                logDebug("#postAccKasiError : $it")
            }, {
                setLoading(false)
            })
    }

    fun postAccManager() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postAccManager(managerRemark.value!!, keputusanManager.value!!, id.value!!, {
            if (it.status) {
                setStatus(true)
            } else {
                setStatus(false)
            }
        }, {
            logDebug("#postAccManagerError : $it")
        }, {
            setLoading(false)
        })
    }

    fun postAccKasubag() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postAccKasubag(kasubagRemark.value!!, keputusanKasubag.value!!, nomorCuti.value!!, id.value!!, {
            if (it.status) {
                setStatus(true)
            } else {
                setStatus(false)
            }
        }, {
            logDebug("#postAccKasubagError : $it")
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

    fun getSisaCuti() {
        setLoading(true)
        val remoteRemoteRepository = RemoteRepository(viewModelScope)
        remoteRemoteRepository.getEmployeeById(employeeId.value!!, {
            setSisaCuti(it.getEmployeeByIdModel.annualLeaveRights)
        }, {
            logDebug("getSisaCuti: $it")
        }, {
            setLoading(false)
        })
    }

}