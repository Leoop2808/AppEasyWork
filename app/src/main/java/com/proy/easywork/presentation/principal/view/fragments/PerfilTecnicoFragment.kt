package com.proy.easywork.presentation.principal.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.model.request.RQBuscarTecnicosGeneral
import com.proy.easywork.data.model.request.RQObtenerPerfilTecnico
import com.proy.easywork.data.model.request.RQSolicitarServicio
import com.proy.easywork.databinding.FragmentPerfilTecnicoBinding
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.presentation.principal.view.activities.PrincipalActivity
import com.proy.easywork.presentation.principal.view.adapter.ComentarioAdapter
import com.proy.easywork.presentation.principal.view.adapter.TecnicoAdapter
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel
import com.proy.easywork.presentation.splash.SplashActivity
import com.proy.easywork.presentation.util.widgets.AlertDialog
import com.squareup.picasso.Picasso


class PerfilTecnicoFragment : Fragment() {
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    var idServicioSolicitado = 0
    private val viewModel by viewModels<PrincipalViewModel> {
        PrincipalViewModel.PrincipalModelFactory(PrincipalRepository(activity?.application!!))
    }
    private lateinit var binding: FragmentPerfilTecnicoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPerfilTecnicoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpEvents()
        viewModel.getPerfilTecnico(
            RQObtenerPerfilTecnico(arguments?.getInt("idTecnicoCategoriaServicio")?:0)
        )
    }

    private fun setUpUI() {
        viewModel.idServicioSolicitado.observe(viewLifecycleOwner){
            showMessage("Solicitud generada")
            view?.let {
                val b = bundleOf(Pair("idServicioEnProceso",viewModel.idServicioSolicitado.value))
                Navigation.findNavController(it).navigate(R.id.action_perfilTecnicoFragment_to_visualizarSolicitudFragment, b)
            }
        }

        viewModel.onMessageError.observe(viewLifecycleOwner){
            it?.let {
                showMessage(it)
            }
        }

        viewModel.perfilTecnico.observe(viewLifecycleOwner){
            it?.let {
                Picasso.get().load(it.urlImagenTecnico).into(binding.imgTecnico)
                binding.tvNombre.text=it.nombreTecnico
                binding.tvProfesion.text=it.categoria
                binding.tvCantClientes.text=it.cantidadClientes
                binding.tvEstrellaProm.text= it.datosValoracion.promedioEstrellas.toString()
                binding.tvResenas.text=it.cantidadResenias.toString()+"  reseñas"

                binding.tvCant1.text= it.datosValoracion.cantidad1Estrellas.toString()
                binding.tvCant2.text= it.datosValoracion.cantidad2Estrellas.toString()
                binding.tvCant3.text= it.datosValoracion.cantidad3Estrellas.toString()
                binding.tvCant4.text= it.datosValoracion.cantidad4Estrellas.toString()
                binding.tvCant5.text= it.datosValoracion.cantidad5Estrellas.toString()

                binding.progbar1.max=5
                binding.progbar2.max=5
                binding.progbar3.max=5
                binding.progbar4.max=5
                binding.progbar5.max=5
                binding.promValoracion.max=5

                binding.progbar1.progress=it.datosValoracion.cantidad1Estrellas
                binding.progbar2.progress=it.datosValoracion.cantidad2Estrellas
                binding.progbar3.progress=it.datosValoracion.cantidad3Estrellas
                binding.progbar4.progress=it.datosValoracion.cantidad4Estrellas
                binding.progbar5.progress=it.datosValoracion.cantidad5Estrellas

                binding.promValoracion.progress=it.datosValoracion.promedioEstrellas

                binding.rcvComentario.layoutManager= LinearLayoutManager(context)
                binding.rcvComentario.adapter = ComentarioAdapter(it.listaComentarios){


                }
                binding.rcvComentario.isNestedScrollingEnabled=false
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
        binding.btSolicitar.setOnClickListener {
            AlertDialog().showMessage(requireContext(),"¿Está seguro de solicitar el técnico?","Si", "No",{
                viewModel.solicitarServicio(
                    RQSolicitarServicio(
                        (arguments?.getInt("idTecnicoCategoriaServicio")?:0),
                        (arguments?.getString("codDistrito")?:""),
                        (arguments?.getString("codMedioPago")?:""),
                        (arguments?.getString("codCategoria")?:""),
                        (arguments?.getString("codTipoBusqueda")?:""),
                        (arguments?.getString("direccion")?:""),
                        (arguments?.getString("problema")?:""),
                        (arguments?.getDouble("latitud")?:0.0),
                        (arguments?.getDouble("longitud")?:0.0)
                    )
                )
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