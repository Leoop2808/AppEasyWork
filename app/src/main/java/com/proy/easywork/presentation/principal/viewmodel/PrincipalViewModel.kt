package com.proy.easywork.presentation.principal.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.proy.easywork.data.db.entity.CategoriaServicio
import com.proy.easywork.data.db.entity.Distrito
import com.proy.easywork.data.model.request.*
import com.proy.easywork.data.model.response.VMBusqTecnico
import com.proy.easywork.data.model.response.VMBusqTecnicoGeneral
import com.proy.easywork.data.model.response.VMPerfil
import com.proy.easywork.data.model.response.VMPerfilTecnico
import com.proy.easywork.data.viewmodel.MAViewModel
import com.proy.easywork.domain.MADataResult
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.domain.repositories.PrincipalRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class PrincipalViewModel (val repository: PrincipalRepository): MAViewModel(){
    private val _listaCategoria = MutableLiveData<List<CategoriaServicio>?>()
    val listaCategoria : LiveData<List<CategoriaServicio>?> = _listaCategoria

    private val _listaDistrito = MutableLiveData<List<Distrito>?>()
    val listaDistrito : LiveData<List<Distrito>?> = _listaDistrito

    private val _listaTecnicos = MutableLiveData<MutableList<VMBusqTecnicoGeneral>?>()
    val listaTecnicos : LiveData<MutableList<VMBusqTecnicoGeneral>?> = _listaTecnicos

    private val _perfilTecnico = MutableLiveData<VMPerfilTecnico>()
    val perfilTecnico : LiveData<VMPerfilTecnico> = _perfilTecnico

    private val _nombreCategoria = MutableLiveData<String>()
    val nombreCategoria : LiveData<String> = _nombreCategoria

    fun getCategoria(codCategoria : String){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getCategoria(codCategoria)) {
                is MADataResult.Success -> {
                    _nombreCategoria.value = result.data
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

    fun listarDistritos(){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getDistritos()) {
                is MADataResult.Success -> {
                    _listaDistrito.value = result.data
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

    fun buscarTecnicosGeneral(request: RQBuscarTecnicosGeneral){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.buscarTecnicoGeneral(request)) {
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

    fun buscarTecnicosFavoritos(request: RQBuscarTecnicosGeneral){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.buscarTecnicoFavoritos(request)) {
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

    fun getPerfilTecnico(request: RQObtenerPerfilTecnico){
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
    fun solicitarServicio(request: RQSolicitarServicio){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.solicitarServicio(request)) {
                is MADataResult.Success -> {
                    _onMessageSuccesful.value = "true"
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