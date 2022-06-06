package com.proy.easywork.presentation.loginTecnico.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.model.request.RQCodigoCelular
import com.proy.easywork.databinding.FragmentLoginCodePhoneAuthTecnicoBinding
import com.proy.easywork.domain.repositories.LoginTecnicoRepository
import com.proy.easywork.presentation.loginTecnico.viewmodel.LoginTecnicoViewModel

class LoginCodePhoneAuthTecnicoFragment : Fragment(){
    private lateinit var binding: FragmentLoginCodePhoneAuthTecnicoBinding

    private val viewModel by viewModels<LoginTecnicoViewModel> {
        LoginTecnicoViewModel.LoginTecnicoModelFactory(LoginTecnicoRepository(activity?.application!!))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentLoginCodePhoneAuthTecnicoBinding.inflate(inflater, container, false)
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

//        binding.btnIniciarSesion.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_loginCodePhoneFragment_to_logInEmailPasswordFragment)
//        }

        binding.btnCodWhatsApp.setOnClickListener {
            if(validate()){
                viewModel.enviarCodigoAutenticacionCelularTecnico(RQCodigoCelular(binding.etNum.text.toString().trim(),"WHATSAPP"))
            }
        }

        binding.btnCodSMS.setOnClickListener {
            if(validate()){
                viewModel.enviarCodigoAutenticacionCelularTecnico(RQCodigoCelular(binding.etNum.text.toString().trim(),"SMS"))
            }
        }
    }

    private fun validate():Boolean{
        if(binding.etNum.text.isNullOrEmpty()){
            showMessage("Ingrese número")
            return false
        }

        if(!binding.checkBox2.isChecked){
            showMessage("Debe aceptar recibir el código de verificación")
            return false
        }

        return true
    }

    private fun setUpUI() {
        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){
            view?.let {
                val bundle = bundleOf(Pair("celular", binding.etNum.text.toString().trim()))
                Navigation.findNavController(it).navigate(R.id.action_loginCodePhoneAuthTecnicoFragment_to_phoneVerificationCodeAuthTecnicoFragment,bundle)
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

    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}