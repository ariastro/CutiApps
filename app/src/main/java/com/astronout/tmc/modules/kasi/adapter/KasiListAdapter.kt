package com.astronout.tmc.modules.kasi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.R
import com.astronout.tmc.databinding.ItemListKasiBinding
import com.astronout.tmc.modules.kasi.model.GetKasiListModel
import com.astronout.tmc.utils.Constants.STATUS_AKTIF
import com.astronout.tmc.utils.Constants.STATUS_AKTIF_CODE
import com.astronout.tmc.utils.Constants.STATUS_NON_AKTIF
import com.astronout.tmc.utils.Constants.USER_KASI

class KasiListAdapter(private val context: Context, private val onClickListener: OnClickListener): ListAdapter<GetKasiListModel, KasiListAdapter.KasiListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KasiListViewHolder =
        KasiListViewHolder.from(parent)

    override fun onBindViewHolder(holder: KasiListViewHolder, position: Int) {
        val getKasiListModel = getItem(position)
        holder.bind(getKasiListModel)

        holder.binding.optionMenu.setOnClickListener {
            if (getKasiListModel != null) {
                onClickListener.onClick(getKasiListModel)
            }
        }

        if (getKasiListModel.kasiJenis == USER_KASI) {
            holder.binding.kasiJabatan.text = "Kasi " + getKasiListModel.kasiJabatan
        } else {
            holder.binding.kasiJabatan.text = "Kasubag " + getKasiListModel.kasiJabatan
        }

        if (holder.binding.status.text == STATUS_AKTIF_CODE) {
            holder.binding.status.text = STATUS_AKTIF
            holder.binding.status.setTextColor(ContextCompat.getColor(context, R.color.greenText))
        } else {
            holder.binding.status.text = STATUS_NON_AKTIF
            holder.binding.status.setTextColor(ContextCompat.getColor(context, R.color.redText))
        }

    }

    class KasiListViewHolder(val binding: ItemListKasiBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getKasiListModel: GetKasiListModel) {
            binding.itemListKasi = getKasiListModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): KasiListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListKasiBinding.inflate(layoutInflater, parent, false)
                return KasiListViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getKasiListModel: GetKasiListModel) -> Unit) {
        fun onClick(getKasiListModel: GetKasiListModel) = clickListener(getKasiListModel)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetKasiListModel>() {

        override fun areItemsTheSame(oldItem: GetKasiListModel, newItem: GetKasiListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetKasiListModel, newItem: GetKasiListModel): Boolean {
            return oldItem.kasiId == newItem.kasiId
        }

    }

}