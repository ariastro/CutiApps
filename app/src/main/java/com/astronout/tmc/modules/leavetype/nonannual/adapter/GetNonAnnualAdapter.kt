package com.astronout.tmc.modules.leavetype.nonannual.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.databinding.ItemGetNonAnnualBinding
import com.astronout.tmc.modules.leavetype.nonannual.model.GetNonAnnualModel

class GetNonAnnualAdapter(private val onClickListener: OnClickListener): ListAdapter<GetNonAnnualModel, GetNonAnnualAdapter.GetNonAnnualViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetNonAnnualViewHolder =
        GetNonAnnualViewHolder.from(parent)

    override fun onBindViewHolder(holder: GetNonAnnualViewHolder, position: Int) {
        val getNonAnnualModel = getItem(position)
        holder.bind(getNonAnnualModel)

        holder.binding.optionMenu.setOnClickListener {
            if (getNonAnnualModel != null) {
                onClickListener.onClick(getNonAnnualModel)
            }
        }

    }


    class GetNonAnnualViewHolder(val binding: ItemGetNonAnnualBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getNonAnnualModel: GetNonAnnualModel) {
            binding.getNonAnnualList = getNonAnnualModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GetNonAnnualViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGetNonAnnualBinding.inflate(layoutInflater, parent, false)
                return GetNonAnnualViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getNonAnnualModel: GetNonAnnualModel) -> Unit) {
        fun onClick(getNonAnnualModel: GetNonAnnualModel) = clickListener(getNonAnnualModel)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetNonAnnualModel>() {

        override fun areItemsTheSame(oldItem: GetNonAnnualModel, newItem: GetNonAnnualModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetNonAnnualModel, newItem: GetNonAnnualModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

}