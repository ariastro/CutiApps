package com.astronout.tmc.modules.kasi.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityKasiListBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.kasi.adapter.KasiListAdapter
import com.astronout.tmc.modules.kasi.view.AddKasiActivity.Companion.REQUEST_ADD_KASI
import com.astronout.tmc.modules.kasi.viewmodel.KasiListViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class KasiListActivity : BaseActivity() {

    private lateinit var binding: ActivityKasiListBinding
    private lateinit var viewModel: KasiListViewModel

    private lateinit var adapter: KasiListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_kasi_list)
        viewModel = ViewModelProvider(this).get(KasiListViewModel::class.java)

        setSupportActionBar(binding.toolbar)

        viewModel.listKasi.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = KasiListAdapter(this, KasiListAdapter.OnClickListener { kasi ->
                    val sheet = SheetKasiFragment()
                    sheet.setKasi(kasi)
                    sheet.show(supportFragmentManager, "SheetKasiFragment")
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvListKasi.setHasFixedSize(true)
                binding.rvListKasi.adapter = adapter
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                viewModel.getListKasi()
                showToast(getString(R.string.update_success))
            } else {
                showToast(getString(R.string.update_failed))
            }
        })

        viewModel.getListKasi()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun nonAktifKasi(id: String, status: String) {
        viewModel.setId(id)
        if (viewModel.id.value!! != User.managerId) {
            if (status == Constants.STATUS_AKTIF_CODE) {
                val alert = AlertDialog.Builder(this)
                alert.setMessage(getString(R.string.non_aktif_alert))
                alert.setPositiveButton(android.R.string.yes) { _, _ ->
                    viewModel.nonAktifKasi()
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

    fun aktifKasi(id: String, status: String) {
        viewModel.setId(id)
        if (viewModel.id.value!! != User.managerId) {
            if (status == Constants.STATUS_NON_AKTIF_CODE) {
                val alert = AlertDialog.Builder(this)
                alert.setMessage(getString(R.string.aktif_alert))
                alert.setPositiveButton(android.R.string.yes) { _, _ ->
                    viewModel.aktifKasi()
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
            viewModel.getListKasi()
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
            startActivityForResult(Intent(this, AddKasiActivity::class.java), REQUEST_ADD_KASI)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_KASI) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListKasi()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}