package team.sw.everyonetayo.util;

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager


class PermissionCheck {
    companion object {
        fun checkGps(context: Context):Boolean {
            return (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        }

        fun checkRecode(context: Context):Boolean {
            return context.checkSelfPermission(Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED
        }
    }
}
