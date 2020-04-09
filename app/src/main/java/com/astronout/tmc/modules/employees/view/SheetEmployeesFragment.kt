package com.astronout.tmc.modules.employees.view


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.astronout.tmc.R
import com.astronout.tmc.modules.employees.model.GetAllEmployeesModel
import kotlinx.android.synthetic.main.fragment_sheet_employees.*

/**
 * A simple [Fragment] subclass.
 */
class SheetEmployeesFragment : SuperBottomSheetFragment() {

    private var employees: GetAllEmployeesModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sheet_employees, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sheet_ubah_employee.setOnClickListener {
            this.dismiss()
            val intent = Intent(context, UpdateEmployeeActivity::class.java)
            intent.putExtra(UpdateEmployeeActivity.EXTRA_EMPLOYEE, employees)
            (activity as EmployeesActivity).startActivityForResult(intent, UpdateEmployeeActivity.REQUEST_UPDATE_EMPLOYEE)
        }
        sheet_hapus_employee.setOnClickListener {
            this.dismiss()
            (activity as EmployeesActivity).removeEmployee(employees?.id!!)
        }
        sheet_batal.setOnClickListener {
            this.dismiss()
        }
    }

    override fun getCornerRadius() = context!!.resources.getDimension(R.dimen.sheet_rounded_corner)

    override fun getPeekHeight(): Int = 380

    override fun getStatusBarColor() = Color.RED

    fun setEmployees(employees: GetAllEmployeesModel) {
        this.employees = employees
    }

}