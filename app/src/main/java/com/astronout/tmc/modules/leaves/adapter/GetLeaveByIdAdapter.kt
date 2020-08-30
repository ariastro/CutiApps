package com.astronout.tmc.modules.leaves.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.R
import com.astronout.tmc.databinding.ItemLeaveBinding
import com.astronout.tmc.modules.leaves.model.GetLeaveByIdModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.Constants.ANNUAL
import com.astronout.tmc.utils.Constants.ANNUAL_YES
import com.astronout.tmc.utils.Constants.NON_ANNUAL

class GetLeaveByIdAdapter(val context: Context, private val onClickListener: OnClickListener): ListAdapter<GetLeaveByIdModel, GetLeaveByIdAdapter.GetLeaveByIdViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetLeaveByIdViewHolder =
        GetLeaveByIdViewHolder.from(parent)

    override fun onBindViewHolder(holder: GetLeaveByIdViewHolder, position: Int) {
        val getLeaveById = getItem(position)
        holder.bind(getLeaveById)

//        when {
//            holder.binding.statusCuti.text == Constants.STATUS_MENUNGGU_CODE -> {
//                holder.binding.statusCuti.text = Constants.STATUS_MENUNGGU
//                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_orange)
//            }
//            holder.binding.statusCuti.text == Constants.STATUS_DISETUJUI_CODE -> {
//                holder.binding.statusCuti.text = Constants.STATUS_DISETUJUI
//                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_blue)
//            }
//            else -> {
//                holder.binding.statusCuti.text = Constants.STATUS_DITOLAK
//                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_red)
//            }
//        }

        if (getLeaveById.managerAcc == Constants.STATUS_DISETUJUI_CODE) {
            holder.binding.statusCuti.text = Constants.STATUS_DISETUJUI
            holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_blue)
        } else {
            if (getLeaveById.kasiAcc == Constants.STATUS_DITOLAK_CODE || getLeaveById.kasubagAcc == Constants.STATUS_DITOLAK_CODE ||
                getLeaveById.managerAcc == Constants.STATUS_DITOLAK_CODE
            ) {
                holder.binding.statusCuti.text = Constants.STATUS_DITOLAK
                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_red)
            } else {
                holder.binding.statusCuti.text = Constants.STATUS_MENUNGGU
                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_orange)
            }
        }

        holder.binding.itemLayout.setOnClickListener {
            if (getLeaveById != null) {
                onClickListener.onClick(getLeaveById)
            }
        }

    }


    class GetLeaveByIdViewHolder(val binding: ItemLeaveBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getLeaveById: GetLeaveByIdModel) {
            binding.itemLeave = getLeaveById
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GetLeaveByIdViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLeaveBinding.inflate(layoutInflater, parent, false)
                return GetLeaveByIdViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getLeaveById: GetLeaveByIdModel) -> Unit) {
        fun onClick(getLeaveById: GetLeaveByIdModel) = clickListener(getLeaveById)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetLeaveByIdModel>() {

        override fun areItemsTheSame(oldItem: GetLeaveByIdModel, newItem: GetLeaveByIdModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetLeaveByIdModel, newItem: GetLeaveByIdModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

}