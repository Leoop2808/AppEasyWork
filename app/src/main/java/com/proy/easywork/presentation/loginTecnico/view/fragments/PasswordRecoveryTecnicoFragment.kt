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
import com.proy.easywork.data.model.request.RQVerificarCodigo
import com.proy.easywork.databinding.FragmentPasswordRecoveryBinding
import com.proy.easywork.databinding.FragmentPasswordRecoveryTecnicoBinding
import com.proy.easywork.domain.repositories.LoginTecnicoRepository
import com.proy.easywork.presentation.loginTecnico.viewmodel.LoginTecnicoViewModel

class PasswordRecoveryTecnicoFragment : Fragment(){
    private lateinit var binding: FragmentPasswordRecoveryTecnicoBinding
    private val viewModel by viewModels<LoginTecnicoViewModel> {
        LoginTecnicoViewModel.LoginTecnicoModelFactory(LoginTecnicoRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentPasswordRecoveryTecnicoBinding.inflate(inflater, container, false)
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
            if(binding.etCodigo.text.isNullOrEmpty()){
                showMessage("Ingresar código")
            }else{
                viewModel.verificarCodigoCorreoTecnico(
                    RQVerificarCodigo(binding.etCodigo.text.toString().trim(),
                    arguments?.getString("correo")?:""
                )
                )
            }
        }
    }

    private fun setUpUI() {

        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){
            view?.let {
                val bundle = bundleOf(Pair("correo", arguments?.getString("correo")?:""), Pair("codigo",binding.etCodigo.text.toString().trim() ))
                Navigation.findNavController(it).navigate(R.id.action_passwordRecoveryTecnicoFragment_to_loginNewPasswordRecoveryTecnicoFragment, bundle)
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