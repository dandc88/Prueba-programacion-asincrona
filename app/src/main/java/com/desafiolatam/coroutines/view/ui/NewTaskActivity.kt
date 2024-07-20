package com.desafiolatam.coroutines.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.desafiolatam.coroutines.data.TaskEntity
import com.desafiolatam.coroutines.databinding.ActivityNewTaskBinding
import com.desafiolatam.coroutines.view.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewTaskBinding
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (title.isNotEmpty()) {
                val newTask = TaskEntity(
                    id = 0,
                    title = title,
                    description = description
                )
                viewModel.addTask(newTask)
                finish()
            } else {
                Toast.makeText(this, "Titulo requerido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
