package com.astronout.tmc.network.repositories

import com.astronout.tmc.base.basemodel.BaseResponseModel
import com.astronout.tmc.base.basemodel.UpdateAvatarResponseModel
import com.astronout.tmc.modules.admin.model.GetAdminListResponseModel
import com.astronout.tmc.modules.manager.model.GetManagerListResponseModel
import com.astronout.tmc.modules.admin.model.GetProfileAdminResponseModel
import com.astronout.tmc.modules.employees.model.UpdateEmployeeResponseModel
import com.astronout.tmc.modules.auth.admin.model.LoginAdminResponseModel
import com.astronout.tmc.modules.auth.admin.model.LoginManagerResponseModel
import com.astronout.tmc.modules.auth.empolyees.model.LoginResponseModel
import com.astronout.tmc.modules.auth.kasi.model.LoginKasiResponseModel
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.department.model.DeleteDepartmentResponseModel
import com.astronout.tmc.modules.department.model.GetDepartmentResponseModel
import com.astronout.tmc.modules.department.model.PostNewDepartmentResponseModel
import com.astronout.tmc.modules.department.model.UpdateDepartmentResponseModel
import com.astronout.tmc.modules.employees.model.DeleteEmployeeResponseModel
import com.astronout.tmc.modules.employees.model.GetAllEmployeesResponseModel
import com.astronout.tmc.modules.employees.model.PostNewEmployeeResponseModel
import com.astronout.tmc.modules.kasi.model.GetKasiListResponseModel
import com.astronout.tmc.modules.kasi.model.GetProfileKasiResponseModel
import com.astronout.tmc.modules.leaves.model.*
import com.astronout.tmc.modules.leavetype.annual.model.DeleteAnnualTypeResponseModel
import com.astronout.tmc.modules.leavetype.annual.model.GetAnnualResponseModel
import com.astronout.tmc.modules.leavetype.annual.model.PostNewAnnualTypeResponseModel
import com.astronout.tmc.modules.leavetype.annual.model.UpdateAnnualTypeResponseModel
import com.astronout.tmc.modules.leavetype.nonannual.model.DeleteNonAnnualResponseModel
import com.astronout.tmc.modules.leavetype.nonannual.model.GetNonAnnualResponseModel
import com.astronout.tmc.modules.leavetype.nonannual.model.PostNewNonAnnualResponseModel
import com.astronout.tmc.modules.leavetype.nonannual.model.UpdateNonAnnualResponseModel
import com.astronout.tmc.modules.manager.model.GetProfileManagerResponseModel
import com.astronout.tmc.modules.profile.model.ChangePasswordResponseModel
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdResponseModel
import com.astronout.tmc.modules.requestleave.model.RequestLeaveResponseModel
import com.astronout.tmc.network.helper.Network
import com.astronout.tmc.network.helper.ServiceFactory
import com.astronout.tmc.utils.Constants.STATUS_AKTIF_CODE
import com.astronout.tmc.utils.defaultDateTimeFormat
import com.astronout.tmc.utils.logError
import kotlinx.coroutines.CoroutineScope
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.joda.time.DateTime
import java.io.File

class RemoteRepository(coroutineScope: CoroutineScope) {

    private val restApi = ServiceFactory.create()
    private val network = Network(coroutineScope)

    fun postLogin(EmailId: String, Password: String, onSuccess: (LoginResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postLogin(EmailId, Password)
        },{
            onSuccess(it!!)
        }, {
            logError("postLogin # Error Message = $it")
            onError(it)
        },{
            onFinally(true)
        })
    }

    fun postAdminLogin(username: String, password: String, onSuccess: (LoginAdminResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postLoginAdmin(username, password)
        }, {
            onSuccess(it!!)
        }, {
            logError("postAdminLogin # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postManagerLogin(username: String, password: String, onSuccess: (LoginManagerResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postLoginManager(username, password)
        }, {
            onSuccess(it!!)
        }, {
            logError("postManagerLogin # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postKasiLogin(username: String, password: String, onSuccess: (LoginKasiResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postLoginKasi(username, password)
        }, {
            onSuccess(it!!)
        }, {
            logError("postKasiLogin # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getLeaveById(id: String, onSuccess: (GetLeaveByIdResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getLeaveById(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("getLeaveById # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getAllLeaves(onSuccess: (GetAllLeavesResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getAllLeaves()
        }, {
            onSuccess(it!!)
        }, {
            logError("getAllLeaves # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postGetAccLeaves(kasiJabatan: String, onSuccess: (GetAccLeavesResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postGetAccLeaves(User.userType, kasiJabatan)
        }, {
            onSuccess(it!!)
        }, {
            logError("postGetAccLeaves # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getEmployeeById(id: String, onSuccess: (GetEmployeeByIdResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getEmployeeById(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("getEmployeeById # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getNonAnnualList(id: String, onSuccess: (GetNonAnnualResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getNonAnnualList(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("getNonAnnualList # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getAnnualList(id: String, onSuccess: (GetAnnualResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getAnnualList(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("getAnnualList # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getDepartmentList(id: String, onSuccess: (GetDepartmentResponseModel) -> Unit, onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getDepartmentList(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("getDepartmentList # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postNewLeave(leaveType: String, fromDate: String, toDate:String, rightsGranted: String,
                     empDepartment: String, desctription: String, id: String,
                     onSuccess: (RequestLeaveResponseModel) -> Unit, onError: (String) -> Unit,
                     onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postNewLeave(leaveType, fromDate, toDate, rightsGranted, empDepartment,
                desctription, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postNewLeave # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postAccKasi(kasiRemark: String, kasiAcc:String, id: String,
                    onSuccess: (BaseResponseModel) -> Unit, onError: (String) -> Unit,
                    onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postAccKasi(kasiRemark, kasiAcc, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postAccKasi # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postAccKasubag(kasubagRemark: String, kasubagAcc:String, nomorCuti: String, id: String,
                       onSuccess: (BaseResponseModel) -> Unit, onError: (String) -> Unit,
                       onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postAccKasubag(kasubagRemark, kasubagAcc, nomorCuti, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postAccKasubag # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postAccManager(managerRemark: String, managerAcc:String, id: String,
                       onSuccess: (BaseResponseModel) -> Unit, onError: (String) -> Unit,
                       onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postAccManager(managerRemark, managerAcc, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postAccManager # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateLeaveRights(id: String, leaveGranted: String, onSuccess: (UpdateLeaveRightsResponseModel) -> Unit,
                              onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateLeaveRights(id, leaveGranted)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateLeaveRights # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getAllEmployees(onSuccess: (GetAllEmployeesResponseModel) -> Unit,
                        onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getAllEmployees()
        }, {
            onSuccess(it!!)
        }, {
            logError("getAllEmployees # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postNewEmployee(empId: String, FirstName: String, LastName:String, EmailId: String,
                        password: String, Gender: String, Dob: String, Position: String, Department: String,
                        Address: String, City: String, Country: String, Phonenumber: String,
                        AnnualLeaveRights: String, onSuccess: (PostNewEmployeeResponseModel) -> Unit,
                        onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postNewEmployee(empId, FirstName, LastName, EmailId, password, Gender, Dob, Position,
                Department, Address, City, Country, Phonenumber, "1", AnnualLeaveRights)
        }, {
            onSuccess(it!!)
        }, {
            logError("postNewEmployee # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateEmployee(empId: String, FirstName: String, LastName:String, EmailId: String,
                           Gender: String, Dob: String, Position: String, Department: String,
                           Address: String, City: String, Country: String, Phonenumber: String,
                           AnnualLeaveRights: String, id: String, onSuccess: (UpdateEmployeeResponseModel) -> Unit,
                           onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateEmployee(empId, FirstName, LastName, EmailId, Gender, Dob, Position,
                Department, Address, City, Country, Phonenumber, AnnualLeaveRights, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateEmployee # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postDeleteEmployee(id: String, onSuccess: (DeleteEmployeeResponseModel) -> Unit,
                           onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postDeleteEmployee(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postDeleteEmployee # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postNewDepartment(departmentName: String, departmentShortName: String, departmentCode: String,
                          onSuccess: (PostNewDepartmentResponseModel) -> Unit,
                          onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postNewDepartment(departmentName, departmentShortName, departmentCode)
        }, {
            onSuccess(it!!)
        }, {
            logError("postNewDepartment # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateDepartment(departmentName: String, departmentShortName: String, departmentCode: String,
                             id: String, onSuccess: (UpdateDepartmentResponseModel) -> Unit,
                             onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateDepartment(departmentName, departmentShortName, departmentCode, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateDepartment # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postDeleteDepartment(id: String, onSuccess: (DeleteDepartmentResponseModel) -> Unit,
                             onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postDeleteDepartment(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postDeleteDepartment # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postNewAnnual(no: String, leaveType: String, description: String,
                      onSuccess: (PostNewAnnualTypeResponseModel) -> Unit,
                      onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postNewAnnual(no, leaveType, description)
        }, {
            onSuccess(it!!)
        }, {
            logError("postNewAnnual # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateAnnual(no: String, leaveType: String, description: String, id: String,
                      onSuccess: (UpdateAnnualTypeResponseModel) -> Unit,
                      onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateAnnual(no, leaveType, description, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateAnnual # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postDeleteAnnual(id: String, onSuccess: (DeleteAnnualTypeResponseModel) -> Unit,
                         onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postDeleteAnnual(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postDeleteAnnual # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postNewNonAnnual(no: String, leaveType: String, rightsGranted: String, description: String,
                         onSuccess: (PostNewNonAnnualResponseModel) -> Unit,
                         onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postNewNonAnnual(no, leaveType, rightsGranted, "None", description)
        }, {
            onSuccess(it!!)
        }, {
            logError("postNewNonAnnual # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateNonAnnual(no: String, leaveType: String, rightsGranted: String, description: String,
                            id: String, onSuccess: (UpdateNonAnnualResponseModel) -> Unit,
                            onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateNonAnnual(no, leaveType, rightsGranted, "None", description, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateNonAnnual # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postDeleteNonAnnual(id: String, onSuccess: (DeleteNonAnnualResponseModel) -> Unit,
                            onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postDeleteNonAnnual(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postDeleteNonAnnual # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postChangePassword(currentPassword: String, newPassword: String, id: String,
                           onSuccess: (ChangePasswordResponseModel) -> Unit,
                           onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postChangePassword(currentPassword, newPassword, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postChangePassword # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateAvatar(avatar: File, onSuccess: (UpdateAvatarResponseModel) -> Unit,
                         onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), avatar)
        val image = MultipartBody.Part.createFormData("avatar", avatar.name, requestBody)
        val userId = RequestBody.create(MediaType.parse("text/plain"), User.idEmployee)
        network.request({
            restApi.postUpdateAvatar(image, userId)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateAvatar # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getManagerById(managerId: String, onSuccess: (GetProfileManagerResponseModel) -> Unit,
                       onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getManagerById(managerId)
        }, {
            onSuccess(it!!)
        }, {
            logError("getManagerById # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postChangePasswordManager(currentPassword: String, newPassword: String, id: String,
                                  onSuccess: (BaseResponseModel) -> Unit,
                                  onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postChangePasswordManager(currentPassword, newPassword, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postChangePasswordManager # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateManager(username: String, name:String, gender: String, birthday: String,
                          address: String, city: String, country: String, phonenumber: String,
                          id: String, onSuccess: (BaseResponseModel) -> Unit,
                          onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateManager(username, name, gender, birthday, address, city, country,
                phonenumber, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateManager # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateManagerAvatar(avatar: File, onSuccess: (BaseResponseModel) -> Unit,
                                onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), avatar)
        val image = MultipartBody.Part.createFormData("manager_avatar", avatar.name, requestBody)
        val userId = RequestBody.create(MediaType.parse("text/plain"), User.managerId)
        network.request({
            restApi.postUpdateManagerAvatar(image, userId)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateManagerAvatar # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getKasiById(id: String, onSuccess: (GetProfileKasiResponseModel) -> Unit,
                    onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getKasiById(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("getKasiById # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postChangePasswordKasi(currentPassword: String, newPassword: String, id: String,
                               onSuccess: (BaseResponseModel) -> Unit,
                               onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postChangePasswordKasi(currentPassword, newPassword, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postChangePasswordKasi # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateKasi(username: String, name:String, gender: String, birthday: String, address: String,
                       city: String, country: String, phonenumber: String, jabatan: String, id: String,
                       onSuccess: (BaseResponseModel) -> Unit, onError: (String) -> Unit,
                       onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateKasi(username, name, gender, birthday, address, city, country,
                phonenumber, jabatan, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateKasi # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateKasiAvatar(avatar: File, onSuccess: (BaseResponseModel) -> Unit,
                             onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), avatar)
        val image = MultipartBody.Part.createFormData("kasi_avatar", avatar.name, requestBody)
        val userId = RequestBody.create(MediaType.parse("text/plain"), User.kasiId)
        network.request({
            restApi.postUpdateKasiAvatar(image, userId)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateKasiAvatar # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getAdminById(id: String, onSuccess: (GetProfileAdminResponseModel) -> Unit,
                       onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getAdminById(id)
        }, {
            onSuccess(it!!)
        }, {
            logError("getManagerById # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateAdmin(username: String, name:String, gender: String, birthday: String,
                        address: String, city: String, country: String, phonenumber: String,
                        id: String, onSuccess: (BaseResponseModel) -> Unit,
                        onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            val dateNow = DateTime()
            restApi.postUpdateAdmin(username, name, gender, birthday, address, city, country,
                phonenumber, dateNow.toString(defaultDateTimeFormat), id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateAdmin # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateAdminAvatar(avatar: File, onSuccess: (BaseResponseModel) -> Unit,
                              onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), avatar)
        val image = MultipartBody.Part.createFormData("admin_avatar", avatar.name, requestBody)
        val userId = RequestBody.create(MediaType.parse("text/plain"), User.adminId)
        network.request({
            restApi.postUpdateAdminAvatar(image, userId)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateAdminAvatar # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postChangePasswordAdmin(currentPassword: String, newPassword: String, id: String,
                                onSuccess: (BaseResponseModel) -> Unit,
                                onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postChangePasswordAdmin(currentPassword, newPassword, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postChangePasswordAdmin # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getAdminList(onSuccess: (GetAdminListResponseModel) -> Unit,
                     onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getAdminList()
        }, {
            onSuccess(it!!)
        }, {
            logError("getAdminList # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getManagerList(onSuccess: (GetManagerListResponseModel) -> Unit,
                       onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getManagerList()
        }, {
            onSuccess(it!!)
        }, {
            logError("getManagerList # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getKasiList(onSuccess: (GetKasiListResponseModel) -> Unit,
                    onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getKasiList()
        }, {
            onSuccess(it!!)
        }, {
            logError("getKasiList # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun getKasi(onSuccess: (GetKasiListResponseModel) -> Unit,
                    onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.getKasiList()
        }, {
            onSuccess(it!!)
        }, {
            logError("getKasiList # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postNewAdmin(username: String, password: String, name:String, gender: String, birthday: String,
                     address: String, city: String, country: String, phonenumber: String,
                     onSuccess: (BaseResponseModel) -> Unit, onError: (String) -> Unit,
                     onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postNewAdmin(username, password, name, gender, birthday, address, city, country,
                phonenumber, STATUS_AKTIF_CODE)
        }, {
            onSuccess(it!!)
        }, {
            logError("postNewAdmin # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postNewManager(username: String, password: String, name:String, gender: String, birthday: String,
                       address: String, city: String, country: String, phonenumber: String,
                       onSuccess: (BaseResponseModel) -> Unit, onError: (String) -> Unit,
                       onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postNewManager(username, password, name, gender, birthday, address, city, country,
                phonenumber, STATUS_AKTIF_CODE)
        }, {
            onSuccess(it!!)
        }, {
            logError("postNewManager # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postNewKasi(username: String, password: String, name:String, gender: String, birthday: String,
                    address: String, city: String, country: String, phonenumber: String, jabatan: String,
                    jenis: String, onSuccess: (BaseResponseModel) -> Unit, onError: (String) -> Unit,
                    onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postNewKasi(username, password, name, gender, birthday, address, city, country,
                phonenumber, STATUS_AKTIF_CODE, jabatan, jenis)
        }, {
            onSuccess(it!!)
        }, {
            logError("postNewKasi # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateStatusAdmin(status: String, id: String, onSuccess: (BaseResponseModel) -> Unit,
                              onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateStatusAdmin(status, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateStatusAdmin # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateStatusManager(status: String, id: String, onSuccess: (BaseResponseModel) -> Unit,
                                onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateStatusManager(status, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateStatusManager # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

    fun postUpdateStatusKasi(status: String, id: String, onSuccess: (BaseResponseModel) -> Unit,
                             onError: (String) -> Unit, onFinally: (Boolean) -> Unit) {
        network.request({
            restApi.postUpdateStatusKasi(status, id)
        }, {
            onSuccess(it!!)
        }, {
            logError("postUpdateStatusKasi # Error Message = $it")
            onError(it)
        }, {
            onFinally(true)
        })
    }

}