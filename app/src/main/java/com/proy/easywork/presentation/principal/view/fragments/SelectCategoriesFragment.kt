package com.proy.easywork.presentation.principal.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proy.easywork.BuildConfig
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentLoginBinding
import com.proy.easywork.databinding.FragmentSelectCategoriesBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.presentation.login.view.fragments.LoginFragment
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel
import com.proy.easywork.presentation.principal.view.activities.PrincipalActivity
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel

class SelectCategoriesFragment : Fragment() {
    private lateinit var binding: FragmentSelectCategoriesBinding
    private val viewModel by viewModels<PrincipalViewModel> {
        PrincipalViewModel.PrincipalModelFactory(PrincipalRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectCategoriesBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_select_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUI()
        setUpEvents()
    }

    private fun setUpUI() {
        viewModel.onMessageError.observe(viewLifecycleOwner){
            it?.let {
                showMessage(it)
            }
        }

        viewModel.listarCategorias()
        viewModel.listaCategoria.observe(viewLifecycleOwner){

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

    private fun setUpEvents() {

    }

    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}