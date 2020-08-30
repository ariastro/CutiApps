package com.astronout.tmc.base.baseview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.astronout.tmc.R
import com.astronout.tmc.modules.auth.empolyees.view.LoginActivity
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.widget.AppProgressDialog

abstract class BaseActivity : AppCompatActivity() {

    lateinit var progress : AppProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProgress()
    }

    protected fun setupProgress() {
        progress = AppProgressDialog(this)
        progress.setCancelable(false)
        progress.setCanceledOnTouchOutside(false)
    }

    protected fun setLogoutDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.exit_dialogue))
        alert.setPositiveButton(android.R.string.yes) { _, _ ->
            clearData()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        alert.setNegativeButton(android.R.string.no) { dialog, _ ->
            dialog.cancel()
        }
        alert.show()
    }

    private fun clearData() {
        User.empId = ""
        User.firstName = ""
        User.lastName = ""
        User.email = ""
        User.gender = ""
        User.dob = ""
        User.position = ""
        User.department = ""
        User.address = ""
        User.city = ""
        User.country = ""
        User.phoneNumber = ""
        User.annualLeaveRights = ""
        User.token = ""
        User.userAvatar = ""
        User.idEmployee = ""

        User.adminId = ""
        User.adminStatus = ""
        User.managerId = ""
        User.managerStatus = ""
        User.userType = ""

        User.kasiId = ""
        User.kasiStatus = ""
        User.kasiJabatan = ""
    }

}