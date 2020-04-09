package com.astronout.tmc.modules.leavetype.nonannual.view


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.astronout.tmc.R
import com.astronout.tmc.modules.leavetype.nonannual.model.GetNonAnnualModel
import kotlinx.android.synthetic.main.fragment_sheet_non_annual.*

/**
 * A simple [Fragment] subclass.
 */
class SheetNonAnnualFragment : SuperBottomSheetFragment() {

    private var nonAnnual: GetNonAnnualModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sheet_non_annual, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sheet_ubah_nonannual.setOnClickListener {
            this.dismiss()
            val intent = Intent(context, UpdateNonAnnualActivity::class.java)
            intent.putExtra(UpdateNonAnnualActivity.EXTRA_NON_ANNUAL, nonAnnual)
            (activity as GetNonAnnualActivity).startActivityForResult(intent,
                UpdateNonAnnualActivity.REQUEST_UPDATE_NON_ANNUAL
            )
        }
        sheet_hapus_nonannual.setOnClickListener {
            this.dismiss()
            (activity as GetNonAnnualActivity).removeNonAnnual(nonAnnual?.id!!)
        }
        sheet_batal.setOnClickListener {
            this.dismiss()
        }
    }

    override fun getCornerRadius() = context!!.resources.getDimension(R.dimen.sheet_rounded_corner)

    override fun getPeekHeight(): Int = 380

    override fun getStatusBarColor() = Color.RED

    fun setNonAnnual(nonAnnualModel: GetNonAnnualModel) {
        this.nonAnnual = nonAnnualModel
    }

}
