package com.astronout.tmc.modules.manager.view

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
import com.astronout.tmc.databinding.ActivityManagerListBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.manager.adapter.ManagerListAdapter
import com.astronout.tmc.modules.manager.view.AddManagerActivity.Companion.REQUEST_ADD_MANAGER
import com.astronout.tmc.modules.manager.viewmodel.ManagerListViewModel
import com.astronout.tmc.modules.manager.viewmodel.SheetManagerFragment
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class ManagerListActivity : BaseActivity() {

    private lateinit var binding: ActivityManagerListBinding
    private lateinit var viewModel: ManagerListViewModel

    private lateinit var adapter: ManagerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_manager_list)
        viewModel = ViewModelProvider(this).get(ManagerListViewModel::class.java)
        binding.listManager = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listManager.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = ManagerListAdapter(this, ManagerListAdapter.OnClickListener {manager ->
                    val sheet = SheetManagerFragment()
                    sheet.setManager(manager)
                    sheet.show(supportFragmentManager, "SheetManagerFragment")
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvListManager.setHasFixedSize(true)
                binding.rvListManager.adapter = adapter
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                viewModel.getListManager()
                showToast(getString(R.string.update_success))
            } else {
                showToast(getString(R.string.update_failed))
            }
        })

        viewModel.getListManager()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun nonAktifManager(id: String, status: String) {
        viewModel.setId(id)
        if (viewModel.id.value!! != User.managerId) {
            if (status == Constants.STATUS_AKTIF_CODE) {
                val alert = AlertDialog.Builder(this)
                alert.setMessage(getString(R.string.non_aktif_alert))
                alert.setPositiveButton(android.R.string.yes) { _, _ ->
                    viewModel.nonAktifManager()
                }
                alert.setNegativeButton(android.R.string.no) { dialog, _ ->
                    dialog.cancel()
                }
                alert.show()
            } else {
                showToast(getString(R.string.already_non_active))
            }
        } else {
            showToast(getString(R.string.invalid_non_active))
        }
    }

    fun aktifManager(id: String, status: String) {
        viewModel.setId(id)
        if (viewModel.id.value!! != User.managerId) {
            if (status == Constants.STATUS_NON_AKTIF_CODE) {
                val alert = AlertDialog.Builder(this)
                alert.setMessage(getString(R.string.aktif_alert))
                alert.setPositiveButton(android.R.string.yes) { _, _ ->
                    viewModel.aktifManager()
                }
                alert.setNegativeButton(android.R.string.no) { dialog, _ ->
                    dialog.cancel()
                }
                alert.show()
            } else {
                showToast(getString(R.string.already_active))
            }
        } else {
            showToast(getString(R.string.invalid_non_active))
        }
    }

    private fun setupSwipeRefressLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListManager()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add){
            startActivityForResult(Intent(this, AddManagerActivity::class.java), REQUEST_ADD_MANAGER)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_MANAGER) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListManager()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}