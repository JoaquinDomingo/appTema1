package com.example.sosphone

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sosphone.databinding.ActivityChistesBinding
import java.util.Locale

class ActivityChistes : AppCompatActivity() {
    private val chistes = listOf(
        "¿Por qué los pájaros no usan Facebook? Porque ya tienen Twitter.",
        "¿Qué le dijo un pez a otro pez? ¡Nada!",
        "¿Por qué el libro de matemáticas estaba triste? Porque tenía muchos problemas.",
        "¿Qué hace una abeja en el gimnasio? ¡Zum-ba!",
        "¿Por qué los esqueletos no pelean entre ellos? Porque no tienen agallas."
    )

    private lateinit var binding: ActivityChistesBinding
    private val TIEMPO_MAXIMO : Long= 1000L
    private var tiempoEntreToque :Long = 0
    private lateinit var handler : Handler
    private lateinit var textoAVoz : TextToSpeech

    private var Checkbox: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChistesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Checkbox = intent.getBooleanExtra("CHISTES", false)
        configurarTextoChiste()
        iniciarHandler()
        initEvent()
    }

    private fun iniciarHandler(){
        handler = Handler(Looper.getMainLooper())
        binding.progressBar.visibility  = View.VISIBLE
        binding.btnChiste.visibility = View.INVISIBLE

        Thread{
            Thread.sleep(3000)
            handler.postDelayed({
                binding.progressBar.visibility = View.GONE
                val description = getString(R.string.describir)
                binding.textChiste.text = description
                if (Checkbox) {
                    contar(description)
                }

                binding.btnChiste.visibility = View.VISIBLE
            },3000)
        }.start()
    }

    private fun configurarTextoChiste() {
        textoAVoz = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) {
                textoAVoz.language = Locale.getDefault()
            } else {
                Toast.makeText(this, "No se pudo cargar la voz", Toast.LENGTH_SHORT).show()
                Checkbox = false
            }
        })
    }


    private fun initEvent(){
        var chistess = chistes
        binding.btnChiste.setOnClickListener {
            val tiempo = System.currentTimeMillis()
            if(tiempo - tiempoEntreToque >= TIEMPO_MAXIMO){
                val chisteAleatorio = chistess.random()
                binding.textChiste.text = chisteAleatorio
                if (Checkbox){
                    contar(chisteAleatorio)
                }
                tiempoEntreToque  = tiempo
            } else {
                Toast.makeText(this, "Espera un momento para escuchar otro chiste.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnVolverChistes.setOnClickListener {
            finish()
        }
    }

    private fun contar(texto: String){
        textoAVoz.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        if (::textoAVoz.isInitialized) {
            textoAVoz.stop()
            textoAVoz.shutdown()
        }

        super.onDestroy()
    }


}