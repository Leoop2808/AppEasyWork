package com.proy.easywork.data.model.request

import java.io.Serializable

class AuthenticateResponse (val access_token :String,
                            val error :String,
                            val error_description :String): Serializable