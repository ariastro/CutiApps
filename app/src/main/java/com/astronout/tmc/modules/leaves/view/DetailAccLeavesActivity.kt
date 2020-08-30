package com.astronout.tmc.modules.leaves.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityDetailAccLeavesBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.leaves.model.GetAccLeavesModel
import com.astronout.tmc.modules.leaves.viewmodel.DetailAccLeavesViewModel
import com.astronout.tmc.utils.*
import com.astronout.tmc.utils.Constants.USER_KASI
import com.astronout.tmc.utils.Constants.USER_KSB
import com.astronout.tmc.utils.Constants.USER_MANAGER
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.decision_dialog_ksb_layout.view.*
import kotlinx.android.synthetic.main.decision_dialog_layout.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DetailAccLeavesActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailAccLeavesBinding
    private lateinit var viewModel: DetailAccLeavesViewModel

    companion object {
        const val EXTRA_ACC_LEAVE = "EXTRA_ACC_LEAVE"
        val REQUEST_DETAIL_ACC_LEAVES = 5824
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_acc_leaves)
        viewModel = ViewModelProvider(this).get(DetailAccLeavesViewModel::class.java)
        binding.detailAccLeaves = viewModel

        setSupportActionBar(binding.toolbar)

        if (intent.hasExtra(EXTRA_ACC_LEAVE)) {
            val extraData = intent.getParcelableExtra<GetAccLeavesModel>(EXTRA_ACC_LEAVE)

            if (extraData.managerAcc == Constants.STATUS_DISETUJUI_CODE) {
                binding.statusCuti.text = Constants.STATUS_DISETUJUI
                binding.statusCuti.background =
                    ContextCompat.getDrawable(this, R.drawable.bg_rounded_blue)
            } else {
                if (extraData.kasiAcc == Constants.STATUS_DITOLAK_CODE || extraData.kasubagAcc == Constants.STATUS_DITOLAK_CODE ||
                    extraData.managerAcc == Constants.STATUS_DITOLAK_CODE
                ) {
                    binding.statusCuti.text = Constants.STATUS_DITOLAK
                    binding.statusCuti.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_rounded_red)
                } else {
                    binding.statusCuti.text = Constants.STATUS_MENUNGGU
                    binding.statusCuti.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_rounded_orange)
                }
            }

            when {
                User.userType == USER_KASI -> {
                    binding.kasiRemark.visibility = gone()
                    binding.tvKasiRemark.visibility = gone()
                    binding.ksbRemark.visibility = gone()
                    binding.tvKsbRemark.visibility = gone()
                }
                User.userType == USER_MANAGER -> {
                    binding.kasiRemark.visibility = gone()
                    binding.tvKasiRemark.visibility = gone()
                    binding.ksbRemark.visibility = visible()
                    binding.tvKsbRemark.visibility = visible()
                }
                else -> {
                    binding.kasiRemark.visibility = visible()
                    binding.tvKasiRemark.visibility = visible()
                    binding.ksbRemark.visibility = gone()
                    binding.tvKsbRemark.visibility = gone()
                }
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
            binding.subag.text = extraData.empDepartment
            binding.keterangan.text = extraData.description
            binding.nomorCuti.text =
                if (extraData.nomorCuti.isNullOrEmpty()) "-" else extraData.nomorCuti
            binding.kasiRemark.text =
                if (extraData.kasiRemark.isNullOrEmpty()) "-" else extraData.kasiRemark
            binding.ksbRemark.text =
                if (extraData.kasubagRemark.isNullOrEmpty()) "-" else extraData.kasubagRemark

            viewModel.setId(extraData.id)
            viewModel.setEmployeeId(extraData.empid)
            viewModel.setLeaveGranted(extraData.rightsGranted)

            viewModel.getSisaCuti()

        }

        viewModel.sisaCuti.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.sisaCuti.text = "$it hari"
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(Constants.EXTRA_MESSAGE, getString(R.string.make_decision_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.make_decision_failed))
            }
        })

        viewModel.statusRights.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(Constants.EXTRA_MESSAGE, getString(R.string.make_decision_success))
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
        if (User.userType == USER_KSB) {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.decision_dialog_ksb_layout, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle(resources.getString(R.string.ambil_keputusan))
            val mAlertDialog = mBuilder.show()
            val spinner: AppCompatSpinner = mDialogView.findViewById(R.id.spinnerKeputusanKsb)
            val remark: TextInputEditText = mDialogView.findViewById(R.id.ksbRemark)
            val noCuti: TextInputEditText = mDialogView.findViewById(R.id.nomor_cuti_ksb)
            val noCutiLayout: TextInputLayout = mDialogView.findViewById(R.id.nomor_cuti_ksb_layout)
            val remarkLayout: TextInputLayout = mDialogView.findViewById(R.id.ksb_remark_layout)
            mDialogView.btn_dialog_simpan_ksb.setOnClickListener {
                when {
                    spinner.selectedItem.toString() == Constants.SETUJUI -> viewModel.setKeputusanKasubag(
                        Constants.STATUS_DISETUJUI_CODE
                    )
                    spinner.selectedItem.toString() == Constants.TOLAK -> viewModel.setKeputusanKasubag(
                        Constants.STATUS_DITOLAK_CODE
                    )
                    else -> {
                        viewModel.setKeputusanKasubag(Constants.STATUS_MENUNGGU_CODE)
                    }
                }
                viewModel.setKasubagRemark(remark.text.toString())
                viewModel.setNomorCuti(noCuti.text.toString())
                viewModel.kasubagRemark.observe(this, Observer { kasubagRemark ->
                    viewModel.nomorCuti.observe(this, Observer { noCuti ->
                        viewModel.keputusanKasubag.observe(this, Observer { keputusanKasubag ->
                            if (keputusanKasubag == Constants.STATUS_MENUNGGU_CODE) {
                                showToast(getString(R.string.empty_decision))
                            } else {
                                if (noCuti.isEmpty()) {
                                    noCutiLayout.error = getString(R.string.empty_no_cuti)
                                } else {
                                    noCutiLayout.error = ""
                                    if (kasubagRemark.isEmpty()) {
                                        remarkLayout.error = getString(R.string.empty_admin_remark)
                                    } else {
                                        remarkLayout.error = ""
                                        viewModel.postAccKasubag()
                                        mAlertDialog.dismiss()
                                    }
                                }
                            }
                        })
                    })
                })
            }
        } else {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.decision_dialog_layout, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle(resources.getString(R.string.ambil_keputusan))
            val mAlertDialog = mBuilder.show()
            val spinner: AppCompatSpinner = mDialogView.findViewById(R.id.spinnerKeputusan)
            val remark: TextInputEditText = mDialogView.findViewById(R.id.remark)
            val remarkLayout: TextInputLayout = mDialogView.findViewById(R.id.remark_layout)

            if (User.userType == USER_KASI) {
                mDialogView.btn_dialog_simpan.setOnClickListener {
                    when {
                        spinner.selectedItem.toString() == Constants.SETUJUI -> viewModel.setKeputusanKasi(
                            Constants.STATUS_DISETUJUI_CODE
                        )
                        spinner.selectedItem.toString() == Constants.TOLAK -> viewModel.setKeputusanKasi(
                            Constants.STATUS_DITOLAK_CODE
                        )
                        else -> {
                            viewModel.setKeputusanKasi(Constants.STATUS_MENUNGGU_CODE)
                        }
                    }
                    viewModel.setKasiRemark(remark.text.toString())
                    viewModel.kasiRemark.observe(this, Observer { kasiRemark ->
                        viewModel.keputusanKasi.observe(this, Observer { keputusanKasi ->
                            if (keputusanKasi == Constants.STATUS_MENUNGGU_CODE) {
                                showToast(getString(R.string.empty_decision))
                            } else {
                                if (kasiRemark.isEmpty()) {
                                    remarkLayout.error = getString(R.string.empty_admin_remark)
                                } else {
                                    remarkLayout.error = ""
                                    viewModel.postAccKasi()
                                    mAlertDialog.dismiss()
                                }
                            }
                        })
                    })
                }
            } else {
                mDialogView.btn_dialog_simpan.setOnClickListener {
                    when {
                        spinner.selectedItem.toString() == Constants.SETUJUI -> viewModel.setKeputusanManager(
                            Constants.STATUS_DISETUJUI_CODE
                        )
                        spinner.selectedItem.toString() == Constants.TOLAK -> viewModel.setKeputusanManager(
                            Constants.STATUS_DITOLAK_CODE
                        )
                        else -> {
                            viewModel.setKeputusanManager(Constants.STATUS_MENUNGGU_CODE)
                        }
                    }
                    viewModel.setManagerRemark(remark.text.toString())
                    viewModel.managerRemark.observe(this, Observer { managerRemark ->
                        viewModel.keputusanManager.observe(this, Observer { keputusanManager ->
                            if (keputusanManager == Constants.STATUS_MENUNGGU_CODE) {
                                showToast(getString(R.string.empty_decision))
                            } else {
                                if (managerRemark.isEmpty()) {
                                    remarkLayout.error = getString(R.string.empty_admin_remark)
                                } else {
                                    remarkLayout.error = ""
                                    viewModel.postAccManager()
                                    if (keputusanManager == Constants.STATUS_DISETUJUI_CODE) {
                                        viewModel.updateAnnualLeaveRights()
                                    }
                                    mAlertDialog.dismiss()
                                }
                            }
                        })
                    })
                }
            }
            mDialogView.btn_dialog_batal.setOnClickListener {
                mAlertDialog.dismiss()
            }
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