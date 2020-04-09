package com.astronout.tmc.modules.leavetype.annual.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityGetAnnualBinding
import com.astronout.tmc.modules.leavetype.annual.adapter.GetAnnualAdapter
import com.astronout.tmc.modules.leavetype.annual.view.AddAnnualActivity.Companion.REQUEST_ADD_ANNUAL
import com.astronout.tmc.modules.leavetype.annual.view.UpdateAnnualActivity.Companion.REQUEST_UPDATE_ANNUAL
import com.astronout.tmc.modules.leavetype.annual.viewmodel.GetAnnualViewModel
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.showToast

class GetAnnualActivity : BaseActivity() {

    private lateinit var binding: ActivityGetAnnualBinding
    private lateinit var viewModel: GetAnnualViewModel

    private lateinit var adapter: GetAnnualAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_annual)
        viewModel = ViewModelProvider(this).get(GetAnnualViewModel::class.java)
        binding.getAnnual = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listAnnual.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = GetAnnualAdapter(GetAnnualAdapter.OnClickListener { getAnnualModel ->
                    val sheet = SheetAnnualFragment()
                    sheet.setAnnual(getAnnualModel)
                    sheet.show(supportFragmentManager, "SheetAnnualFragment")
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvAnnual.setHasFixedSize(true)
                binding.rvAnnual.adapter = adapter
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                viewModel.getListAnnual()
                showToast(getString(R.string.delete_success))
            } else {
                showToast(getString(R.string.delete_failed))
            }
        })

        viewModel.getListAnnual()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupSwipeRefressLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListAnnual()
            binding.swipeRefresh.isRefreshing = false
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
    }

    fun removeAnnual(annualId: String) {
        viewModel.setAnnualId(annualId)
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.delete_alert))
        alert.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.deleteAnnual()
        }
        alert.setNegativeButton(android.R.string.no) { dialog, _ ->
            dialog.cancel()
        }
        alert.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            startActivityForResult(Intent(this, AddAnnualActivity::class.java), REQUEST_ADD_ANNUAL)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_ANNUAL) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListAnnual()
                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
                }
            }
        } else if (requestCode == REQUEST_UPDATE_ANNUAL) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListAnnual()
                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}
