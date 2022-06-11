package com.proy.easywork.presentation.tecnico.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.databinding.FragmentSelectCategoriesBinding
import com.proy.easywork.databinding.FragmentTecnicoBinding
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.domain.repositories.TecnicoRepository
import com.proy.easywork.presentation.principal.view.adapter.CategoriaAdapter
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel
import com.proy.easywork.presentation.splash.SplashActivity
import com.proy.easywork.presentation.tecnico.viewmodel.TecnicoViewModel


class TecnicoFragment : Fragment() {
    private lateinit var binding: FragmentTecnicoBinding
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    private val viewModel by viewModels<TecnicoViewModel> {
        TecnicoViewModel.TecnicoModelFactory(TecnicoRepository(activity?.application!!))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTecnicoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        capturarCheckBox()
        setUpUI()
        setUpEvents()
    }

    private fun capturarCheckBox() {
        if (binding.cbFiltroCategorias.isChecked()==true){
            Toast.makeText(context,"Filtro de categorías activado",Toast.LENGTH_SHORT).show()
        }

        if (binding.cbFiltroZonas.isChecked()==true){
            Toast.makeText(context,"Filtro de zonas activado",Toast.LENGTH_SHORT).show()
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
        binding.imgCerrarSesion.setOnClickListener {
            sp.clearSession()
            startActivity(SplashActivity().newIntent(requireContext()))
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