package com.astronout.tmc.modules.requestleave.view

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAnnualLeaveBinding
import com.astronout.tmc.modules.requestleave.view.ChooseAnnualActivity.Companion.EXTRA_ANNUAL_TYPE
import com.astronout.tmc.modules.requestleave.view.ChooseAnnualActivity.Companion.REQUEST_ANNUAL_TYPE
import com.astronout.tmc.modules.requestleave.viewmodel.AnnualLeaveViewModel
import com.astronout.tmc.utils.*
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.util.*

class AnnualLeaveActivity : BaseActivity() {

    private lateinit var binding: ActivityAnnualLeaveBinding
    private lateinit var viewModel: AnnualLeaveViewModel

    companion object {
        const val REQUEST_ANNUAL_LEAVE = 7251
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_annual_leave)
        viewModel = ViewModelProvider(this).get(AnnualLeaveViewModel::class.java)
        binding.annualLeave = viewModel

        setSupportActionBar(binding.toolbar)
        supportActionBar?.subtitle = resources.getString(R.string.mengurangi_hak_cuti_tahunan)

        setupProgressBar()

        viewModel.sisaCuti.observe(this, Observer {
            binding.sisaCuti.text = "Sisa cuti anda : $it hari."
            if (it.toInt() < 1) {
                binding.btnAjukan.visibility = gone()
                binding.alertNoRightLeft.visibility = visible()
            }
        })

        viewModel.startDate.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.startDate.setText(it)
            }
        })

        viewModel.endDate.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.endDate.setText(it)
            }
        })

        viewModel.leaveRightUsed.observe(this, Observer {
            if (it < 1) {
                binding.endDate.setText("")
            }
            binding.totalHariCuti.text = "Hak cuti dipergunakan : $it hari."
        })

        viewModel.jenisCuti.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.jenisCuti.setText(it)
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(EXTRA_MESSAGE, getString(R.string.leave_request_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.leave_request_failed))
            }
        })

        binding.startDate.setOnClickListener {
            dialogChooseLeaveDate(this) { dateResult ->
                viewModel.setStartDate(dateResult)
            }
        }

        binding.endDate.setOnClickListener {
            if (binding.startDate.text.isNullOrEmpty()) {
                showToast(resources.getString(R.string.choose_start_date_first))
            } else {
                val dateStart = viewModel.startDate.value
                val date = DateTime.parse(
                    dateStart,
                    DateTimeFormat.forPattern(dateTimeFormat)
                )
                val calendarDate = date.toCalendar(Locale.getDefault())
                calendarDate.add(Calendar.DATE, 1)
                dialogChooseLeaveUntil(calendarDate, this) { dateResult ->
                    viewModel.setEndDate(dateResult)
                }
            }
        }

        viewModel.startDate.observe(this, Observer { startDate ->
            viewModel.endDate.observe(this, Observer { endDate ->
                val start = DateTime.parse(
                    startDate,
                    DateTimeFormat.forPattern(dateTimeFormat)
                )
                val end = DateTime.parse(
                    endDate,
                    DateTimeFormat.forPattern(dateTimeFormat)
                )
                if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                    viewModel.setLeaveRightUsed(Days.daysBetween(start, end).days)
                }
            })
        })

        binding.btnAjukan.setOnClickListener {
            when {
                binding.jenisCuti.text.toString().isEmpty() -> binding.jenisCuti.error =
                    getString(R.string.empty_field)
                binding.startDate.text.toString().isEmpty() -> binding.startDate.error =
                    getString(R.string.empty_field)
                binding.endDate.text.toString().isEmpty() -> binding.endDate.error =
                    getString(R.string.empty_field)
                binding.keterangan.text.toString().isEmpty() -> binding.keterangan.error =
                    getString(R.string.empty_field)
                else -> {
                    viewModel.leaveRightUsed.observe(this, Observer {
                        if (it > viewModel.sisaCuti.value!!.toInt()) {
                            showToast(getString(R.string.not_enough_rights))
                        } else {
                            viewModel.postNewLeave()
                        }
                    })
                }
            }
        }

        binding.jenisCuti.setOnClickListener {
            val intent = Intent(this, ChooseAnnualActivity::class.java)
            startActivityForResult(intent, REQUEST_ANNUAL_TYPE)
        }

        getLeaveRights()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun getLeaveRights() {
        viewModel.getSisaCuti()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupProgressBar() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progress.show()
            } else {
                progress.dismiss()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ANNUAL_TYPE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val jeniCuti = data.getStringExtra(EXTRA_ANNUAL_TYPE)
                    viewModel.setJenisCuti(jeniCuti!!)
                }
            }
        }
    }

}
