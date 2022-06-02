package com.proy.easywork.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentCompleteProfileBinding
import com.proy.easywork.databinding.FragmentCreatePasswordBinding

class CompleteProfileFragment : Fragment() {

    private lateinit var binding: FragmentCompleteProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCompleteProfileBinding.inflate(inflater, container, false)
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

        //Spinner Documento
        val listDoc = ArrayList<String>()
        listDoc.add(0, "Seleccionar")
        listDoc.add("DNI")
        listDoc.add("CE")
        listDoc.add("Pasaporte")
        binding.spinnerTipDoc.adapter = spinnerAdaptador(listDoc)

        //Spinner Distrito
        val listDistrito = ArrayList<String>()
        listDistrito.add(0, "Seleccionar")
        listDistrito.add("Ancón")
        listDistrito.add("Ate Vitarte")
        listDistrito.add("Barranco")
        listDistrito.add("Breña")
        listDistrito.add("Carabayllo")
        listDistrito.add("Cercado")
        listDistrito.add("Chorrillos")
        listDistrito.add("Cieneguilla")
        listDistrito.add("Comas")
        listDistrito.add("El Agustino")
        listDistrito.add("Independencia")
        listDistrito.add("Jesús María")
        listDistrito.add("La Molina")
        listDistrito.add("La Victoria")
        listDistrito.add("Lince")
        listDistrito.add("Los Olivos")
        listDistrito.add("Lurigancho")
        listDistrito.add("Lurín")
        listDistrito.add("Magdalena")
        listDistrito.add("Miraflores")
        listDistrito.add("Pachacamac")
        listDistrito.add("Pblo. Libre")
        listDistrito.add("Pta. Hermosa")
        listDistrito.add("Pta. Negra")
        listDistrito.add("Pte. Piedra")
        listDistrito.add("Pucusana")
        listDistrito.add("Rimac")
        listDistrito.add("S.J.L.")
        listDistrito.add("S.J.M.")
        listDistrito.add("S.M.P.")
        listDistrito.add("San Bartolo")
        listDistrito.add("San Borja")
        listDistrito.add("San Isidro")
        listDistrito.add("San Luis")
        listDistrito.add("San Miguel")
        listDistrito.add("Santa Rosa")
        listDistrito.add("Sta. Anita")
        listDistrito.add("Sta. María")
        listDistrito.add("Surco")
        listDistrito.add("Surquillo")
        listDistrito.add("V.M.T.")
        listDistrito.add("Villa El Salvador")
        binding.spinnerDistrito.adapter = spinnerAdaptador(listDistrito)


        binding.btnContinuar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_completeProfileFragment_to_fragmentSelectCategories)
        }
    }
}