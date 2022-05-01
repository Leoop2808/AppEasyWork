package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentLogInEmailPasswordBinding
import com.proy.easywork.databinding.FragmentLoginCodePhoneBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel

class LogInEmailPasswordFragment : Fragment() {

    private lateinit var binding: FragmentLogInEmailPasswordBinding

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.LoginModelFactory(LoginRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentLogInEmailPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBackArrow.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnRegistrate.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_logInEmailPasswordFragment_to_loginCodePhoneFragment)
        }

        binding.btnIniciarSesion.setOnClickListener {
            viewModel.login(binding.etCorreo.text.toString(), binding.edContrasenia.text.toString())
        }

        viewModel.login.observe(viewLifecycleOwner){
            Toast.makeText(context,"login", Toast.LENGTH_LONG).show()
        }
        binding.btnOlvidasteContra.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_logInEmailPasswordFragment_to_passwordRecoveryEmailFragment)
        }
    }

}