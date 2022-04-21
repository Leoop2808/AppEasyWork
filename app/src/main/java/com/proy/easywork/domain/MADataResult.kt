package com.proy.easywork.domain

sealed class MADataResult<out T>{
    data class Success<T>(val data: T?) : MADataResult<T>()
    data class UnAuthorized(val e:Exception):MADataResult<Nothing>()
    data class AuthentificateFailure(val e:Exception):MADataResult<Nothing>()
    data class Failure(val e:Exception):MADataResult<Nothing>()
    data class AccountFailure(val e:Exception):MADataResult<Nothing>()
    data class ServerFailure(val e:Exception):MADataResult<Nothing>()
    data class Cancellation(val message:String?):MADataResult<Nothing>()
    data class Warning(val e:Exception):MADataResult<Nothing>()
}