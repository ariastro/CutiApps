package com.astronout.tmc.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.astronout.tmc.R
import kotlinx.android.synthetic.main.layout_progress_dialog.*

class AppProgressDialog(context : Context, message : String = "") : Dialog(context){

    private val message: String

    init {
        this.message = message
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_progress_dialog)
        textLoading.text = message
    }
}