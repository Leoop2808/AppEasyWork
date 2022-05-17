package com.proy.easywork.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class MAViewModel : ViewModel() {

    protected val _onMessageSuccesful= MutableLiveData<String>()
    val onMessageSuccesful: LiveData<String> = _onMessageSuccesful

    protected val _onMessageError= MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError

    protected val _onMessageWarning= MutableLiveData<String>()
    val onMessageWarning: LiveData<String> = _onMessageWarning

    protected val _isEmptyList= MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    protected val _isViewLoading= MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    protected val _operationComplete = MutableLiveData<Boolean>()
    val operationComplete: LiveData<Boolean> = _operationComplete

    protected val _onLogout= MutableLiveData<String>()
    val onLogout: LiveData<String> = _onLogout

    protected val _serverFailure= MutableLiveData<Boolean>()
    val serverFailure: LiveData<Boolean> = _serverFailure
    protected val _authFailure= MutableLiveData<Boolean>()
    val authFailure: LiveData<Boolean> = _authFailure
    protected val _accountFailure= MutableLiveData<Boolean>()
    val accountFailure: LiveData<Boolean> = _accountFailure
}