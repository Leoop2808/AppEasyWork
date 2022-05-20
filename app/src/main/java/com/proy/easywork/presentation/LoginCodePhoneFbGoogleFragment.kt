package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.model.request.RQAuthenticationGoogle
import com.proy.easywork.data.model.request.RQCodigoCelular
import com.proy.easywork.databinding.FragmentLoginCodePhoneFbGoogleBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel

class LoginCodePhoneFbGoogleFragment : Fragment() {

    private lateinit var binding: FragmentLoginCodePhoneFbGoogleBinding
    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.LoginModelFactory(LoginRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentLoginCodePhoneFbGoogleBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun validate():Boolean{
        if(binding.etNumGF.text.isNullOrEmpty()){
            showMessage("Ingrese número")
            return false
        }

        if(!binding.checkBox.isChecked){
            showMessage("Debe aceptar recibir el código de verificación")
            return false
        }

        return true
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

        binding.btnIniciarSesion.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginCodePhoneFbGoogleFragment_to_logInEmailPasswordFragment)
        }


        binding.btnCodWhatsApp.setOnClickListener {
            if(validate()){
                viewModel.enviarCodigoCelular(RQCodigoCelular(binding.etNumGF.text.toString().trim(),"WHATSAPP"))
            }
        }

        binding.btnCodSMS.setOnClickListener {
            if(validate()){
                viewModel.enviarCodigoCelular(RQCodigoCelular(binding.etNumGF.text.toString().trim(),"SMS"))
            }
        }
    }
    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUpUI() {

        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){
            view?.let {
                val bundle = bundleOf(Pair("celular", binding.etNumGF.text.toString().trim()))
                Navigation.findNavController(it).navigate(R.id.action_loginCodePhoneFbGoogleFragment_to_phoneVerificationCodeFbGoogleFragment2,bundle)
            }
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
}