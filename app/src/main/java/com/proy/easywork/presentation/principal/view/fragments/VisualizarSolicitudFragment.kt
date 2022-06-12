package com.proy.easywork.presentation.principal.view.fragments

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
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.model.request.RQClienteCancelarServicio
import com.proy.easywork.databinding.FragmentVisualizarSolicitudBinding
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel
import com.proy.easywork.presentation.util.widgets.AlertDialog
import com.squareup.picasso.Picasso

class VisualizarSolicitudFragment : Fragment() {
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    private lateinit var binding: FragmentVisualizarSolicitudBinding
    private val viewModel by viewModels<PrincipalViewModel> {
        PrincipalViewModel.PrincipalModelFactory(PrincipalRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVisualizarSolicitudBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpEvents()
        viewModel.getServicioEnProceso(arguments?.getInt("idServicioEnProceso")?:0)
        viewModel.servicioEnProceso.observe(viewLifecycleOwner){
            it?.let {
                Picasso.get().load(it.urlImagenUsuarioTecnico).into(binding.imgTecnico)
                binding.txtNombrePerfil.text=it.nombreUsuarioTecnico
                binding.txtCategoria.text=it.nombreCategoriaServicio
                binding.txtDescripcionProblema.text=it.descripcionProblema
                binding.txtNombreDistrito.text= it.nombreDistrito
                binding.txtnombreMedioPago.text=it.nombreMedioPago
                binding.txtDireccion.text=it.direccion
            }
        }
    }
    private fun setUpUI() {
        viewModel.servicioCancelado.observe(viewLifecycleOwner){
            showMessage("Solicitud de servicio cancelada")
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_visualizarSolicitudFragment_to_fragmentSelectCategories2)
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
        binding.btHome.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_visualizarSolicitudFragment_to_fragmentSelectCategories2)
        }
    }

    private fun setUpEvents() {
        binding.btnCancelar.setOnClickListener {
            AlertDialog().showMessage(requireContext(),"¿Está seguro de CANCELAR la solicitud de servicio?","Si","No",{
                viewModel.cancelarSolicitudServicio(RQClienteCancelarServicio(arguments?.getInt("idServicioEnProceso")?:0,""));
            },{})
        }

    }

    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}