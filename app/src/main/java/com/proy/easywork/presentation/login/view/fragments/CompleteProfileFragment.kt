package com.proy.easywork.presentation.login.view.fragments

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

        val listDoc = ArrayList<String>()
        listDoc.add(0, "Seleccionar")
        listDoc.add("DNI")
        listDoc.add("CE")
        listDoc.add("Pasaporte")
        binding.spinnerTipDoc.adapter = spinnerAdaptador(listDoc)

        binding.btnContinuar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_completeProfileFragment_to_createPasswordFragment)
        }
    }
}