package com.shaikhsoheb.dnsandblescanner.common

import android.net.nsd.NsdManager
import android.net.nsd.NsdManager.RegistrationListener
import android.net.nsd.NsdServiceInfo
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class NsdHelper @Inject constructor() {

    @Inject lateinit var service: NsdServiceInfo
    @Inject lateinit var nsdManager: NsdManager

    init {
        service.serviceName = SERVICE_NAME
        service.serviceType = SERVICE_TYPE
    }

    private fun setupService(port: Int) {
        service.serviceName = SERVICE_NAME
        service.serviceType = SERVICE_TYPE
        service.port = port
    }

    fun getNsdlManager(): NsdManager {
        return nsdManager
    }

    companion object {
        const val SERVICE_NAME = "SampleNsdService"
        const val SERVICE_TYPE = "_http._tcp"

        fun registerNSDService(nsdHelper: NsdHelper, serviceInfo: NsdServiceInfo, port: Int, listener: RegistrationListener) {
            nsdHelper.setupService(port)
            nsdHelper.getNsdlManager().registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, listener)
        }

        fun unregisterNSDService(nsdHelper: NsdHelper, listener: RegistrationListener?) {
            nsdHelper.getNsdlManager().unregisterService(listener)
        }
    }
}