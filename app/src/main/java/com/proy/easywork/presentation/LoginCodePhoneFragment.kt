package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentLoginBinding
import com.proy.easywork.databinding.FragmentLoginCodePhoneBinding

class LoginCodePhoneFragment : Fragment() {

    private lateinit var binding: FragmentLoginCodePhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentLoginCodePhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBackArrow.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnIniciarSesion.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginCodePhoneFragment_to_logInEmailPasswordFragment)
        }

        binding.btnCodWhatsApp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_btnWhatsApp_loginCodePhoneFragment_to_loginEmailFragment)
        }

        binding.btnCodSMS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_btnSMS_loginCodePhoneFragment_to_loginEmailFragment2)
        }

    }

}