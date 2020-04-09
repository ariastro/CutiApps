package com.astronout.tmc.modules.department.view


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment

import com.astronout.tmc.R
import com.astronout.tmc.modules.department.model.GetDepartmentModel
import kotlinx.android.synthetic.main.fragment_sheet_department.*

/**
 * A simple [Fragment] subclass.
 */
class SheetDepartmentFragment : SuperBottomSheetFragment() {

    private var department: GetDepartmentModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sheet_department, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sheet_ubah_divisi.setOnClickListener {
            this.dismiss()
            val intent = Intent(context, UpdateDepartmentActivity::class.java)
            intent.putExtra(UpdateDepartmentActivity.EXTRA_DEPARTMENT, department)
            (activity as DepartmentActivity).startActivityForResult(intent, UpdateDepartmentActivity.REQUEST_UPDATE_DEPARTMENT)
        }
        sheet_hapus_divisi.setOnClickListener {
            this.dismiss()
            (activity as DepartmentActivity).removeDepartment(department?.id!!)
        }
        sheet_batal.setOnClickListener {
            this.dismiss()
        }
    }

    override fun getCornerRadius() = context!!.resources.getDimension(R.dimen.sheet_rounded_corner)

    override fun getPeekHeight(): Int = 380

    override fun getStatusBarColor() = Color.RED

    fun setDepartment(department: GetDepartmentModel) {
        this.department = department
    }

}
