package com.proy.easywork.presentation.tecnico.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.model.request.RQBuscarTecnicosGeneral
import com.proy.easywork.data.model.request.RQObtenerSolicitudesGenerales
import com.proy.easywork.databinding.FragmentElegirTecnicoBinding
import com.proy.easywork.databinding.FragmentVisualizarSolicitudesBinding
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.domain.repositories.TecnicoRepository
import com.proy.easywork.presentation.principal.view.adapter.TecnicoAdapter
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel
import com.proy.easywork.presentation.splash.SplashActivity
import com.proy.easywork.presentation.tecnico.view.adapter.SolicitudAdapter
import com.proy.easywork.presentation.tecnico.viewmodel.TecnicoViewModel
import com.proy.easywork.presentation.util.widgets.AlertDialog

class VisualizarSolicitudesFragment : Fragment() {
    private lateinit var binding: FragmentVisualizarSolicitudesBinding
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    var idServicio = 0
    private val viewModel by viewModels<TecnicoViewModel> {
        TecnicoViewModel.TecnicoModelFactory(TecnicoRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVisualizarSolicitudesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvents()
        setUpUI()

        if (arguments?.getString("codTipoBusqueda")?:"" == "1"){
            viewModel.getSolicitudesDirectas(
                RQObtenerSolicitudesGenerales(
                    (arguments?.getBoolean("flgOrderByCategoria")?:false),
                    (arguments?.getBoolean("flgOrderByZona")?:false)
                )
            )
        }else{
            viewModel.getSolicitudesGenerales(
                RQObtenerSolicitudesGenerales(
                    (arguments?.getBoolean("flgOrderByCategoria")?:false),
                    (arguments?.getBoolean("flgOrderByZona")?:false)
                )
            )
        }
    }

    private fun setUpUI() {
        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){
            view?.let  {
                val b = bundleOf(Pair("idServicioEnProceso", idServicio?:""))
                Navigation.findNavController(it).navigate(R.id.action_visualizarSolicitudesFragment_to_visualizarSolicitudTecnicoFragment, b)
            }
        }

        viewModel.onMessageError.observe(viewLifecycleOwner){
            it?.let {
                showMessage(it)
            }
        }

        viewModel.listaSolicitudes.observe(viewLifecycleOwner){
            it?.let{
                binding.rcvTecnicos.layoutManager= LinearLayoutManager(context)
                binding.rcvTecnicos.adapter = SolicitudAdapter(it){
                    view?.let{view->
                        AlertDialog().showMessage(requireContext(),"¿Está seguro de ACEPTAR la solicitud de servicio?","Si, Aceptar")
                        {
                            idServicio = it.idServicio
                            viewModel.aceptarSolicitudServicio(it.idServicio)
                        }
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