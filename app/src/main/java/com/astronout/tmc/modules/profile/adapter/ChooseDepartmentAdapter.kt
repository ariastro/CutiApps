package com.astronout.tmc.modules.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.databinding.ItemGetDepartmentBinding
import com.astronout.tmc.modules.department.model.GetDepartmentModel
import com.astronout.tmc.utils.gone

class ChooseDepartmentAdapter(private val onClickListener: OnClickListener): ListAdapter<GetDepartmentModel, ChooseDepartmentAdapter.GetDepartmentViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetDepartmentViewHolder =
        GetDepartmentViewHolder.from(parent)

    override fun onBindViewHolder(holder: GetDepartmentViewHolder, position: Int) {
        val getDepartmentModel = getItem(position)
        holder.bind(getDepartmentModel)

        holder.binding.optionMenu.visibility = gone()

        holder.binding.content.setOnClickListener {
            if (getDepartmentModel != null) {
                onClickListener.onClick(getDepartmentModel)
            }
        }

    }


    class GetDepartmentViewHolder(val binding: ItemGetDepartmentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getDepartmentModel: GetDepartmentModel) {
            binding.getDepartmentList = getDepartmentModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GetDepartmentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGetDepartmentBinding.inflate(layoutInflater, parent, false)
                return GetDepartmentViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getDepartmentModel: GetDepartmentModel) -> Unit) {
        fun onClick(getDepartmentModel: GetDepartmentModel) = clickListener(getDepartmentModel)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetDepartmentModel>() {

        override fun areItemsTheSame(oldItem: GetDepartmentModel, newItem: GetDepartmentModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetDepartmentModel, newItem: GetDepartmentModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

}