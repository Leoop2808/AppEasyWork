package com.proy.easywork.presentation.principal.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.model.request.RQBuscarTecnicosGeneral
import com.proy.easywork.data.model.request.RQBusqueda
import com.proy.easywork.databinding.FragmentElegirTecnicoBinding
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.presentation.principal.view.adapter.CategoriaAdapter
import com.proy.easywork.presentation.principal.view.adapter.TecnicoAdapter
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel
import com.proy.easywork.presentation.splash.SplashActivity

class ElegirTecnicoFragment : Fragment() {

    private lateinit var binding: FragmentElegirTecnicoBinding
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref

    private val viewModel by viewModels<PrincipalViewModel> {
        PrincipalViewModel.PrincipalModelFactory(PrincipalRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentElegirTecnicoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvents()
        setUpUI()

        if (arguments?.getString("codTipoBusqueda")?:"" == "1"){
            viewModel.buscarTecnicosFavoritos(
                RQBuscarTecnicosGeneral(
                    (arguments?.getString("codCategoria")?:""),
                    (arguments?.getString("direccion")?:""),
                    (arguments?.getDouble("latitud")?:0.0),
                    (arguments?.getDouble("longitud")?:0.0),
                    "10",
//                    (arguments?.getString("codDistrito")?:""),
                    (arguments?.getString("problema")?:""),
                    (arguments?.getString("codMedioPago")?:"")
                )
            )
        }else{
            viewModel.buscarTecnicosGeneral(
                RQBuscarTecnicosGeneral(
                    (arguments?.getString("codCategoria")?:""),
                    (arguments?.getString("direccion")?:""),
                    (arguments?.getDouble("latitud")?:0.0),
                    (arguments?.getDouble("longitud")?:0.0),
                    "10",
//                    (arguments?.getString("codDistrito")?:""),
                    (arguments?.getString("problema")?:""),
                    (arguments?.getString("codMedioPago")?:"")
                )
            )
        }
    }

    private fun setUpUI() {

        viewModel.onMessageError.observe(viewLifecycleOwner){
            it?.let {
                showMessage(it)
            }
        }

        viewModel.listaTecnicos.observe(viewLifecycleOwner){
            it?.let{
                binding.rcvTecnicos.layoutManager= LinearLayoutManager(context)
                binding.rcvTecnicos.adapter = TecnicoAdapter(it){
                    view?.let{view->
                        Navigation.findNavController(view).navigate(R.id.action_elegirTecnicoFragment_to_perfilTecnicoFragment)
                    }

                }
                binding.rcvTecnicos.isNestedScrollingEnabled=false
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

    private fun setUpEvents() {

        binding.imgCerrarSesion.setOnClickListener {
            sp.clearSession()
            startActivity(SplashActivity().newIntent(requireContext()))
        }

    }

    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}