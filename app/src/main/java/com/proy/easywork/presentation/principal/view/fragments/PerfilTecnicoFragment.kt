package com.proy.easywork.presentation.principal.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentPerfilTecnicoBinding
import com.proy.easywork.presentation.principal.view.adapter.ComentarioAdapter
import com.proy.easywork.presentation.principal.view.adapter.TecnicoAdapter


class PerfilTecnicoFragment : Fragment() {

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

        val lista = mutableListOf<String>()
        lista.add("Por Juan Vladez el 25/03/2022")
        lista.add("Por Juan Vladez el 25/03/2022")
        lista.add("Por Juan Vladez el 25/03/2022")
        lista.add("Por Juan Vladez el 25/03/2022")
        lista.add("Por Juan Vladez el 25/03/2022")
        lista.add("Por Juan Vladez el 25/03/2022")

        binding.rcvComentario.layoutManager= LinearLayoutManager(context)
        binding.rcvComentario.adapter = ComentarioAdapter(lista){


        }
        binding.rcvComentario.isNestedScrollingEnabled=false
    }
}