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

//            when {
//                extraData.status == STATUS_MENUNGGU_CODE -> {
//                    binding.statusCuti.text = STATUS_MENUNGGU
//                    binding.statusCuti.background =
//                        ContextCompat.getDrawable(this, R.drawable.bg_rounded_orange)
//                }
//                extraData.status == STATUS_DISETUJUI_CODE -> {
//                    binding.statusCuti.text = STATUS_DISETUJUI
//                    binding.statusCuti.background =
//                        ContextCompat.getDrawable(this, R.drawable.bg_rounded_blue)
//                }
//                else -> {
//                    binding.statusCuti.text = STATUS_DITOLAK
//                    binding.statusCuti.background =
//                        ContextCompat.getDrawable(this, R.drawable.bg_rounded_red)
//                }
//            }

            if (extraData.managerAcc == STATUS_DISETUJUI_CODE) {
                binding.statusCuti.text = STATUS_DISETUJUI
                binding.tvRemark.text = getString(R.string.catatan_kepala_bnn)
                binding.managerRemark.text = extraData.managerRemark
                binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_blue)
            } else {
                when (STATUS_DITOLAK_CODE) {
                    extraData.kasiAcc -> {
                        binding.managerRemark.text = extraData.kasiRemark
                        binding.tvRemark.text = getString(R.string.catatan_kasi)
                        binding.statusCuti.text = STATUS_DITOLAK
                        binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_red)
                    }
                    extraData.kasubagAcc -> {
                        binding.managerRemark.text = extraData.kasubagRemark
                        binding.tvRemark.text = getString(R.string.catatan_kasubag)
                        binding.statusCuti.text = STATUS_DITOLAK
                        binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_red)
                    }
                    extraData.managerAcc -> {
                        binding.managerRemark.text = extraData.managerRemark
                        binding.tvRemark.text = getString(R.string.catatan_kepala_bnn)
                        binding.statusCuti.text = STATUS_DITOLAK
                        binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_red)
                    }
                    else -> {
                        binding.managerRemark.text = "-"
                        binding.tvRemark.text = getString(R.string.catatan_admin)
                        binding.statusCuti.text = STATUS_MENUNGGU
                        binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_orange)
                    }
                }
            }

//            if (extraData.managerRemark.isNullOrEmpty()) {
//                binding.managerRemark.text = "-"
//            } else {
//                binding.managerRemark.text = extraData.managerRemark
//            }

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
