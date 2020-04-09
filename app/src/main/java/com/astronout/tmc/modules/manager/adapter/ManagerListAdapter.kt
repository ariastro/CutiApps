package com.astronout.tmc.modules.manager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.R
import com.astronout.tmc.databinding.ItemListManagerBinding
import com.astronout.tmc.modules.manager.model.GetManagerListModel
import com.astronout.tmc.utils.Constants.STATUS_AKTIF
import com.astronout.tmc.utils.Constants.STATUS_AKTIF_CODE
import com.astronout.tmc.utils.Constants.STATUS_NON_AKTIF

class ManagerListAdapter(private val context: Context, private val onClickListener: OnClickListener): ListAdapter<GetManagerListModel, ManagerListAdapter.ManagerListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerListViewHolder =
        ManagerListViewHolder.from(parent)

    override fun onBindViewHolder(holder: ManagerListViewHolder, position: Int) {
        val getManagerListModel = getItem(position)
        holder.bind(getManagerListModel)

        holder.binding.optionMenu.setOnClickListener {
            if (getManagerListModel != null) {
                onClickListener.onClick(getManagerListModel)
            }
        }

        if (holder.binding.status.text == STATUS_AKTIF_CODE) {
            holder.binding.status.text = STATUS_AKTIF
            holder.binding.status.setTextColor(ContextCompat.getColor(context, R.color.greenText))
        } else {
            holder.binding.status.text = STATUS_NON_AKTIF
            holder.binding.status.setTextColor(ContextCompat.getColor(context, R.color.redText))
        }

    }

    class ManagerListViewHolder(val binding: ItemListManagerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getManagerListModel: GetManagerListModel) {
            binding.itemListManager = getManagerListModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ManagerListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListManagerBinding.inflate(layoutInflater, parent, false)
                return ManagerListViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getManagerListModel: GetManagerListModel) -> Unit) {
        fun onClick(getManagerListModel: GetManagerListModel) = clickListener(getManagerListModel)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetManagerListModel>() {

        override fun areItemsTheSame(oldItem: GetManagerListModel, newItem: GetManagerListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetManagerListModel, newItem: GetManagerListModel): Boolean {
            return oldItem.managerId == newItem.managerId
        }

    }

}