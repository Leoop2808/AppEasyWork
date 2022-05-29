package com.proy.easywork.presentation.principal.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentFormularioBinding

class FormularioFragment : Fragment() {

    private lateinit var binding: FragmentFormularioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFormularioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvents()
    }

    private fun setUpEvents() {
        binding.imgFlechaAtras.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

    }


}