package com.astronout.tmc.modules.leaves.view

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityDetailLeaveBinding
import com.astronout.tmc.modules.leaves.model.GetLeaveByIdModel
import com.astronout.tmc.modules.leaves.viewmodel.DetailLeaveViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.Constants.STATUS_DISETUJUI
import com.astronout.tmc.utils.Constants.STATUS_DISETUJUI_CODE
import com.astronout.tmc.utils.Constants.STATUS_DITOLAK
import com.astronout.tmc.utils.Constants.STATUS_MENUNGGU
import com.astronout.tmc.utils.Constants.STATUS_MENUNGGU_CODE
import com.astronout.tmc.utils.dateTimeFormat
import com.astronout.tmc.utils.defaultDateTimeFormat
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DetailLeaveActivity : BaseActivity() {

    private lateinit var viewModel: DetailLeaveViewModel
    private lateinit var binding: ActivityDetailLeaveBinding

    companion object {
        val EXTRA_LEAVE = "EXTRA_LEAVE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_leave)
        viewModel = ViewModelProvider(this).get(DetailLeaveViewModel::class.java)
        binding.detailLeave = viewModel

        setSupportActionBar(binding.toolbar)

        if (intent.hasExtra(EXTRA_LEAVE)) {
            val extraData = intent.getParcelableExtra<GetLeaveByIdModel>(EXTRA_LEAVE)

//            when {
//                extraData.status == STATUS_MENUNGGU_CODE -> {
//                    binding.statusCuti.text = STATUS_MENUNGGU
//                    binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_orange)
//                }
//                extraData.status == STATUS_DISETUJUI_CODE -> {
//                    binding.statusCuti.text = STATUS_DISETUJUI
//                    binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_blue)
//                }
//                else -> {
//                    binding.statusCuti.text = STATUS_DITOLAK
//                    binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_red)
//                }
//            }

            if (extraData.managerAcc == STATUS_DISETUJUI_CODE) {
                binding.statusCuti.text = STATUS_DISETUJUI
                binding.tvRemark.text = getString(R.string.catatan_kepala_bnn)
                binding.managerRemark.text = extraData.managerRemark
                binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_blue)
            } else {
                when (Constants.STATUS_DITOLAK_CODE) {
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
                        binding.statusCuti.text = STATUS_MENUNGGU
                        binding.statusCuti.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_orange)
                    }
                }
            }

//            if (extraData.annual == ANNUAL_NO) {
//                binding.isTahunan.text = NON_ANNUAL
//            } else {
//                binding.isTahunan.text = ANNUAL
//            }

//            if (extraData.managerRemark.isNullOrEmpty()) {
//                binding.managerRemark.text = "-"
//            } else {
//                binding.managerRemark.text = extraData.managerRemark
//            }

            binding.jumlahHariCuti.text = extraData.rightsGranted + " hari"
            val date = DateTime.parse(extraData.postingDate, DateTimeFormat.forPattern(
                defaultDateTimeFormat))
            binding.tanggalPengajuan.text = date.toString(dateTimeFormat)
            binding.tanggalCuti.text = "${extraData.fromDate} - ${extraData.toDate}"
            binding.jenisCuti.text = extraData.leaveType
            binding.keterangan.text = extraData.description

        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
