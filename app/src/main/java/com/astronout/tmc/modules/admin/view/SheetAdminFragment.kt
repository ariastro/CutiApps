package com.astronout.tmc.modules.admin.view


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.astronout.tmc.R
import com.astronout.tmc.modules.admin.model.GetAdminListModel
import kotlinx.android.synthetic.main.fragment_sheet_admin.*

/**
 * A simple [Fragment] subclass.
 */
class SheetAdminFragment : SuperBottomSheetFragment() {

    private var admin: GetAdminListModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sheet_admin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sheet_non_aktif_admin.setOnClickListener {
            this.dismiss()
            (activity as AdminListActivity).nonAktifAdmin(admin?.id!!, admin?.adminStatus!!)
        }
        sheet_aktif_admin.setOnClickListener {
            this.dismiss()
            (activity as AdminListActivity).aktifAdmin(admin?.id!!, admin?.adminStatus!!)
        }
        sheet_batal.setOnClickListener {
            this.dismiss()
        }
    }

    override fun getCornerRadius() = context!!.resources.getDimension(R.dimen.sheet_rounded_corner)

    override fun getPeekHeight(): Int = 380

    override fun getStatusBarColor() = Color.RED

    fun setAdmin(admin: GetAdminListModel) {
        this.admin = admin
    }

}
