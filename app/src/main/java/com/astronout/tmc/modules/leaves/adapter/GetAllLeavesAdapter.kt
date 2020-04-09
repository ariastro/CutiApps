package com.astronout.tmc.modules.leaves.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.R
import com.astronout.tmc.databinding.ItemAllLeavesBinding
import com.astronout.tmc.databinding.ItemLeaveBinding
import com.astronout.tmc.modules.leaves.model.GetAllLeavesModel
import com.astronout.tmc.modules.leaves.model.GetLeaveByIdModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.Constants.ANNUAL
import com.astronout.tmc.utils.Constants.ANNUAL_YES
import com.astronout.tmc.utils.Constants.NON_ANNUAL

class GetAllLeavesAdapter(val context: Context, private val onClickListener: OnClickListener): ListAdapter<GetAllLeavesModel, GetAllLeavesAdapter.GetAllLeavesViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetAllLeavesViewHolder =
        GetAllLeavesViewHolder.from(parent)

    override fun onBindViewHolder(holder: GetAllLeavesViewHolder, position: Int) {
        val getAllLeaves = getItem(position)
        holder.bind(getAllLeaves)

        when {
            holder.binding.statusCuti.text == Constants.STATUS_MENUNGGU_CODE -> {
                holder.binding.statusCuti.text = Constants.STATUS_MENUNGGU
                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_orange)
            }
            holder.binding.statusCuti.text == Constants.STATUS_DISETUJUI_CODE -> {
                holder.binding.statusCuti.text = Constants.STATUS_DISETUJUI
                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_blue)
            }
            else -> {
                holder.binding.statusCuti.text = Constants.STATUS_DITOLAK
                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_red)
            }
        }

        if (holder.binding.jenisCuti.text == ANNUAL_YES) {
            holder.binding.jenisCuti.text = ANNUAL
        } else {
            holder.binding.jenisCuti.text = NON_ANNUAL
        }

        holder.binding.itemLayout.setOnClickListener {
            if (getAllLeaves != null) {
                onClickListener.onClick(getAllLeaves)
            }
        }

    }


    class GetAllLeavesViewHolder(val binding: ItemAllLeavesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getAllLeavesModel: GetAllLeavesModel) {
            binding.itemAllLeaves = getAllLeavesModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GetAllLeavesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAllLeavesBinding.inflate(layoutInflater, parent, false)
                return GetAllLeavesViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getAllLeavesModel: GetAllLeavesModel) -> Unit) {
        fun onClick(getAllLeavesModel: GetAllLeavesModel) = clickListener(getAllLeavesModel)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetAllLeavesModel>() {

        override fun areItemsTheSame(oldItem: GetAllLeavesModel, newItem: GetAllLeavesModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetAllLeavesModel, newItem: GetAllLeavesModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

}