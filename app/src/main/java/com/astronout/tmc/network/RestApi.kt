package com.astronout.tmc.network

import com.astronout.tmc.base.basemodel.BaseResponseModel
import com.astronout.tmc.modules.auth.admin.model.LoginAdminResponseModel
import com.astronout.tmc.modules.auth.empolyees.model.LoginResponseModel
import com.astronout.tmc.modules.employees.model.*
import com.astronout.tmc.modules.auth.admin.model.LoginManagerResponseModel
import com.astronout.tmc.modules.department.model.DeleteDepartmentResponseModel
import com.astronout.tmc.modules.department.model.GetDepartmentResponseModel
import com.astronout.tmc.modules.department.model.PostNewDepartmentResponseModel
import com.astronout.tmc.modules.department.model.UpdateDepartmentResponseModel
import com.astronout.tmc.modules.leavetype.annual.model.DeleteAnnualTypeResponseModel
import com.astronout.tmc.modules.leavetype.annual.model.GetAnnualResponseModel
import com.astronout.tmc.modules.leavetype.annual.model.PostNewAnnualTypeResponseModel
import com.astronout.tmc.modules.leavetype.annual.model.UpdateAnnualTypeResponseModel
import com.astronout.tmc.modules.leavetype.nonannual.model.DeleteNonAnnualResponseModel
import com.astronout.tmc.modules.leavetype.nonannual.model.GetNonAnnualResponseModel
import com.astronout.tmc.modules.leavetype.nonannual.model.PostNewNonAnnualResponseModel
import com.astronout.tmc.modules.leavetype.nonannual.model.UpdateNonAnnualResponseModel
import com.astronout.tmc.modules.profile.model.ChangePasswordResponseModel
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdResponseModel
import com.astronout.tmc.base.basemodel.UpdateAvatarResponseModel
import com.astronout.tmc.modules.admin.model.GetAdminListResponseModel
import com.astronout.tmc.modules.manager.model.GetManagerListResponseModel
import com.astronout.tmc.modules.admin.model.GetProfileAdminResponseModel
import com.astronout.tmc.modules.auth.kasi.model.LoginKasiResponseModel
import com.astronout.tmc.modules.kasi.model.GetKasiListResponseModel
import com.astronout.tmc.modules.kasi.model.GetProfileKasiResponseModel
import com.astronout.tmc.modules.leaves.model.*
import com.astronout.tmc.modules.manager.model.GetProfileManagerResponseModel
import com.astronout.tmc.modules.requestleave.model.RequestLeaveResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RestApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(@Field("EmailId") emailId: String,
                          @Field("Password") password: String) : Response<LoginResponseModel>

    @FormUrlEncoded
    @POST("admin")
    suspend fun postLoginAdmin(@Field("UserName") emailId: String,
                               @Field("Password") password: String) : Response<LoginAdminResponseModel>

    @FormUrlEncoded
    @POST("manager")
    suspend fun postLoginManager(@Field("manager_username") emailId: String,
                                 @Field("manager_password") password: String) : Response<LoginManagerResponseModel>

    @FormUrlEncoded
    @POST("kasi")
    suspend fun postLoginKasi(@Field("kasi_username") username: String,
                              @Field("kasi_password") password: String) : Response<LoginKasiResponseModel>

    @GET("employees")
    suspend fun getAllEmployees(): Response<GetAllEmployeesResponseModel>

    @GET("employees")
    suspend fun getEmployeeById(@Query("id") id: String): Response<GetEmployeeByIdResponseModel>

    @FormUrlEncoded
    @POST("employees")
    suspend fun postNewEmployee(@Field("EmpId") empId: String,
                                @Field("FirstName") firstName: String,
                                @Field("LastName") lastName: String,
                                @Field("EmailId") emailId: String,
                                @Field("Password") password: String,
                                @Field("Gender") gender: String,
                                @Field("Dob") dob: String,
                                @Field("Position") position: String,
                                @Field("Department") department: String,
                                @Field("Address") address: String,
                                @Field("City") city: String,
                                @Field("Country") country: String,
                                @Field("Phonenumber") phoneNumber: String,
                                @Field("Status") status: String,
                                @Field("AnnualLeaveRights") annualLeaveRights: String): Response<PostNewEmployeeResponseModel>

    @FormUrlEncoded
    @POST("employees/update")
    suspend fun postUpdateEmployee(@Field("EmpId") empId: String,
                                   @Field("FirstName") firstName: String,
                                   @Field("LastName") lastName: String,
                                   @Field("EmailId") emailId: String,
                                   @Field("Gender") gender: String,
                                   @Field("Dob") dob: String,
                                   @Field("Position") position: String,
                                   @Field("Department") department: String,
                                   @Field("Address") address: String,
                                   @Field("City") city: String,
                                   @Field("Country") country: String,
                                   @Field("Phonenumber") phoneNumber: String,
                                   @Field("AnnualLeaveRights") annualLeaveRights: String,
                                   @Field("id") id: String) : Response<UpdateEmployeeResponseModel>

    @FormUrlEncoded
    @POST("employees/delete")
    suspend fun postDeleteEmployee(@Field("id") id: String): Response<DeleteEmployeeResponseModel>

    @GET("leaves")
    suspend fun getAllLeaves(): Response<GetAllLeavesResponseModel>

    @FormUrlEncoded
    @POST("leaves/needacc")
    suspend fun postGetAccLeaves(@Field("userType") userType: String,
                                 @Field("kasi_jabatan") kasiJabatan: String): Response<GetAccLeavesResponseModel>

    @GET("leaves")
    suspend fun getLeaveById(@Query("id") id: String): Response<GetLeaveByIdResponseModel>

    @GET("annual")
    suspend fun getNonAnnualList(@Query("id") id: String): Response<GetNonAnnualResponseModel>

    @GET("nonannual")
    suspend fun getAnnualList(@Query("id") id: String): Response<GetAnnualResponseModel>

    @GET("departments")
    suspend fun getDepartmentList(@Query("id") id: String): Response<GetDepartmentResponseModel>

    @FormUrlEncoded
    @POST("leaves")
    suspend fun postNewLeave(@Field("LeaveType") leaveType: String,
                             @Field("FromDate") fromDate: String,
                             @Field("ToDate") toDate: String,
                             @Field("RightsGranted") rightsGranted: String,
                             @Field("emp_department") empDepartment: String,
                             @Field("Description") description: String,
                             @Field("empid") empId: String) : Response<RequestLeaveResponseModel>

    @FormUrlEncoded
    @POST("leaves/acckasi")
    suspend fun postAccKasi(@Field("kasi_remark") kasiRemark: String,
                            @Field("kasi_acc") kasiAcc: String,
                            @Field("id") id: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("leaves/accksb")
    suspend fun postAccKasubag(@Field("kasubag_remark") kasubagRemark: String,
                               @Field("kasubag_acc") kasubagAcc: String,
                               @Field("nomor_cuti") nomorCuti: String,
                               @Field("id") id: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("leaves/accmanager")
    suspend fun postAccManager(@Field("manager_remark") managerRemark: String,
                               @Field("manager_acc") managerAcc: String,
                               @Field("id") id: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("leaves/updateLeaveRights")
    suspend fun postUpdateLeaveRights(@Field("id") id: String,
                                      @Field("leaveGranted") leaveGranted: String) : Response<UpdateLeaveRightsResponseModel>

    @FormUrlEncoded
    @POST("departments")
    suspend fun postNewDepartment(@Field("DepartmentName") departmentName: String,
                                  @Field("DepartmentShortName") departmentShortName: String,
                                  @Field("DepartmentCode") departmentCode:String) : Response<PostNewDepartmentResponseModel>

    @FormUrlEncoded
    @POST("departments/update")
    suspend fun postUpdateDepartment(@Field("DepartmentName") departmentName: String,
                                     @Field("DepartmentShortName") departmentShortName: String,
                                     @Field("DepartmentCode") departmentCode:String,
                                     @Field("id") id: String) : Response<UpdateDepartmentResponseModel>

    @FormUrlEncoded
    @POST("departments/delete")
    suspend fun postDeleteDepartment(@Field("id") id: String) : Response<DeleteDepartmentResponseModel>

    @FormUrlEncoded
    @POST("nonannual")
    suspend fun postNewAnnual(@Field("No") no: String,
                              @Field("LeaveType") leaveType: String,
                              @Field("Description") description:String) : Response<PostNewAnnualTypeResponseModel>

    @FormUrlEncoded
    @POST("nonannual/update")
    suspend fun postUpdateAnnual(@Field("No") no: String,
                                 @Field("LeaveType") leaveType: String,
                                 @Field("Description") description:String,
                                 @Field("id") id: String) : Response<UpdateAnnualTypeResponseModel>

    @FormUrlEncoded
    @POST("nonannual/delete")
    suspend fun postDeleteAnnual(@Field("id") id: String) : Response<DeleteAnnualTypeResponseModel>

    @FormUrlEncoded
    @POST("annual")
    suspend fun postNewNonAnnual(@Field("No") no: String,
                                 @Field("LeaveType") leaveType: String,
                                 @Field("RightsGranted") rightsGranted: String,
                                 @Field("AttachmentDoc") attachmentDoc: String,
                                 @Field("Description") description:String) : Response<PostNewNonAnnualResponseModel>

    @FormUrlEncoded
    @POST("annual/update")
    suspend fun postUpdateNonAnnual(@Field("No") no: String,
                                    @Field("LeaveType") leaveType: String,
                                    @Field("RightsGranted") rightsGranted: String,
                                    @Field("AttachmentDoc") attachmentDoc: String,
                                    @Field("Description") description:String,
                                    @Field("id") id: String) : Response<UpdateNonAnnualResponseModel>

    @FormUrlEncoded
    @POST("annual/delete")
    suspend fun postDeleteNonAnnual(@Field("id") id: String) : Response<DeleteNonAnnualResponseModel>

    @FormUrlEncoded
    @POST("employees/changepassword")
    suspend fun postChangePassword(@Field("currentPassword") currentPassword: String,
                                   @Field("Password") Password: String,
                                   @Field("id") id: String) : Response<ChangePasswordResponseModel>

    @Multipart
    @POST("employees/updateavatar")
    suspend fun postUpdateAvatar(@Part avatarImage: MultipartBody.Part,
                                 @Part("id") userId: RequestBody) : Response<UpdateAvatarResponseModel>

    @GET("manager")
    suspend fun getManagerById(@Query("manager_id") managerId: String): Response<GetProfileManagerResponseModel>

    @FormUrlEncoded
    @POST("manager/changepassword")
    suspend fun postChangePasswordManager(@Field("currentPassword") currentPassword: String,
                                          @Field("Password") Password: String,
                                          @Field("id") id: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("manager/update")
    suspend fun postUpdateManager(@Field("manager_username") username: String,
                                  @Field("manager_name") name: String,
                                  @Field("manager_gender") gender: String,
                                  @Field("manager_birthday") birthday: String,
                                  @Field("manager_address") address: String,
                                  @Field("manager_city") city: String,
                                  @Field("manager_country") country: String,
                                  @Field("manager_phone") phoneNumber: String,
                                  @Field("manager_id") id: String) : Response<BaseResponseModel>

    @Multipart
    @POST("manager/updateavatar")
    suspend fun postUpdateManagerAvatar(@Part avatarImage: MultipartBody.Part,
                                        @Part("manager_id") userId: RequestBody) : Response<BaseResponseModel>

    @GET("admin")
    suspend fun getAdminById(@Query("id") id: String): Response<GetProfileAdminResponseModel>

    @GET("kasi")
    suspend fun getKasiById(@Query("kasi_id") id: String): Response<GetProfileKasiResponseModel>

    @FormUrlEncoded
    @POST("kasi/changepassword")
    suspend fun postChangePasswordKasi(@Field("currentPassword") currentPassword: String,
                                       @Field("Password") Password: String,
                                       @Field("id") id: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("kasi/update")
    suspend fun postUpdateKasi(@Field("kasi_username") username: String,
                               @Field("kasi_name") name: String,
                               @Field("kasi_gender") gender: String,
                               @Field("kasi_birthday") birthday: String,
                               @Field("kasi_address") address: String,
                               @Field("kasi_city") city: String,
                               @Field("kasi_country") country: String,
                               @Field("kasi_phone") phoneNumber: String,
                               @Field("kasi_jabatan") jabatan: String,
                               @Field("kasi_id") id: String) : Response<BaseResponseModel>

    @Multipart
    @POST("kasi/updateavatar")
    suspend fun postUpdateKasiAvatar(@Part avatarImage: MultipartBody.Part,
                                     @Part("kasi_id") userId: RequestBody) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("admin/update")
    suspend fun postUpdateAdmin(@Field("UserName") username: String,
                                @Field("admin_name") name: String,
                                @Field("admin_gender") gender: String,
                                @Field("admin_birthday") birthday: String,
                                @Field("admin_address") address: String,
                                @Field("admin_city") city: String,
                                @Field("admin_country") country: String,
                                @Field("admin_phone") phoneNumber: String,
                                @Field("updationDate") updationDate: String,
                                @Field("id") id: String) : Response<BaseResponseModel>

    @Multipart
    @POST("admin/updateavatar")
    suspend fun postUpdateAdminAvatar(@Part avatarImage: MultipartBody.Part,
                                      @Part("id") id: RequestBody) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("admin/changepassword")
    suspend fun postChangePasswordAdmin(@Field("currentPassword") currentPassword: String,
                                        @Field("Password") Password: String,
                                        @Field("id") id: String) : Response<BaseResponseModel>

    @GET("admin")
    suspend fun getAdminList(): Response<GetAdminListResponseModel>

    @GET("manager")
    suspend fun getManagerList(): Response<GetManagerListResponseModel>

    @GET("kasi")
    suspend fun getKasiList(): Response<GetKasiListResponseModel>

    @FormUrlEncoded
    @POST("admin/add")
    suspend fun postNewAdmin(@Field("UserName") username: String,
                             @Field("Password") password: String,
                             @Field("admin_name") name: String,
                             @Field("admin_gender") gender: String,
                             @Field("admin_birthday") birthday: String,
                             @Field("admin_address") address: String,
                             @Field("admin_city") city: String,
                             @Field("admin_country") country: String,
                             @Field("admin_phone") phoneNumber: String,
                             @Field("admin_status") status: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("manager/add")
    suspend fun postNewManager(@Field("manager_username") username: String,
                               @Field("manager_password") password: String,
                               @Field("manager_name") name: String,
                               @Field("manager_gender") gender: String,
                               @Field("manager_birthday") birthday: String,
                               @Field("manager_address") address: String,
                               @Field("manager_city") city: String,
                               @Field("manager_country") country: String,
                               @Field("manager_phone") phoneNumber: String,
                               @Field("manager_status") status: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("kasi/add")
    suspend fun postNewKasi(@Field("kasi_username") username: String,
                            @Field("kasi_password") password: String,
                            @Field("kasi_name") name: String,
                            @Field("kasi_gender") gender: String,
                            @Field("kasi_birthday") birthday: String,
                            @Field("kasi_address") address: String,
                            @Field("kasi_city") city: String,
                            @Field("kasi_country") country: String,
                            @Field("kasi_phone") phoneNumber: String,
                            @Field("kasi_status") status: String,
                            @Field("kasi_jabatan") jabatan: String,
                            @Field("kasi_jenis") jenis: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("admin/updatestatus")
    suspend fun postUpdateStatusAdmin(@Field("admin_status") status: String,
                                      @Field("id") id: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("manager/updatestatus")
    suspend fun postUpdateStatusManager(@Field("manager_status") status: String,
                                        @Field("manager_id") id: String) : Response<BaseResponseModel>

    @FormUrlEncoded
    @POST("kasi/updatestatus")
    suspend fun postUpdateStatusKasi(@Field("kasi_status") status: String,
                                     @Field("kasi_id") id: String) : Response<BaseResponseModel>

}