package com.astronout.tmc.modules.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.R
import com.astronout.tmc.databinding.ItemListAdminBinding
import com.astronout.tmc.modules.admin.model.GetAdminListModel
import com.astronout.tmc.utils.Constants.STATUS_AKTIF
import com.astronout.tmc.utils.Constants.STATUS_AKTIF_CODE
import com.astronout.tmc.utils.Constants.STATUS_NON_AKTIF

class AdminListAdapter(private val context: Context, private val onClickListener: OnClickListener): ListAdapter<GetAdminListModel, AdminListAdapter.AdminListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminListViewHolder =
        AdminListViewHolder.from(parent)

    override fun onBindViewHolder(holder: AdminListViewHolder, position: Int) {
        val getadminList = getItem(position)
        holder.bind(getadminList)

        holder.binding.optionMenu.setOnClickListener {
            if (getadminList != null) {
                onClickListener.onClick(getadminList)
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

    class AdminListViewHolder(val binding: ItemListAdminBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getAdminListModel: GetAdminListModel) {
            binding.itemListAdmin = getAdminListModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AdminListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListAdminBinding.inflate(layoutInflater, parent, false)
                return AdminListViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getAdminListModel: GetAdminListModel) -> Unit) {
        fun onClick(getAdminListModel: GetAdminListModel) = clickListener(getAdminListModel)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetAdminListModel>() {

        override fun areItemsTheSame(oldItem: GetAdminListModel, newItem: GetAdminListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetAdminListModel, newItem: GetAdminListModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

}