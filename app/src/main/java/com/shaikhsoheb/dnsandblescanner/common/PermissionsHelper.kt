package com.shaikhsoheb.dnsandblescanner.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

object PermissionsHelper {

    const val RUNTIME_PERMISSION_REQUEST_CODE = 1001

    fun Context.hasPermission(permissionType: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permissionType) ==
                PackageManager.PERMISSION_GRANTED
    }

    fun Context.hasRequiredRuntimePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            hasPermission(Manifest.permission.BLUETOOTH_SCAN) &&
                    hasPermission(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    fun AppCompatActivity.requestRelevantRuntimePermissions() {
        if (hasRequiredRuntimePermissions()) { return }
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
                requestLocationPermission()
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                requestBluetoothPermissions()
            }
        }
    }

    private fun AppCompatActivity.requestLocationPermission() {
        runOnUiThread {
            AlertDialog.Builder(this)
                .setTitle("Location permission required")
                .setMessage("The system requires apps to be granted location access in order to scan for BLE devices.")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this@requestLocationPermission,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        RUNTIME_PERMISSION_REQUEST_CODE
                    )
                }.show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun AppCompatActivity.requestBluetoothPermissions() {
        runOnUiThread {
            AlertDialog.Builder(this)
                .setTitle("Bluetooth permissions required")
                .setMessage("The system requires apps to be granted Bluetooth access in order to scan for and connect to BLE devices.")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ),
                        RUNTIME_PERMISSION_REQUEST_CODE
                    )
                }.show()
        }
    }
}
