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
import com.proy.easywork.data.model.request.RQVerificarCodigo
import com.proy.easywork.databinding.FragmentPasswordRecoveryBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel

class PasswordRecoveryFragment : Fragment() {

    private lateinit var binding: FragmentPasswordRecoveryBinding
    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.LoginModelFactory(LoginRepository(activity?.application!!))
    }
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

         binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
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

        binding.btnRegistrate.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_passwordRecoveryFragment_to_loginCodePhoneFragment2)
        }

        binding.btnContinuar.setOnClickListener {
            if(binding.etCodigo.text.isNullOrEmpty()){
                showMessage("Ingresar código")
            }else{
                viewModel.verificarCodigoCorreo(RQVerificarCodigo(binding.etCodigo.text.toString().trim(),
                    arguments?.getString("correo")?:""
                ))
            }

        }
    }

    private fun setUpUI() {

        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){
            view?.let {
                val bundle = bundleOf(Pair("correo", arguments?.getString("correo")?:""), Pair("codigo",binding.etCodigo.text.toString().trim() ))
                Navigation.findNavController(it).navigate(R.id.action_passwordRecoveryFragment_to_loginNewPasswordRecoveryFragment2, bundle)
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

    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}