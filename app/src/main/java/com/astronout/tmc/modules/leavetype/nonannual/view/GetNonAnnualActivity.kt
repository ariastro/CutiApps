package com.astronout.tmc.modules.leavetype.nonannual.view

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
import com.astronout.tmc.databinding.ActivityGetNonAnnualBinding
import com.astronout.tmc.modules.leavetype.nonannual.adapter.GetNonAnnualAdapter
import com.astronout.tmc.modules.leavetype.nonannual.view.AddNonAnnualActivity.Companion.REQUEST_ADD_NON_ANNUAL
import com.astronout.tmc.modules.leavetype.nonannual.view.UpdateNonAnnualActivity.Companion.REQUEST_UPDATE_NON_ANNUAL
import com.astronout.tmc.modules.leavetype.nonannual.viewmodel.GetNonAnnualViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class GetNonAnnualActivity : BaseActivity() {

    private lateinit var binding: ActivityGetNonAnnualBinding
    private lateinit var viewModel: GetNonAnnualViewModel

    private lateinit var adapter: GetNonAnnualAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_non_annual)
        viewModel = ViewModelProvider(this).get(GetNonAnnualViewModel::class.java)
        binding.getNonAnnual = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listNonAnnual.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter =
                    GetNonAnnualAdapter(GetNonAnnualAdapter.OnClickListener { getNonAnnualModel ->
                        val sheet = SheetNonAnnualFragment()
                        sheet.setNonAnnual(getNonAnnualModel)
                        sheet.show(supportFragmentManager, "SheetNonAnnualFragment")
                    })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvNonAnnual.setHasFixedSize(true)
                binding.rvNonAnnual.adapter = adapter
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                viewModel.getListNonAnnual()
                showToast(getString(R.string.delete_success))
            } else {
                showToast(getString(R.string.delete_failed))
            }
        })

        viewModel.getListNonAnnual()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun removeNonAnnual(nonAnnualId: String) {
        viewModel.setId(nonAnnualId)
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.delete_alert))
        alert.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.deleteNonAnnual()
        }
        alert.setNegativeButton(android.R.string.no) { dialog, _ ->
            dialog.cancel()
        }
        alert.show()
    }

    private fun setupSwipeRefressLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListNonAnnual()
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
            startActivityForResult(
                Intent(this, AddNonAnnualActivity::class.java),
                REQUEST_ADD_NON_ANNUAL
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_NON_ANNUAL) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListNonAnnual()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        } else if (requestCode == REQUEST_UPDATE_NON_ANNUAL) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListNonAnnual()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}