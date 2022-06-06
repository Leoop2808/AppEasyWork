package com.proy.easywork.presentation.tecnico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proy.easywork.data.viewmodel.MAViewModel
import com.proy.easywork.domain.repositories.TecnicoRepository

class TecnicoViewModel (val repository: TecnicoRepository): MAViewModel(){
    class TecnicoModelFactory(private val repository: TecnicoRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TecnicoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TecnicoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}