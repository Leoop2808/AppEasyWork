package com.proy.easywork.presentation.loginTecnico.viewmodel

import androidx.lifecycle.*
import com.proy.easywork.data.exception.DefaultException
import com.proy.easywork.data.model.request.*
import com.proy.easywork.data.viewmodel.MAViewModel
import com.proy.easywork.domain.MADataResult
import com.proy.easywork.domain.repositories.LoginTecnicoRepository
import kotlinx.coroutines.launch

class LoginTecnicoViewModel (val repository: LoginTecnicoRepository): MAViewModel(){
    private val _login = MutableLiveData<Boolean?>()
    val login : LiveData<Boolean?> = _login

    private val _cargaMaestros = MutableLiveData<Boolean?>()
    val cargaMaestros : LiveData<Boolean?> = _cargaMaestros

    private val _registerCompletedFacebook= MutableLiveData<Boolean>()
    val registerCompletedFacebook : LiveData<Boolean> = _registerCompletedFacebook

    private val _validatePhone =  MutableLiveData<Boolean>()
    private val _completeProfile = MutableLiveData<Boolean>()

    private val _verifyPhone = MutableLiveData<Boolean>()
    val validatePhone : LiveData<Boolean> = _validatePhone
    val completeProfile : LiveData<Boolean> = _completeProfile

    private val _successRecoveryPassword = MutableLiveData<Boolean>()
    val successRecoveryPassword : LiveData<Boolean> = _successRecoveryPassword

    val verifyPhone : LiveData<Boolean> = _verifyPhone
    fun login(correo: String, password: String){
        _isViewLoading.value=true
        viewModelScope.launch {
            when (val result = repository.authenticate(correo, password, "2")) {
                is MADataResult.Success -> {
                    _login.value = true
                }
                is MADataResult.Failure -> {
                    _onMessageError.value = result.e.message.toString()
                }
                is MADataResult.ServerFailure->{
                    _onMessageError.value = result.e.message.toString()
                }
                is MADataResult.AccountFailure-> {
                    _onMessageError.value = DefaultException.DEFAULT_MESSAGE
                }
                else -> {
                    _onMessageError.value = DefaultException.DEFAULT_MESSAGE
                }
            }
            _isViewLoading.value=false
        }
    }

    fun loginGoogleTecnico(request : RQAuthenticationGoogle){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.authenticateGoogleTecnico(request)) {
                is MADataResult.Success -> {
                    when (result.data?.flgMostrarRegistroUsuario) {
                        "true" -> {
                            _completeProfile.value = true
                        }
                        "false" ->{
                            when (result.data?.flgCelularValidado){
                                "true" -> {
                                    _login.value = true
                                }
                                "false" -> {
                                    _validatePhone.value = true
                                }
                            }
                        }
                    }
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

    fun loginFacebookTecnico(request : RQAuthenticationFacebook){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.authenticateFacebookTecnico(request)) {
                is MADataResult.Success -> {
                    when (result.data?.flgMostrarRegistroUsuario) {
                        "true" -> {
                            _completeProfile.value = true
                        }
                        "false" ->{
                            when (result.data?.flgCelularValidado){
                                "true" -> {
                                    _login.value = true
                                }
                                "false" -> {
                                    _validatePhone.value = true
                                }
                            }
                        }
                    }
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

    fun enviarCodigoCelularTecnico(request : RQCodigoCelular){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.enviarCodigoCelularTecnico(request)) {
                is MADataResult.Success -> {
                    _onMessageSuccesful.value = result.data
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

    fun enviarCodigoCorreoTecnico(request : RQCodigoCorreo){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.enviarCodigoCorreoTecnico(request)) {
                is MADataResult.Success -> {
                    _onMessageSuccesful.value= result.data
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

    fun verificarCodigoCorreoTecnico(request : RQVerificarCodigo){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.verificarCodigoCorreoTecnico(request)) {
                is MADataResult.Success -> {
                    _onMessageSuccesful.value= result.data
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

    fun verificarCodigoCelularTecnico(request : RQVerificarCodigoCelular){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.verificarCodigoCelularTecnico(request)) {
                is MADataResult.Success -> {
                    _onMessageSuccesful.value= result.data
                }
                is MADataResult.Failure -> {
                    _onMessageError.value = "Código invalido"
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

    fun actualizarClaveTecnico(request : RQClave){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.actualizarClaveTecnico(request)) {
                is MADataResult.Success -> {
                    _successRecoveryPassword.value= true
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

    fun registrarDispositivo(request : RQDispositivo){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.registrarDispositivo(request)) {
                is MADataResult.Success -> {
                    _onMessageSuccesful.value= result.data
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

    fun listarMaestros(){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.listarMaestros()) {
                is MADataResult.Success -> {
                    _isViewLoading.value = false
                    _cargaMaestros.value= true
                }
                is MADataResult.Failure -> {
                    _isViewLoading.value = false
                    _onMessageError.value = result.e.message.toString()
                }
                is MADataResult.AccountFailure->{
                    _isViewLoading.value = false
                    _accountFailure.value = true
                }
                is MADataResult.AuthentificateFailure->{
                    _isViewLoading.value = false
                    _authFailure.value = true
                }
                is MADataResult.ServerFailure->{
                    _isViewLoading.value = false
                    _serverFailure.value = true
                }
            }
        }
    }

    fun enviarCodigoAutenticacionCelularTecnico(request : RQCodigoCelular){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.enviarCodigoAutenticacionCelularTecnico(request)) {
                is MADataResult.Success -> {
                    _onMessageSuccesful.value = result.data
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

    fun loginPhoneTecnico(request : RQAuthenticationPhone){
        _isViewLoading.value = true
        viewModelScope.launch {
            when (val result = repository.authenticatePhoneTecnico(request)) {
                is MADataResult.Success -> {
                    when (result.data?.flgMostrarRegistroUsuario) {
                        "true" -> {
                            _login.value = true
                        }
                        "false" ->{
                            when (result.data?.flgCelularValidado){
                                "true" -> {
                                    _login.value = true
                                }
                                "false" -> {
                                    _login.value = true
                                }
                            }
                        }
                    }
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
    class LoginTecnicoModelFactory(private val repository: LoginTecnicoRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginTecnicoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginTecnicoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}