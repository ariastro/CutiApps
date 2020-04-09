package com.astronout.tmc.modules.leavetype.annual.view


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment

import com.astronout.tmc.R
import com.astronout.tmc.modules.leavetype.annual.model.GetAnnualModel
import kotlinx.android.synthetic.main.fragment_sheet_annual.*

/**
 * A simple [Fragment] subclass.
 */
class SheetAnnualFragment : SuperBottomSheetFragment() {

    private var annual: GetAnnualModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sheet_annual, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sheet_ubah_annual.setOnClickListener {
            this.dismiss()
            val intent = Intent(context, UpdateAnnualActivity::class.java)
            intent.putExtra(UpdateAnnualActivity.EXTRA_ANNUAL, annual)
            (activity as GetAnnualActivity).startActivityForResult(intent, UpdateAnnualActivity.REQUEST_UPDATE_ANNUAL)
        }
        sheet_hapus_annual.setOnClickListener {
            this.dismiss()
            (activity as GetAnnualActivity).removeAnnual(annual?.id!!)
        }
        sheet_batal.setOnClickListener {
            this.dismiss()
        }
    }

    override fun getCornerRadius() = context!!.resources.getDimension(R.dimen.sheet_rounded_corner)

    override fun getPeekHeight(): Int = 380

    override fun getStatusBarColor() = Color.RED

    fun setAnnual(annualModel: GetAnnualModel) {
        this.annual = annualModel
    }

}
