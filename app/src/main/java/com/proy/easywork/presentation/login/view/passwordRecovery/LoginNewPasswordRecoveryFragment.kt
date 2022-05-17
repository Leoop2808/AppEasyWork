package com.proy.easywork.presentation.login.view.passwordRecovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.model.request.RQClave
import com.proy.easywork.databinding.FragmentLoginNewPasswordRecoveryBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel

class LoginNewPasswordRecoveryFragment : Fragment() {

    private lateinit var binding: FragmentLoginNewPasswordRecoveryBinding

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.LoginModelFactory(LoginRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

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

            if(validateFileds()){
                viewModel.actualizarClave(RQClave(arguments?.getString("codigo")?:"",arguments?.getString("correo")?:"", binding.etContrasena.text.toString().trim() ))
            }
        }

        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){
            Navigation.findNavController(view).navigate(R.id.action_loginNewPasswordRecoveryFragment_to_successfulPasswordRecoveryFragment)
        }


        viewModel.onMessageError.observe(viewLifecycleOwner){
            it?.let { showMessage(it) }
        }

        viewModel.isViewLoading.observe(viewLifecycleOwner) {
            it.let {
                if (it) {
                    binding.pb.visibility = View.VISIBLE
                } else {
                    binding.pb.visibility = View.GONE
                }
            }
        }
    }

    private fun validateFileds(): Boolean{
        if(binding.etContrasena.text.isNullOrEmpty()){
            showMessage("Ingrese contraseña")
            return false
        }

        if(binding.etContrasena2.text.isNullOrEmpty()){
            showMessage("Ingrese la confirmación contraseña")
            return false
        }

        if(binding.etContrasena.text.toString().trim()!=binding.etContrasena2.text.toString().trim()){
            showMessage("Las contraseñas no coinciden")
            return false
        }


        return true
    }

    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}