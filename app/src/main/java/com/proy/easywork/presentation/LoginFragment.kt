package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTelefono.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_create_account_loginFragment_to_loginCodePhoneFragment)
        }

        binding.btnLogIn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_log_in_loginFragment_to_logInEmailPasswordFragment)
        }

        binding.btnFb.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_loginCodePhoneFbGoogleFragment)
        }

        binding.btnGoogle.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_loginCodePhoneFbGoogleFragment2)
        }
    }

}