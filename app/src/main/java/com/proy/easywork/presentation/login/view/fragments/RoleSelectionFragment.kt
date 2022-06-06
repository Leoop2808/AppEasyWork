package com.proy.easywork.presentation.login.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.databinding.FragmentRoleSelectionBinding
import com.proy.easywork.presentation.loginTecnico.view.activities.LoginTecnicoActivity
import com.proy.easywork.presentation.principal.view.activities.PrincipalActivity
import com.proy.easywork.presentation.tecnico.view.activities.TecnicoActivity

class RoleSelectionFragment : Fragment() {
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    private lateinit var binding: FragmentRoleSelectionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRoleSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCliente.setOnClickListener {
            sp.saveTok("3")
            Navigation.findNavController(it).navigate(R.id.action_roleSelectionFragment_to_loginFragment)
        }

        binding.btnTecnico.setOnClickListener {
            sp.saveTok("2")
            context?.let { it1 -> startActivity(LoginTecnicoActivity().newIntent(it1)) }
        }
    }

}