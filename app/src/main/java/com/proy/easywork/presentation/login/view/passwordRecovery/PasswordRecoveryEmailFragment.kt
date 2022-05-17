package com.proy.easywork.presentation.login.view.passwordRecovery

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
import com.proy.easywork.data.model.request.RQCodigoCorreo
import com.proy.easywork.databinding.FragmentPasswordRecoveryEmailBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel

class PasswordRecoveryEmailFragment : Fragment() {

    private lateinit var binding: FragmentPasswordRecoveryEmailBinding

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.LoginModelFactory(LoginRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentPasswordRecoveryEmailBinding.inflate(inflater, container, false)
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
            Navigation.findNavController(it).navigate(R.id.action_passwordRecoveryEmailFragment_to_loginCodePhoneFragment)
        }


        binding.btnEnviar.setOnClickListener {
            if(binding.etCorreo.text.isNullOrEmpty()){
                showMessage("Ingrese el correo")
            }else{
                viewModel.enviarCodigoCorreo(RQCodigoCorreo(binding.etCorreo.text.toString().trim()))
            }
        }
    }

    private fun setUpUI() {

        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){
            view?.let {
                val bundle = bundleOf(Pair("correo", binding.etCorreo.text.toString().trim()))
                Navigation.findNavController(it).navigate(R.id.action_passwordRecoveryEmailFragment_to_passwordRecoveryFragment,bundle)
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