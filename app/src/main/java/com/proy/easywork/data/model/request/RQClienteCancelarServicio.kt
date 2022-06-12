package com.proy.easywork.data.model.request

import java.io.Serializable

data class RQClienteCancelarServicio (val idServicio: Int,
                                      val motivoCancelacion: String): Serializable