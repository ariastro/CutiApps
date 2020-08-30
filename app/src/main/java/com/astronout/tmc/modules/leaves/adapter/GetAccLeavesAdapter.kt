package com.astronout.tmc.modules.leaves.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.R
import com.astronout.tmc.databinding.ItemAccLeavesBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.leaves.model.GetAccLeavesModel
import com.astronout.tmc.utils.Constants.STATUS_DISETUJUI
import com.astronout.tmc.utils.Constants.STATUS_DISETUJUI_CODE
import com.astronout.tmc.utils.Constants.STATUS_DITOLAK
import com.astronout.tmc.utils.Constants.STATUS_DITOLAK_CODE
import com.astronout.tmc.utils.Constants.STATUS_MENUNGGU
import com.astronout.tmc.utils.Constants.STATUS_MENUNGGU_CODE
import com.astronout.tmc.utils.Constants.USER_MANAGER
import com.astronout.tmc.utils.gone
import com.astronout.tmc.utils.visible

class GetAccLeavesAdapter(val context: Context, private val onClickListener: OnClickListener) :
    ListAdapter<GetAccLeavesModel, GetAccLeavesAdapter.GetAccLeavesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetAccLeavesViewHolder =
        GetAccLeavesViewHolder.from(parent)

    override fun onBindViewHolder(holder: GetAccLeavesViewHolder, position: Int) {
        val getAccLeaves = getItem(position)
        holder.bind(getAccLeaves)

        if (User.userType == USER_MANAGER) {
            holder.binding.nomorCuti.visibility = visible()
        } else {
            holder.binding.nomorCuti.visibility = gone()
        }

        if (getAccLeaves.managerAcc == STATUS_DISETUJUI_CODE) {
            holder.binding.statusCuti.text = STATUS_DISETUJUI
            holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_blue)
        } else {
            if (getAccLeaves.kasiAcc == STATUS_DITOLAK_CODE || getAccLeaves.kasubagAcc == STATUS_DITOLAK_CODE ||
                getAccLeaves.managerAcc == STATUS_DITOLAK_CODE) {
                holder.binding.statusCuti.text = STATUS_DITOLAK
                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_red)
            } else {
                holder.binding.statusCuti.text = STATUS_MENUNGGU
                holder.binding.statusCuti.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_orange)
            }
        }

        holder.binding.itemLayout.setOnClickListener {
            if (getAccLeaves != null) {
                onClickListener.onClick(getAccLeaves)
            }
        }

    }

    class GetAccLeavesViewHolder(val binding: ItemAccLeavesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(getAccLeavesModel: GetAccLeavesModel) {
            binding.itemAccLeaves = getAccLeavesModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GetAccLeavesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAccLeavesBinding.inflate(layoutInflater, parent, false)
                return GetAccLeavesViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getAccLeavesModel: GetAccLeavesModel) -> Unit) {
        fun onClick(getAccLeavesModel: GetAccLeavesModel) = clickListener(getAccLeavesModel)
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<GetAccLeavesModel>() {

        override fun areItemsTheSame(oldItem: GetAccLeavesModel, newItem: GetAccLeavesModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetAccLeavesModel, newItem: GetAccLeavesModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

}