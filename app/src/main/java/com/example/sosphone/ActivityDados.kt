package com.example.sosphone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sosphone.databinding.ActivitydadosBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ActivityDados : AppCompatActivity() {
    private lateinit var bindingMain : ActivitydadosBinding
    private var sum : Int = 0


    private var apuesta: Int = 3
    private var retrasoTiradas: Long = 1000

    private val handler = Handler(Looper.getMainLooper())
    private val animationCycles = 5
    private var isRolling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivitydadosBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        retrasoTiradas = intent.getIntExtra("TIEMPO TIRADAS", 1) * 1000L
        apuesta = intent.getIntExtra("APUESTA", 3)

        Toast.makeText(this, "Adivinar: $apuesta", Toast.LENGTH_SHORT).show()

        bindingMain.imagviewDado3.visibility = View.VISIBLE

        initEvent()
    }

    private fun initEvent() {
        bindingMain.txtResultado.visibility = View.INVISIBLE
        bindingMain.imageButton.setOnClickListener{
            if (!isRolling) {
                bindingMain.txtResultado.visibility = View.INVISIBLE
                game()
            } else {
                Toast.makeText(this, "La tirada está en curso. Espera un momento.", Toast.LENGTH_SHORT).show()
            }
        }


        bindingMain.btnVolverDados.setOnClickListener {
            finish()
        }
    }

    private fun game(){
        isRolling = true
        scheduleRun()
    }

    private fun scheduleRun() {



        for (i in 1..animationCycles) {
            handler.postDelayed({
                throwDadoInTime()
            }, (100 * i).toLong())
        }


        handler.postDelayed({
            viewResult()
        }, retrasoTiradas)
    }

    private fun throwDadoInTime() {
        val numDados = Array(3){Random.nextInt(1, 7)}
        val imagViews : Array<ImageView> = arrayOf(
            bindingMain.imagviewDado1,
            bindingMain.imagviewDado2,
            bindingMain.imagviewDado3
        )

        sum = numDados.sum()
        for (i in 0..2)
            selectView(imagViews[i], numDados[i])
    }

    private fun selectView(imgV: ImageView, v: Int) {
        when (v){
            1 -> imgV.setImageResource(R.drawable.dado1)
            2 -> imgV.setImageResource(R.drawable.dado2)
            3 -> imgV.setImageResource(R.drawable.dado3)
            4 -> imgV.setImageResource(R.drawable.dado4)
            5 -> imgV.setImageResource(R.drawable.dado5)
            6 -> imgV.setImageResource(R.drawable.dado6)
        }
    }

    private fun viewResult() {
        bindingMain.txtResultado.text = sum.toString()
        bindingMain.txtResultado.visibility = View.VISIBLE
        isRolling = false

        if (sum == apuesta) {
            launchSuccessActivity()
        } else {
            Toast.makeText(this, "Fallaste. Salió el número $sum.", Toast.LENGTH_LONG).show()
        }

        showResultCard(sum)
    }

    private fun launchSuccessActivity() {
        Toast.makeText(this, "¡ACERTASTE! El número es $sum.", Toast.LENGTH_LONG).show()
        val intent = Intent(this, AcertadoActivity::class.java).apply {
            putExtra("RESULTADO", sum)
        }
        startActivity(intent)
    }

    private fun showResultCard(total: Int) {
        val recursoCarta = when (total) {
            3 -> R.drawable.card_3
            4 -> R.drawable.card_4
            5 -> R.drawable.card_5
            6 -> R.drawable.card_6
            7 -> R.drawable.card_7
            8 -> R.drawable.card_8
            9 -> R.drawable.card_9
            10 -> R.drawable.card_10
            11 -> R.drawable.card_11
            12 -> R.drawable.card_12
            13 -> R.drawable.card_13
            14 -> R.drawable.card_14
            15 -> R.drawable.card_15
            16 -> R.drawable.card_16
            17 -> R.drawable.card_17
            18 -> R.drawable.card_18
            else -> R.drawable.card_default
        }
        bindingMain.imageResultadoCarta.setImageResource(recursoCarta)
        bindingMain.imageResultadoCarta.visibility = View.VISIBLE
    }
}