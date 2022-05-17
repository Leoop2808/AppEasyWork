package com.proy.easywork.data.model.request

import java.io.Serializable

data class RQClave (val recoveryCode: String, val username: String, val newPassword: String): Serializable