package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentLoginEmailBinding
import com.proy.easywork.databinding.FragmentLoginValidationCodeBinding

class LoginValidationCodeFragment : Fragment() {

    private lateinit var binding: FragmentLoginValidationCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentLoginValidationCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBackArrow.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnIniciarSesion.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginValidationCodeFragment_to_logInEmailPasswordFragment)
        }

        binding.btnContinuar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginValidationCodeFragment_to_createPasswordFragment)
        }

    }
}