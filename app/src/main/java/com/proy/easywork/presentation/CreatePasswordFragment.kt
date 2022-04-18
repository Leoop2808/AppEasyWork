package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentCreatePasswordBinding
import com.proy.easywork.databinding.FragmentLoginValidationCodeBinding

class CreatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentCreatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBackArrow.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnIniciarSesion.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_createPasswordFragment_to_logInEmailPasswordFragment2)
        }

        binding.btnContinuar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_createPasswordFragment_to_successfulUserCreationFragment)
        }

    }
}