package com.proy.easywork.presentation.login.viewmodel

import androidx.lifecycle.*
import com.proy.easywork.data.exception.DefaultException
import com.proy.easywork.data.viewmodel.MAViewModel
import com.proy.easywork.domain.MADataResult
import com.proy.easywork.domain.repositories.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel (val repository: LoginRepository): MAViewModel () {

    private val _login = MutableLiveData<Boolean?>()
    val login : LiveData<Boolean?> = _login

    fun login(correo: String, password: String){
        _isViewLoading.value=true
        viewModelScope.launch {
            when (val result = repository.authenticate(correo, password)) {
                is MADataResult.Success -> {
                    _login.value=true
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
    class LoginModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}