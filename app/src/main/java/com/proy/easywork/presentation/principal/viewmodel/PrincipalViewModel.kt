package com.proy.easywork.presentation.principal.viewmodel

import androidx.lifecycle.*
import com.proy.easywork.data.db.entity.CategoriaServicio
import com.proy.easywork.data.model.request.RQBusqueda
import com.proy.easywork.data.model.request.RQCodigoCelular
import com.proy.easywork.data.model.request.RQPerfil
import com.proy.easywork.data.model.response.VMBusqTecnico
import com.proy.easywork.data.model.response.VMPerfil
import com.proy.easywork.data.viewmodel.MAViewModel
import com.proy.easywork.domain.MADataResult
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class PrincipalViewModel (val repository: PrincipalRepository): MAViewModel(){
    private val _listaCategoria = MutableLiveData<List<CategoriaServicio>?>()
    val listaCategoria : LiveData<List<CategoriaServicio>?> = _listaCategoria

    private val _listaTecnicos = MutableLiveData<MutableList<VMBusqTecnico>?>()
    val listaTecnicos : LiveData<MutableList<VMBusqTecnico>?> = _listaTecnicos

    private val _perfilTecnico = MutableLiveData<VMPerfil>()
    val perfilTecnico : LiveData<VMPerfil> = _perfilTecnico

    fun listarCategorias(){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getCategorias()) {
                is MADataResult.Success -> {
                    _listaCategoria.value = result.data
                }
                is MADataResult.Failure -> {
                    _onMessageError.value = result.e.message.toString()
                }
                is MADataResult.AccountFailure->{
                    _accountFailure.value = true
                }
                is MADataResult.AuthentificateFailure->{
                    _authFailure.value = true
                }
                is MADataResult.ServerFailure->{
                    _serverFailure.value = true
                }
            }
            _isViewLoading.value = false
        }
    }


    fun buscarTecnicos(request: RQBusqueda){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.buscarTecnico(request)) {
                is MADataResult.Success -> {
                    _listaTecnicos.value = result.data?.data
                }
                is MADataResult.Failure -> {
                    _onMessageError.value = result.e.message.toString()
                }
                is MADataResult.AccountFailure->{
                    _accountFailure.value = true
                }
                is MADataResult.AuthentificateFailure->{
                    _authFailure.value = true
                }
                is MADataResult.ServerFailure->{
                    _serverFailure.value = true
                }
            }
            _isViewLoading.value = false
        }
    }

    fun getPerfilTecnico(request: RQPerfil){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getPerfilTecnico(request)) {
                is MADataResult.Success -> {
                    _perfilTecnico.value = result.data?.data
                }
                is MADataResult.Failure -> {
                    _onMessageError.value = result.e.message.toString()
                }
                is MADataResult.AccountFailure->{
                    _accountFailure.value = true
                }
                is MADataResult.AuthentificateFailure->{
                    _authFailure.value = true
                }
                is MADataResult.ServerFailure->{
                    _serverFailure.value = true
                }
            }
            _isViewLoading.value = false
        }
    }
    class PrincipalModelFactory(private val repository: PrincipalRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PrincipalViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PrincipalViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}