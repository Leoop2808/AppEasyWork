package com.proy.easywork.presentation.loginTecnico.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentSuccessfulPasswordRecoveryTecnicoBinding

class SuccessfulPasswordRecoveryTecnicoFragment : Fragment() {
    private lateinit var binding: FragmentSuccessfulPasswordRecoveryTecnicoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessfulPasswordRecoveryTecnicoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgContinuar.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_successfulPasswordRecoveryTecnicoFragment_to_logInEmailPasswordTecnicoFragment)
            }
        }

    }
}