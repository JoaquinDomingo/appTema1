package com.example.sosphone

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.sosphone.databinding.ActivityMain2Binding
import java.util.Calendar



class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private var phonenumber: String? = null
    private var url: String? = null

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        phonenumber = intent.getStringExtra(getString(R.string.string_phone))
        url = intent.getStringExtra(getString(R.string.string_url))
        registerPermissionLauncher()
    }

    fun onButtonClick(view: View) {
        when (view.id) {
            R.id.btnLlamada -> {
                if (phonenumber.isNullOrEmpty()) {
                    Toast.makeText(this, "Número no válido", Toast.LENGTH_SHORT).show()
                } else if (isCallPermissionGranted()) {
                    callPhone()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            }

            R.id.btnUrl -> {
                url?.let { openUrl(it) } ?: Toast.makeText(this, "URL no válida", Toast.LENGTH_SHORT).show()
            }

            R.id.btnAlarm -> setAlarm()

            R.id.btnGmail -> sendEmail()
        }
    }

    private fun callPhone() {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phonenumber")))
    }

    private fun isCallPermissionGranted() =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

    private fun registerPermissionLauncher() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) callPhone() else goToAppSettings()
            }
    }

    private fun goToAppSettings() {
        Toast.makeText(this, "Necesitas habilitar los permisos", Toast.LENGTH_LONG).show()
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        })
    }
    /*
    private fun openUrl(rawUrl: String) {
        val formattedUrl = if (rawUrl.startsWith("http://") || rawUrl.startsWith("https://")) rawUrl else "https://$rawUrl"
        Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl)).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            try { startActivity(this) }
            catch (e: Exception) {
                Toast.makeText(this@MainActivity2, "No hay navegador disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }
    */
    private fun openUrl(rawUrl: String) {
        val formattedUrl = if (rawUrl.startsWith("http://") || rawUrl.startsWith("https://")) {
            rawUrl
        } else {
            "https://$rawUrl"
        }

        if (rawUrl.equals("twitter.com", ignoreCase = true) || rawUrl.equals("x.com" , ignoreCase = true)) {
            Toast.makeText(this@MainActivity2, "Esta Url no está disponible", Toast.LENGTH_LONG).show()
            return
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl)).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity2, "No hay navegador disponible", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 2)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma creada por Joaquin a traves de la app")
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minute)
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
        }
        Toast.makeText(this, "Alarma programada en 2 minutos", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:dojoaquindo@gmail.com")).apply {
            putExtra(Intent.EXTRA_SUBJECT, "Correo desde SOSPhone")
            putExtra(Intent.EXTRA_TEXT, "Mensaje enviado desde la app SOSPhone.")
        }
        try { startActivity(intent) }
        catch (e: Exception) {
            Toast.makeText(this, "No se encontró app de correo", Toast.LENGTH_SHORT).show()
        }
    }
}
