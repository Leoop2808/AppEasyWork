package com.proy.easywork.presentation.principal.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentElegirTecnicoBinding
import com.proy.easywork.presentation.principal.view.adapter.TecnicoAdapter

class ElegirTecnicoFragment : Fragment() {

    private lateinit var binding: FragmentElegirTecnicoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentElegirTecnicoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lista = mutableListOf<String>()
        lista.add("Nombre 1")
        lista.add("Nombre 2")
        lista.add("Nombre 3")
        lista.add("Nombre 4")
        lista.add("Nombre 5")
        lista.add("Nombre 6")
        lista.add("Nombre 7")
        binding.rcvTecnicos.layoutManager= LinearLayoutManager(context)
        binding.rcvTecnicos.adapter = TecnicoAdapter(lista){

            view?.let{view->
                Navigation.findNavController(view).navigate(R.id.action_elegirTecnicoFragment_to_perfilTecnicoFragment)
            }

        }
        binding.rcvTecnicos.isNestedScrollingEnabled=false
    }
}