package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentLoginNewPasswordRecoveryBinding
import com.proy.easywork.databinding.FragmentPasswordRecoveryBinding

class LoginNewPasswordRecoveryFragment : Fragment() {

    private lateinit var binding: FragmentLoginNewPasswordRecoveryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentLoginNewPasswordRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBackArrow.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnRegistrate.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginNewPasswordRecoveryFragment_to_loginCodePhoneFragment)
        }

        binding.btnContinuar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginNewPasswordRecoveryFragment_to_successfulPasswordRecoveryFragment)
        }
    }
}