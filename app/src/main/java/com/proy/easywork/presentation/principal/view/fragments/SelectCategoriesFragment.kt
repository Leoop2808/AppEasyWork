package com.proy.easywork.presentation.principal.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.databinding.FragmentSelectCategoriesBinding
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.presentation.principal.view.adapter.CategoriaAdapter
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel
import com.proy.easywork.presentation.splash.SplashActivity
import okhttp3.internal.wait

class SelectCategoriesFragment : Fragment() {
    private var flgServicioEnProceso = false
    private var idServicioEnProceso = 0
    private lateinit var binding: FragmentSelectCategoriesBinding
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    private val viewModel by viewModels<PrincipalViewModel> {
        PrincipalViewModel.PrincipalModelFactory(PrincipalRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clienteValidarServicioEnProceso()
        viewModel.validServicioEnProceso.observe(viewLifecycleOwner){
            flgServicioEnProceso = it.flgServicioEnProceso
            idServicioEnProceso = it.idServicioEnProceso
        }
        if(flgServicioEnProceso){
            binding.ntServEnProceso.visibility = View.GONE
        }else{
            binding.ntServEnProceso.visibility = View.VISIBLE
        }
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
            it?.let {
                binding.rcvCateg.layoutManager=GridLayoutManager(context, 2)
                binding.rcvCateg.adapter = CategoriaAdapter(it){
                    if (flgServicioEnProceso){
                        showMessage("Usted ya cuenta con una solicitud de servicio en proceso")
                    }else{
                        val b = bundleOf(Pair("codCategoria",it.codCategoriaServicio))
                        Navigation.findNavController(requireView()).navigate(R.id.action_fragmentSelectCategories2_to_mapsFragment,b)
                    }

                }
                binding.rcvCateg.isNestedScrollingEnabled=false
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

        binding.ntServEnProceso.setOnClickListener {
            if (flgServicioEnProceso){
                val b = bundleOf(Pair("idServicioEnProceso",idServicioEnProceso))
                Navigation.findNavController(requireView()).navigate(R.id.action_fragmentSelectCategories2_to_visualizarSolicitudFragment2,b)
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