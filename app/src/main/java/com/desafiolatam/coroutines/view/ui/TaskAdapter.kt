package com.desafiolatam.coroutines.view.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.desafiolatam.coroutines.data.TaskEntity
import com.desafiolatam.coroutines.databinding.ItemTaskBinding

class TaskAdapter :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private lateinit var binding: ItemTaskBinding
    lateinit var taskList: List<TaskEntity>

    var onLongClick: ((TaskEntity) -> Unit)? = null

    var onCheckChanged: ((TaskEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size

    inner class TaskViewHolder(binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun onBind(task: TaskEntity) {
            binding.run {
                tvTaskTitle.text = task.title
                tvTaskDescription.text = task.description
                cbIsCompleted.isChecked = task.isCompleted

                // Aplicar el estilo de texto tachado si la tarea está completa
                tvTaskTitle.paintFlags = if (task.isCompleted) {
                    tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }

                // Configurar el CheckBox y su listener
                cbIsCompleted.setOnCheckedChangeListener { _, isChecked ->
                    val updatedTask = task.copy(isCompleted = isChecked)
                    onCheckChanged?.invoke(updatedTask)
                }



                itemView.setOnLongClickListener {
                    onLongClick?.invoke(task)
                    true
                }

            }
        }
    }
}