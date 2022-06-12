package com.proy.easywork.presentation.loginTecnico.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.model.request.RQClave
import com.proy.easywork.databinding.FragmentLoginNewPasswordRecoveryTecnicoBinding
import com.proy.easywork.domain.repositories.LoginTecnicoRepository
import com.proy.easywork.presentation.loginTecnico.viewmodel.LoginTecnicoViewModel

class LoginNewPasswordRecoveryTecnicoFragment : Fragment(){
    private lateinit var binding: FragmentLoginNewPasswordRecoveryTecnicoBinding

    private val viewModel by viewModels<LoginTecnicoViewModel> {
        LoginTecnicoViewModel.LoginTecnicoModelFactory(LoginTecnicoRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentLoginNewPasswordRecoveryTecnicoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpEvents()

    }
    private fun setUpEvents() {

        binding.imgBackArrow.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.btnContinuar.setOnClickListener {

            if(validateFileds()){
                viewModel.actualizarClaveTecnico(RQClave(arguments?.getString("codigo")?:"",arguments?.getString("correo")?:"", binding.etContrasena.text.toString().trim() ))
            }
        }


    }

    private fun setUpUI() {

        viewModel.successRecoveryPassword.observe(viewLifecycleOwner){
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_loginNewPasswordRecoveryTecnicoFragment_to_successfulPasswordRecoveryTecnicoFragment)
            }
        }

        viewModel.onMessageError.observe(viewLifecycleOwner){
            it?.let {
                showMessage(it)
            }
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