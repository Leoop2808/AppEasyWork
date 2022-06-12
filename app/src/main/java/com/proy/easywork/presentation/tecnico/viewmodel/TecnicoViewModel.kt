package com.proy.easywork.presentation.tecnico.viewmodel

import androidx.lifecycle.*
import com.proy.easywork.data.model.request.RQBuscarTecnicosGeneral
import com.proy.easywork.data.model.request.RQClienteCancelarServicio
import com.proy.easywork.data.model.request.RQObtenerSolicitudesGenerales
import com.proy.easywork.data.model.request.RQTecnicoCancelarServicio
import com.proy.easywork.data.model.response.*
import com.proy.easywork.data.viewmodel.MAViewModel
import com.proy.easywork.domain.MADataResult
import com.proy.easywork.domain.repositories.TecnicoRepository
import kotlinx.coroutines.launch

class TecnicoViewModel (val repository: TecnicoRepository): MAViewModel(){
    private val _validServicioEnProceso = MutableLiveData<VMValidarServicioEnProceso>()
    val validServicioEnProceso : LiveData<VMValidarServicioEnProceso> = _validServicioEnProceso

    private val _initSolicitudes = MutableLiveData<VMDatosSolicitud>()
    val initSolicitudes : LiveData<VMDatosSolicitud> = _initSolicitudes

    private val _servicioEnProceso = MutableLiveData<VMTecnicoServicioEnProceso>()
    val servicioEnProceso : LiveData<VMTecnicoServicioEnProceso> = _servicioEnProceso

    private val _listaSolicitudes = MutableLiveData<MutableList<VMSolicitudGeneral>?>()
    val listaSolicitudes : LiveData<MutableList<VMSolicitudGeneral>?> = _listaSolicitudes

    private val _servicioCancelado  = MutableLiveData<String>()
    val servicioCancelado : LiveData<String> = _servicioCancelado

    private val _servicioFinalizado  = MutableLiveData<String>()
    val servicioFinalizado : LiveData<String> = _servicioFinalizado

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

    fun getServicioEnProceso(idServicioEnProceso: Int){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getServicioEnProceso(idServicioEnProceso)) {
                is MADataResult.Success -> {
                    _servicioEnProceso.value = result.data?.data
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

    fun getSolicitudesDirectas(request: RQObtenerSolicitudesGenerales){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getSolicitudesDirectas(request)) {
                is MADataResult.Success -> {
                    _listaSolicitudes.value = result.data?.data
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

    fun getSolicitudesGenerales(request: RQObtenerSolicitudesGenerales){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getSolicitudesGenerales(request)) {
                is MADataResult.Success -> {
                    _listaSolicitudes.value = result.data?.data
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

    fun aceptarSolicitudServicio(idSolicitud:Int){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.aceptarSolicitudServicio(idSolicitud)) {
                is MADataResult.Success -> {
                    _onMessageSuccesful.value = "1"
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

    fun cancelarSolicitudServicio(request: RQTecnicoCancelarServicio){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.cancelarSolicitudServicio(request)) {
                is MADataResult.Success -> {
                    _servicioCancelado.value = "1"
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

    fun finalizarSolicitudServicio(request: RQTecnicoCancelarServicio){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.finalizarSolicitudServicio(request)) {
                is MADataResult.Success -> {
                    _servicioFinalizado.value = "1"
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