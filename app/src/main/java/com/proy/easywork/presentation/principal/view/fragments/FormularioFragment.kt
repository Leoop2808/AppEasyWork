package com.proy.easywork.presentation.principal.view.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.data.db.entity.Distrito
import com.proy.easywork.data.model.request.RQAuthenticationPhone
import com.proy.easywork.data.model.request.RQBuscarTecnicosGeneral
import com.proy.easywork.databinding.FragmentFormularioBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel
import com.proy.easywork.presentation.principal.view.adapter.TecnicoAdapter
import com.proy.easywork.presentation.principal.viewmodel.PrincipalViewModel

class FormularioFragment : Fragment() {
    var listaDistrito = mutableListOf<Distrito>()
    private val viewModel by viewModels<PrincipalViewModel> {
        PrincipalViewModel.PrincipalModelFactory(PrincipalRepository(activity?.application!!))
    }
    private lateinit var binding: FragmentFormularioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFormularioBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun spinnerAdaptador(list: List<String>): ArrayAdapter<String>? {
        val adapterP: ArrayAdapter<String> =
            object : ArrayAdapter<String>(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                list
            ) {
                @SuppressLint("ResourceAsColor")
                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent) as TextView
                    view.setTextColor(Color.parseColor("#8C8C8C"))
                    //view.setTypeface(view.typeface, Typeface.BOLD)
                    return view
                }

                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }
            }
        adapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapterP
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvents()
        viewModel.getCategoria(arguments?.getString("codCategoria")?:"")
        viewModel.nombreCategoria.observe(viewLifecycleOwner){
            binding.etCategoria.setText(it)
            viewModel.listarDistritos()
        }
        binding.etDireccion.setText(arguments?.getString("direccion")?:"")
        viewModel.listaDistrito.observe(viewLifecycleOwner){
           it?.let {
               listaDistrito = it as MutableList<Distrito>
               val listDistrito = ArrayList<String>()
               listDistrito.add(0, "Seleccionar")
               it.forEach {
                   listDistrito.add(it.siglaDistrito)
               }
               binding.spinnerDistrito.adapter = spinnerAdaptador(listDistrito)
           }
        }
    }

    private fun setUpEvents() {
        binding.imgFlechaAtras.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.btnContinuar.setOnClickListener {
            if(validateField()){
                var medioPago = "1"
                if (binding.rbYape.isChecked()){
                    medioPago = "1"
                }else if (binding.rbPlin.isChecked()){
                    medioPago = "2"
                }else if (binding.rbEfectivo.isChecked()){
                    medioPago = "3"
                }
                var tipoBusqueda = "1"
                if (binding.rbTuZona.isChecked()){
                    tipoBusqueda = "2"
                }else if (binding.rbTuFavorito.isChecked()){
                    tipoBusqueda = "1"
                }

               var codDistrito = "10"
               val indexDistrito =listaDistrito.indexOfFirst { itz -> itz.id == binding.spinnerDistrito.selectedItemPosition - 1}
                listaDistrito.forEach{
                    if(it.id == indexDistrito)
                    {
                        codDistrito = it.codDistrito
                    }
                }
                val b = bundleOf(
                    Pair("direccion", arguments?.getString("direccion")?:""),
                    Pair("latitud",arguments?.getDouble("latitud")?:0.0),
                    Pair("longitud",arguments?.getDouble("longitud")?:0.0),
                    Pair("codCategoria", arguments?.getString("codCategoria")),
                    Pair("codDistrito", codDistrito),
                    Pair("problema", binding.etDescProblema.text.toString()),
                    Pair("codMedioPago", medioPago),
                    Pair("codTipoBusqueda", tipoBusqueda))

                Navigation.findNavController(it).navigate(R.id.action_formularioFragment_to_elegirTecnicoFragment,b)
            }
        }
    }

    private fun validateField():Boolean{
        if (binding.spinnerDistrito.selectedItemPosition == 0)
        {
            showMessage("Seleccione un distrito")
            return false
        }
        if (!binding.rbYape.isChecked && !binding.rbPlin.isChecked && !binding.rbEfectivo.isChecked ){
            showMessage("Seleccione un método de pago")
            return false
        }

        if (!binding.rbTuZona.isChecked && !binding.rbTuFavorito.isChecked){
            showMessage("Seleccione un tipo de búsqueda")
            return false
        }

        return true
    }
    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}