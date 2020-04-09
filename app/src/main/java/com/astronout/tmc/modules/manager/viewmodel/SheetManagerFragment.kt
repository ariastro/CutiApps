package com.astronout.tmc.modules.manager.viewmodel

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.astronout.tmc.R
import com.astronout.tmc.modules.manager.model.GetManagerListModel
import com.astronout.tmc.modules.manager.view.ManagerListActivity
import kotlinx.android.synthetic.main.fragment_sheet_manager.*

class SheetManagerFragment : SuperBottomSheetFragment() {

    private var manager: GetManagerListModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sheet_manager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sheet_non_aktif_manager.setOnClickListener {
            this.dismiss()
            (activity as ManagerListActivity).nonAktifManager(manager?.managerId!!, manager?.managerStatus!!)
        }
        sheet_aktif_manager.setOnClickListener {
            this.dismiss()
            (activity as ManagerListActivity).aktifManager(manager?.managerId!!, manager?.managerStatus!!)
        }
        sheet_batal.setOnClickListener {
            this.dismiss()
        }
    }

    override fun getCornerRadius() = context!!.resources.getDimension(R.dimen.sheet_rounded_corner)

    override fun getPeekHeight(): Int = 380

    override fun getStatusBarColor() = Color.RED

    fun setManager(manager: GetManagerListModel) {
        this.manager = manager
    }

}
