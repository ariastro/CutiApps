package com.astronout.tmc.modules.kasi.view


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment

import com.astronout.tmc.R
import com.astronout.tmc.modules.kasi.model.GetKasiListModel
import kotlinx.android.synthetic.main.fragment_sheet_kasi.*

/**
 * A simple [Fragment] subclass.
 */
class SheetKasiFragment : SuperBottomSheetFragment() {

    private var kasi: GetKasiListModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sheet_kasi, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sheet_non_aktif_kasi.setOnClickListener {
            this.dismiss()
            (activity as KasiListActivity).nonAktifKasi(kasi?.kasiId!!, kasi?.kasiStatus!!)
        }
        sheet_aktif_kasi.setOnClickListener {
            this.dismiss()
            (activity as KasiListActivity).aktifKasi(kasi?.kasiId!!, kasi?.kasiStatus!!)
        }
        sheet_batal.setOnClickListener {
            this.dismiss()
        }
    }

    override fun getCornerRadius() = context!!.resources.getDimension(R.dimen.sheet_rounded_corner)

    override fun getPeekHeight(): Int = 380

    override fun getStatusBarColor() = Color.RED

    fun setKasi(kasi: GetKasiListModel) {
        this.kasi = kasi
    }

}