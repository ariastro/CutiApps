package com.astronout.tmc.modules.leaves.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityDetailAllLeavesBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.leaves.model.GetAllLeavesModel
import com.astronout.tmc.modules.leaves.viewmodel.DetailAllLeavesViewModel
import com.astronout.tmc.utils.*
import com.astronout.tmc.utils.Constants.ANNUAL
import com.astronout.tmc.utils.Constants.ANNUAL_NO
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.Constants.NON_ANNUAL
import com.astronout.tmc.utils.Constants.SETUJUI
import com.astronout.tmc.utils.Constants.STATUS_DISETUJUI
import com.astronout.tmc.utils.Constants.STATUS_DISETUJUI_CODE
import com.astronout.tmc.utils.Constants.STATUS_DITOLAK
import com.astronout.tmc.utils.Constants.STATUS_DITOLAK_CODE
import com.astronout.tmc.utils.Constants.STATUS_MENUNGGU
import com.astronout.tmc.utils.Constants.STATUS_MENUNGGU_CODE
import com.astronout.tmc.utils.Constants.TOLAK
import com.astronout.tmc.utils.Constants.USER_MANAGER
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.decision_dialog_layout.*
import kotlinx.android.synthetic.main.decision_dialog_layout.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DetailAllLeavesActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailAllLeavesBinding
    private lateinit var viewModel: DetailAllLeavesViewModel

    private var isAnnual = false

    companion object {
        val EXTRA_ALL_LEAVES = "EXTRA_ALL_LEAVES"
        val REQUEST_DETAIL_LEAVES = 5821
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_all_leaves)
        viewModel = ViewModelProvider(this).get(DetailAllLeavesViewModel::class.java)
        binding.detailAllLeaves = viewModel

        setSupportActionBar(binding.toolbar)

        if (intent.hasExtra(EXTRA_ALL_LEAVES)) {
            val extraData = intent.getParcelableExtra<GetAllLeavesModel>(EXTRA_ALL_LEAVES)

            when {
                extraData.status == STATUS_MENUNGGU_CODE -> {
                    binding.statusCuti.text = STATUS_MENUNGGU
                    binding.statusCuti.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_rounded_orange)
                }
                extraData.status == STATUS_DISETUJUI_CODE -> {
                    binding.statusCuti.text = STATUS_DISETUJUI
                    binding.statusCuti.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_rounded_blue)
                }
                else -> {
                    binding.statusCuti.text = STATUS_DITOLAK
                    binding.statusCuti.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_rounded_red)
                }
            }

            if (extraData.annual == ANNUAL_NO) {
                viewModel.setIsAnnual(false)
                isAnnual = false
                binding.isTahunan.text = NON_ANNUAL
            } else {
                viewModel.setIsAnnual(true)
                isAnnual = true
                binding.isTahunan.text = ANNUAL
            }

            if (extraData.adminRemark.isNullOrEmpty()) {
                binding.adminRemark.text = "-"
            } else {
                binding.adminRemark.text = extraData.adminRemark
            }

            binding.jumlahHariCuti.text = extraData.rightsGranted + " hari"
            val date = DateTime.parse(
                extraData.postingDate, DateTimeFormat.forPattern(
                    defaultDateTimeFormat
                )
            )

            binding.namaKaryawan.text = extraData.firstName + " " + extraData.lastName
            binding.tanggalPengajuan.text = date.toString(dateTimeFormat)
            binding.tanggalCuti.text = "${extraData.fromDate} - ${extraData.toDate}"
            binding.jenisCuti.text = extraData.leaveType
            binding.keterangan.text = extraData.description

            if (User.userType == USER_MANAGER) {
                if (extraData.status == STATUS_MENUNGGU_CODE) {
                    binding.btnAmbilKeputusan.visibility = visible()
                } else {
                    binding.btnAmbilKeputusan.visibility = gone()
                }
            }

            viewModel.setId(extraData.id)
            viewModel.setEmployeeId(extraData.empid)
            viewModel.setLeaveGranted(extraData.rightsGranted)

        }

        viewModel.status.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(EXTRA_MESSAGE, getString(R.string.make_decision_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.make_decision_failed))
            }
        })

        viewModel.statusRights.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(EXTRA_MESSAGE, getString(R.string.make_decision_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.make_decision_failed))
            }
        })

        setupProgressBar()

        binding.btnAmbilKeputusan.setOnClickListener {
            showDialog()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.decision_dialog_layout, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle(resources.getString(R.string.ambil_keputusan))
        val mAlertDialog = mBuilder.show()
        val spinner: AppCompatSpinner = mDialogView.findViewById(R.id.spinnerKeputusan)
        val adminRemark: TextInputEditText = mDialogView.findViewById(R.id.adminRemark)
        val adminRemarkLayout: TextInputLayout = mDialogView.findViewById(R.id.admin_remark_layout)

        mDialogView.btn_dialog_simpan.setOnClickListener {
            when {
                spinner.selectedItem.toString() == SETUJUI -> viewModel.setKeputusan(STATUS_DISETUJUI_CODE)
                spinner.selectedItem.toString() == TOLAK -> viewModel.setKeputusan(STATUS_DITOLAK_CODE)
                else -> {
                    viewModel.setKeputusan(STATUS_MENUNGGU_CODE)
                }
            }
            viewModel.setAdminRemark(adminRemark.text.toString())
            viewModel.adminRemark.observe(this, Observer { adminRemark ->
                viewModel.keputusan.observe(this, Observer { keputusan ->
                    if (keputusan == STATUS_MENUNGGU_CODE) {
                        showToast(getString(R.string.empty_decision))
                    } else {
                        if (adminRemark.isEmpty()) {
                            adminRemarkLayout.error = getString(R.string.empty_admin_remark)
                        } else {
                            adminRemarkLayout.error = ""
                            viewModel.postDecision()
                            if (isAnnual && keputusan == STATUS_DISETUJUI_CODE) {
                                viewModel.updateAnnualLeaveRights()
                            }
                            mAlertDialog.dismiss()
                        }
                    }
                })
            })
        }
        mDialogView.btn_dialog_batal.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun setupProgressBar() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progress.show()
            } else {
                progress.dismiss()
            }
        })

        viewModel.isLoadingRights.observe(this, Observer {
            if (it) {
                progress.show()
            } else {
                progress.dismiss()
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
