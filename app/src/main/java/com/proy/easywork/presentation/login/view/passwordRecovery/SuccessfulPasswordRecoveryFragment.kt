package com.proy.easywork.presentation.login.view.passwordRecovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentSuccessfulPasswordRecoveryBinding
import com.proy.easywork.databinding.FragmentSuccessfulUserCreationBinding
import com.proy.easywork.presentation.principal.view.activities.PrincipalActivity

class SuccessfulPasswordRecoveryFragment : Fragment() {
    private lateinit var binding: FragmentSuccessfulPasswordRecoveryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessfulPasswordRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgContinuar.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_successfulPasswordRecoveryFragment_to_logInEmailPasswordFragment)
            }
        }

    }
}