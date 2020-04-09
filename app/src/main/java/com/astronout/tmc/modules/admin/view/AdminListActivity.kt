package com.astronout.tmc.modules.admin.view

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
import com.astronout.tmc.databinding.ActivityAdminListBinding
import com.astronout.tmc.modules.admin.adapter.AdminListAdapter
import com.astronout.tmc.modules.admin.view.AddAdminActivity.Companion.REQUEST_ADD_ADMIN
import com.astronout.tmc.modules.admin.viewmodel.AdminListViewModel
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.Constants.STATUS_AKTIF_CODE
import com.astronout.tmc.utils.Constants.STATUS_NON_AKTIF_CODE
import com.astronout.tmc.utils.showToast

class AdminListActivity : BaseActivity() {

    private lateinit var binding: ActivityAdminListBinding
    private lateinit var viewModel: AdminListViewModel

    private lateinit var adapter: AdminListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_list)
        viewModel = ViewModelProvider(this).get(AdminListViewModel::class.java)
        binding.listAdmin = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listAdmin.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = AdminListAdapter(this, AdminListAdapter.OnClickListener {adminList ->
                    val sheet = SheetAdminFragment()
                    sheet.setAdmin(adminList)
                    sheet.show(supportFragmentManager, "SheetAdminFragment")
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvListAdmin.setHasFixedSize(true)
                binding.rvListAdmin.adapter = adapter
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                viewModel.getListAdmin()
                showToast(getString(R.string.update_success))
            } else {
                showToast(getString(R.string.update_failed))
            }
        })

        viewModel.getListAdmin()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun nonAktifAdmin(id: String, status: String) {
        viewModel.setId(id)
        if (viewModel.id.value!! != User.adminId) {
            if (status == STATUS_AKTIF_CODE) {
                val alert = AlertDialog.Builder(this)
                alert.setMessage(getString(R.string.non_aktif_alert))
                alert.setPositiveButton(android.R.string.yes) { _, _ ->
                    viewModel.nonAktifAdmin()
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

    fun aktifAdmin(id: String, status: String) {
        viewModel.setId(id)
        if (viewModel.id.value!! != User.adminId) {
            if (status == STATUS_NON_AKTIF_CODE) {
                val alert = AlertDialog.Builder(this)
                alert.setMessage(getString(R.string.aktif_alert))
                alert.setPositiveButton(android.R.string.yes) { _, _ ->
                    viewModel.aktifAdmin()
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
            viewModel.getListAdmin()
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
            startActivityForResult(Intent(this, AddAdminActivity::class.java), REQUEST_ADD_ADMIN)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_ADMIN) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListAdmin()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}