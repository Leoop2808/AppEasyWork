package com.proy.easywork.presentation.tecnico.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.databinding.FragmentVisualizarSolicitudBinding
import com.proy.easywork.databinding.FragmentVisualizarSolicitudTecnicoBinding
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.domain.repositories.TecnicoRepository
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel
import com.proy.easywork.presentation.tecnico.viewmodel.TecnicoViewModel
import com.proy.easywork.presentation.util.widgets.AlertDialog
import com.squareup.picasso.Picasso

class VisualizarSolicitudTecnicoFragment : Fragment() {
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    private lateinit var binding: FragmentVisualizarSolicitudTecnicoBinding
    private val viewModel by viewModels<TecnicoViewModel> {
        TecnicoViewModel.TecnicoModelFactory(TecnicoRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVisualizarSolicitudTecnicoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpEvents()
        viewModel.getServicioEnProceso(arguments?.getInt("idServicioEnProceso")?:0)
        viewModel.servicioEnProceso.observe(viewLifecycleOwner){
            it?.let {
                when(it.codCategoriaServicio){
                    "1"-> Picasso.get().load(R.drawable.electricidad).into(binding.imgCategoria)
                    "2"-> Picasso.get().load(R.drawable.carpinteria).into(binding.imgCategoria)
                    "3"-> Picasso.get().load(R.drawable.pintura).into(binding.imgCategoria)
                    "5"-> Picasso.get().load(R.drawable.alba_ileria).into(binding.imgCategoria)
                    "6"-> Picasso.get().load(R.drawable.gasfiteria).into(binding.imgCategoria)
                    "7"-> Picasso.get().load(R.drawable.limpieza).into(binding.imgCategoria)
                }

                binding.txtNombrePerfil.text=it.nombreUsuarioCliente
                binding.txtnombreCategoriaServicio.text=it.nombreCategoriaServicio
                binding.txtDescripcionProblema.text=it.descripcionProblema
                binding.txtnombreDistrito.text= it.nombreDistrito
                binding.txtnombreMedioPago.text=it.nombreMedioPago
                binding.txtDireccion.text=it.direccion
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
        binding.btHome.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_visualizarSolicitudTecnicoFragment_to_tecnicoFragment)
        }
    }

    private fun setUpEvents() {
        binding.btnCancelar.setOnClickListener {
            AlertDialog().showMessage(requireContext(),"¿Está seguro de CANCELAR la solicitud de servicio?","Si, Cancelar"){}
        }
        binding.btnFinalizar.setOnClickListener {
            AlertDialog().showMessage(requireContext(),"¿Está seguro de FINALIZAR la solicitud de servicio?","Si, Finalizar"){}
        }
    }

    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}