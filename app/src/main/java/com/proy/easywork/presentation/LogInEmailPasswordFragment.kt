package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentLogInEmailPasswordBinding
import com.proy.easywork.databinding.FragmentLoginCodePhoneBinding

class LogInEmailPasswordFragment : Fragment() {

    private lateinit var binding: FragmentLogInEmailPasswordBinding

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

        binding.btnOlvidasteContra.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_logInEmailPasswordFragment_to_passwordRecoveryEmailFragment)
        }
    }

}