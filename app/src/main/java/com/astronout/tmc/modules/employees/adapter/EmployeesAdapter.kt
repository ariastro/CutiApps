package com.astronout.tmc.modules.employees.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.astronout.tmc.databinding.ItemAllEployeesBinding
import com.astronout.tmc.modules.employees.model.GetAllEmployeesModel

class EmployeesAdapter(private val onClickListener: OnClickListener): ListAdapter<GetAllEmployeesModel, EmployeesAdapter.EmployeeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder =
        EmployeeViewHolder.from(parent)

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val getAllEmployeesModel = getItem(position)
        holder.bind(getAllEmployeesModel)

        holder.binding.optionMenu.setOnClickListener {
            if (getAllEmployeesModel != null) {
                onClickListener.onClick(getAllEmployeesModel)
            }
        }

    }

    class EmployeeViewHolder(val binding: ItemAllEployeesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(getAllEmployeesModel: GetAllEmployeesModel) {
            binding.itemAllEmployees = getAllEmployeesModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmployeeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAllEployeesBinding.inflate(layoutInflater, parent, false)
                return EmployeeViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (getAllEmployeesModel: GetAllEmployeesModel) -> Unit) {
        fun onClick(getAllEmployeesModel: GetAllEmployeesModel) = clickListener(getAllEmployeesModel)
    }

    private companion object DiffCallback: DiffUtil.ItemCallback<GetAllEmployeesModel>() {

        override fun areItemsTheSame(oldItem: GetAllEmployeesModel, newItem: GetAllEmployeesModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetAllEmployeesModel, newItem: GetAllEmployeesModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

}