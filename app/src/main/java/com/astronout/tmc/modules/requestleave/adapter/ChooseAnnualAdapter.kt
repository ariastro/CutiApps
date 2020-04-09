package com.astronout.tmc.modules.requestleave.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.databinding.ItemGetAnnualBinding
import com.astronout.tmc.modules.leavetype.annual.model.GetAnnualModel
import com.astronout.tmc.utils.gone

class ChooseAnnualAdapter(private val onClickListener: OnClickListener): ListAdapter<GetAnnualModel, ChooseAnnualAdapter.GetAnnualViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetAnnualViewHolder =
        GetAnnualViewHolder.from(parent)

    override fun onBindViewHolder(holder: GetAnnualViewHolder, position: Int) {
        val getAnnualModel = getItem(position)
        holder.bind(getAnnualModel)

        holder.binding.optionMenu.visibility = gone()

        holder.binding.content.setOnClickListener {
            if (getAnnualModel != null) {
                onClickListener.onClick(getAnnualModel)
            }
        }

    }


    class GetAnnualViewHolder(val binding: ItemGetAnnualBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getAnnualModel: GetAnnualModel) {
            binding.getAnnualList = getAnnualModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GetAnnualViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGetAnnualBinding.inflate(layoutInflater, parent, false)
                return GetAnnualViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getAnnualModel: GetAnnualModel) -> Unit) {
        fun onClick(getAnnualModel: GetAnnualModel) = clickListener(getAnnualModel)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetAnnualModel>() {

        override fun areItemsTheSame(oldItem: GetAnnualModel, newItem: GetAnnualModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetAnnualModel, newItem: GetAnnualModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

}