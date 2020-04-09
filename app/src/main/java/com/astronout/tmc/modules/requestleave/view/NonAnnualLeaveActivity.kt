package com.astronout.tmc.modules.requestleave.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityNonAnnualLeaveBinding
import com.astronout.tmc.modules.requestleave.view.ChooseNonAnnualActivity.Companion.EXTRA_NON_ANNUAL_TYPE
import com.astronout.tmc.modules.requestleave.view.ChooseNonAnnualActivity.Companion.EXTRA_RIGHTS_GRANTED
import com.astronout.tmc.modules.requestleave.view.ChooseNonAnnualActivity.Companion.REQUEST_NON_ANNUAL_TYPE
import com.astronout.tmc.modules.requestleave.viewmodel.NonAnnualLeaveViewModel
import com.astronout.tmc.utils.*
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.util.*

class NonAnnualLeaveActivity : BaseActivity() {

    private lateinit var binding: ActivityNonAnnualLeaveBinding
    private lateinit var viewModel: NonAnnualLeaveViewModel

    companion object {
        const val REQUEST_NON_ANNUAL_LEAVE = 2451
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_non_annual_leave)
        viewModel = ViewModelProvider(this).get(NonAnnualLeaveViewModel::class.java)
        binding.nonAnnualLeave = viewModel

        setSupportActionBar(binding.toolbar)
        supportActionBar?.subtitle = resources.getString(R.string.tidak_mengurangi_hak_cuti_tahunan)

        setupProgressBar()

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

        viewModel.rightsUsed.observe(this, Observer {
            if (it < 1) {
                binding.endDate.setText("")
            }
            binding.totalHariCuti.text = "Hak cuti dipergunakan : $it hari."
        })

        viewModel.jenisCuti.observe(this, Observer { jenisCuti ->
            viewModel.rightsGranted.observe(this, Observer { rightsGranted ->
                if (jenisCuti.isNotEmpty()) {
                    if (rightsGranted != 0) {
                        binding.jenisCuti.setText("$jenisCuti | Hak diberikan $rightsGranted hari")
                    }
                }
            })
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
            when {
                binding.jenisCuti.text.isNullOrEmpty() -> binding.jenisCutiLayout.error =
                    getString(R.string.choose_leave_type_first)
                binding.startDate.text.isNullOrEmpty() -> binding.startDateLayout.error =
                    getString(R.string.choose_start_date_first)
                else -> {
                    binding.jenisCutiLayout.error = null
                    binding.startDateLayout.error = null
                    viewModel.rightsGranted.observe(this, Observer {
                        val dateStart = viewModel.startDate.value
                        val date =
                            DateTime.parse(dateStart, DateTimeFormat.forPattern(dateTimeFormat))
                        val calendarDate = date.toCalendar(Locale.getDefault())
                        calendarDate.add(Calendar.DATE, 1)
                        val maxDate = date.toCalendar(Locale.getDefault())
                        maxDate.add(Calendar.DATE, it)

                        dialogChoosefixedMinMaxDate(calendarDate, maxDate, this) { dateResult ->
                            viewModel.setEndDate(dateResult)
                        }
                    })
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
                    viewModel.setRightsUsed(Days.daysBetween(start, end).days)
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
                    viewModel.postNewLeave()
                }
            }
        }

        binding.jenisCuti.setOnClickListener {
            if (binding.endDate.text!!.isNotEmpty()) {
                binding.endDate.setText("")
                viewModel.setRightsUsed(0)
            }
            val intent = Intent(this, ChooseNonAnnualActivity::class.java)
            startActivityForResult(intent, REQUEST_NON_ANNUAL_TYPE)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        if (requestCode == REQUEST_NON_ANNUAL_TYPE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val jeniCuti = data.getStringExtra(EXTRA_NON_ANNUAL_TYPE)
                    val rightsGranted = data.getStringExtra(EXTRA_RIGHTS_GRANTED)
                    viewModel.setJenisCuti(jeniCuti!!)
                    viewModel.setRightsGranted(rightsGranted!!.toInt())
                }
            }
        }
    }

}