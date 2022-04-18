package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentLoginBinding
import com.proy.easywork.databinding.FragmentSuccessfulUserCreationBinding

class SuccessfulUserCreationFragment : Fragment() {

    private lateinit var binding: FragmentSuccessfulUserCreationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentSuccessfulUserCreationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinuar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_successfulUserCreationFragment_to_completeProfileFragment)
        }

    }

}