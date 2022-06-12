package com.proy.easywork.presentation.tecnico.viewmodel

import androidx.lifecycle.*
import com.proy.easywork.data.model.request.RQBuscarTecnicosGeneral
import com.proy.easywork.data.model.response.VMDatosSolicitud
import com.proy.easywork.data.model.response.VMValidarServicioEnProceso
import com.proy.easywork.data.viewmodel.MAViewModel
import com.proy.easywork.domain.MADataResult
import com.proy.easywork.domain.repositories.TecnicoRepository
import kotlinx.coroutines.launch

class TecnicoViewModel (val repository: TecnicoRepository): MAViewModel(){
    private val _validServicioEnProceso = MutableLiveData<VMValidarServicioEnProceso>()
    val validServicioEnProceso : LiveData<VMValidarServicioEnProceso> = _validServicioEnProceso

    private val _initSolicitudes = MutableLiveData<VMDatosSolicitud>()
    val initSolicitudes : LiveData<VMDatosSolicitud> = _initSolicitudes

    fun tecnicoValidarServicioEnProceso(){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.tecnicoValidarServicioEnProceso()) {
                is MADataResult.Success -> {
                    _validServicioEnProceso.value = result.data?.data
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

    fun obtenerSolicitudes(){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.obtenerSolicitudes()) {
                is MADataResult.Success -> {
                    _initSolicitudes.value = result.data?.data
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