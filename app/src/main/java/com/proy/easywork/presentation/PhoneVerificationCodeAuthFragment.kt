package com.proy.easywork.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.model.request.RQAuthenticationPhone
import com.proy.easywork.data.model.request.RQVerificarCodigoCelular
import com.proy.easywork.databinding.FragmentPhoneVerificationCodeAuthBinding
import com.proy.easywork.databinding.FragmentPhoneVerificationCodeBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel

class PhoneVerificationCodeAuthFragment : Fragment() {

    private lateinit var binding: FragmentPhoneVerificationCodeAuthBinding
    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.LoginModelFactory(LoginRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentPhoneVerificationCodeAuthBinding.inflate(inflater, container, false)
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

        binding.btnIniciarSesion.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_phoneVerificationCodeFragment_to_logInEmailPasswordFragment)
        }

        binding.btnContinuarAuth.setOnClickListener {
            if(binding.etCodigoCelularAuth.text.isNullOrEmpty()){
                showMessage("Ingresar código")
            }else{
                viewModel.loginPhone(
                    RQAuthenticationPhone(arguments?.getString("celular")?:"", binding.etCodigoCelularAuth.text.toString().trim(), 0.0, 0.0)
                )
            }
        }
    }

    private fun setUpUI() {

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