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

    private var apuesta = 3
    private var tiempoTiradas = 1

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        phonenumber = intent.getStringExtra(getString(R.string.string_phone))
        url = intent.getStringExtra(getString(R.string.string_url))
        apuesta = intent.getIntExtra("APUESTA", 3)
        tiempoTiradas = intent.getIntExtra("TIEMPO TIRADAS", 1)
        registerPermissionLauncher()
    }

    fun onButtonClick(view: View) {
        when (view.id) {
            R.id.btnLlamada -> {
                if (phonenumber.isNullOrEmpty()) {
                    Toast.makeText(this, "Ese número no sale", Toast.LENGTH_SHORT).show()
                } else if (isCallPermissionGranted()) {
                    llamadaTlf()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            }

            R.id.btnUrl -> {
                url?.let { abrirUrl(it) } ?: Toast.makeText(
                    this,
                    "Esa Url es incorrecta",
                    Toast.LENGTH_SHORT
                ).show()
            }

            R.id.btnAlarm -> establecerAlarma()

            R.id.btnGmail -> enviarCorreoElectronico()

            R.id.btnDados -> {
                val intent = Intent(this, ActivityDados::class.java).apply {
                    putExtra("TIEMPO TIRADAS", tiempoTiradas)
                    putExtra("APUESTA", apuesta)
                }
                startActivity(intent)
            }

            R.id.btnvolverAtras -> {
                val intent = Intent(this, ConfActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.putExtra("back", true)
                startActivity(intent)
            }
        }
    }

    private fun llamadaTlf() {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phonenumber")))
    }

    private fun isCallPermissionGranted() =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

    private fun registerPermissionLauncher() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) llamadaTlf() else goToAppSettings()
            }
    }

    private fun goToAppSettings() {
        Toast.makeText(this, "Necesitas habilitar los permisos", Toast.LENGTH_LONG).show()
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        })
    }

    private fun abrirUrl(rawUrl: String) {
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


    private fun establecerAlarma() {
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

    private fun enviarCorreoElectronico() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:dojoaquindo@gmail.com")).apply {
            putExtra(Intent.EXTRA_SUBJECT, "Correo desde la aplicación de Joaquín Domingo Domingo")
            putExtra(Intent.EXTRA_TEXT, "Este es el mensaje enviado a través del metodo sendEmail()")
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No se encontró ninguna app de correo", Toast.LENGTH_SHORT).show()
        }
    }
}
