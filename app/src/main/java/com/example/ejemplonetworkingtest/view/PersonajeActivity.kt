package com.example.ejemplonetworkingtest.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejemplonetworkingtest.databinding.ActivityPersonajesBinding
import com.example.ejemplonetworkingtest.di.Injection
import com.example.ejemplonetworkingtest.model.PersonajeModel
import com.example.ejemplonetworkingtest.viewmodel.PersonajeViewModel

private const val TAG = "CONSOLE"
class PersonajeActivity : AppCompatActivity() {

    private val viewModel by viewModels<PersonajeViewModel> {
        Injection.provideViewModelFactory()
    }
    private lateinit var adapter: PersonajeAdapter

    private lateinit var binding: ActivityPersonajesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonajesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        adapter = PersonajeAdapter(viewModel.personaje.value ?: emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel.personaje.observe(this, renderPersonajes)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    private val renderPersonajes = Observer<List<PersonajeModel>> {
        Log.v(TAG, "data updated $it")
        binding.layoutError.root.visibility = View.GONE
        binding.layoutEmpty.root.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        binding.progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        binding.layoutError.root.visibility = View.VISIBLE
        binding.layoutEmpty.root.visibility = View.GONE
        binding.layoutError.textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        binding.layoutEmpty.root.visibility = View.VISIBLE
        binding.layoutError.root.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPersonajesCallback()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }
}
