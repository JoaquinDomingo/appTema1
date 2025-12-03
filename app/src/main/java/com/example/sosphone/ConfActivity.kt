package com.example.sosphone

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sosphone.databinding.ActivityConfBinding
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import androidx.core.content.edit
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ConfActivity : AppCompatActivity() {

    private lateinit var confBinding: ActivityConfBinding
    private lateinit var sharedFich: SharedPreferences
    private lateinit var nameSharedPhone: String
    private lateinit var nameSharedMin : String
    private lateinit var nameSharedUrl : String

    private lateinit var nameSharedSpinner: String
    private lateinit var nameSharedCheckBox: String
    private lateinit var nameSharedRadio: String
    private lateinit var nameSharedDatePicker: String


    private var apuesta: Int = 3

    private var tiempoTiradas: Int = 1

    private var check: Boolean = false
    private var fecha: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confBinding = ActivityConfBinding.inflate(layoutInflater)
        setContentView(confBinding.root)

        initPreferentShared()
        start()
    }

    private fun initPreferentShared() {
        val nameSharedFich = getString(R.string.name_preferen_shared_fich)
        this.nameSharedPhone = getString(R.string.name_shared_phone)
        this.nameSharedUrl = getString(R.string.name_shared_url)
        this.nameSharedMin = getString(R.string.name_shared_min)

        this.nameSharedSpinner = getString(R.string.name_shared_spinner)
        this.nameSharedCheckBox = getString(R.string.name_shared_checkbox)
        this.nameSharedRadio = getString(R.string.name_shared_radio)
        this.nameSharedDatePicker = getString(R.string.name_shared_DatePicker)

        this.sharedFich = getSharedPreferences(nameSharedFich, Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        val ret = intent.getBooleanExtra("back", false)
        if (ret){
            confBinding.editPhone.setText("")
            confBinding.editURL.setText("")
            Toast.makeText(this, R.string.msg_new_phone, Toast.LENGTH_LONG).show()
            intent.removeExtra("back")
        }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)
    }

    fun isValidPhoneNumber2(phoneNumber: String, countryCode: String): Boolean {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            val number = phoneUtil.parse(phoneNumber, countryCode)
            if (number != null) {
                phoneUtil.isValidNumber(number)
            } else {
                false
            }
        } catch (e: NumberParseException) {
            e.printStackTrace()
            false
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun start(){
        val sharedPhone : String?  = sharedFich.getString(nameSharedPhone, null)
        val sharedUrl : String? = sharedFich.getString(nameSharedUrl, null)


        apuesta = sharedFich.getInt(nameSharedSpinner, 3).coerceIn(3, 18)
        check = sharedFich.getBoolean(nameSharedCheckBox, false)
        tiempoTiradas = sharedFich.getInt(nameSharedRadio, 1).coerceIn(1, 3)
        fecha = sharedFich.getString(nameSharedDatePicker, null)


        if (sharedPhone != null && sharedUrl != null){
            startMainActivity(sharedPhone, sharedUrl)
        }


        setupSpinner()
        setupDatePicker()
        setupRadio()
        setupCheckbox()


        confBinding.btnConf.setOnClickListener {
            val numberPhone = confBinding.editPhone.text.toString()
            val url = confBinding.editURL.text.toString()

            if (numberPhone.isEmpty())
                Toast.makeText(this, R.string.msg_empty_phone, Toast.LENGTH_LONG).show()
            else if (url.isEmpty())
                Toast.makeText(this, R.string.msg_empty_url, Toast.LENGTH_LONG).show()
            else if (fecha.isNullOrEmpty())
                Toast.makeText(this, "Debe seleccionar una fecha.", Toast.LENGTH_LONG).show()
            else if (!isValidPhoneNumber2(numberPhone, "ES"))
                Toast.makeText(this, R.string.msg_not_valid_phone, Toast.LENGTH_LONG).show()
            else{

                sharedFich.edit {
                    putString(nameSharedPhone, numberPhone)
                    putString(nameSharedUrl, url)


                    putInt(nameSharedSpinner, apuesta)
                    putBoolean(nameSharedCheckBox, check)
                    putInt(nameSharedRadio, tiempoTiradas)
                    putString(nameSharedDatePicker, fecha)
                }
                startMainActivity(numberPhone, url)
            }
        }
    }


    private fun setupSpinner() {
        val betOptions = (3..18).toList()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            betOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        confBinding.spinnerTime.adapter = adapter

        val initialIndex = betOptions.indexOf(apuesta)
        if (initialIndex != -1) confBinding.spinnerTime.setSelection(initialIndex)

        confBinding.spinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                apuesta = parent.getItemAtPosition(position) as Int
            }
            override fun onNothingSelected(parent: AdapterView<*>) {  }
        }
    }

    private fun setupRadio() {
        when (tiempoTiradas) {
            1 -> confBinding.radioTime1.isChecked = true
            2 -> confBinding.radioTime2.isChecked = true
            3 -> confBinding.radioTime3.isChecked = true
            else -> confBinding.radioTime1.isChecked = true
        }

        confBinding.radioGroupTime.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioTime1 -> tiempoTiradas = 1
                R.id.radioTime2 -> tiempoTiradas = 2
                R.id.radioTime3 -> tiempoTiradas = 3
            }
        }
    }


    private fun setupDatePicker() {
        confBinding.datePickerButton.setOnClickListener {
            showMaterialDatePicker()
        }
        fecha?.let { confBinding.datePickerButton.text = "Fecha: $it" }
    }

    private fun showMaterialDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Seleccionar Fecha de Registro")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.show(supportFragmentManager, "Elige una fecha")

        datePicker.addOnPositiveButtonClickListener { fechaLong ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = fechaLong

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = dateFormat.format(calendar.time)

            fecha = dateString
            confBinding.datePickerButton.text = "Fecha: $dateString"
            Toast.makeText(this, "Fecha configurada: $fecha", Toast.LENGTH_SHORT).show()
        }

        datePicker.addOnNegativeButtonClickListener {
            Toast.makeText(this, "Selección de fecha cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCheckbox() {
        confBinding.checkTTS.isChecked = check

        confBinding.checkTTS.setOnCheckedChangeListener { _, isChecked ->
            check = isChecked
        }
    }


    private fun startMainActivity(phone: String, url: String, ) {
        val intent = Intent(this@ConfActivity, MainActivity2::class.java)
        intent.apply {
            putExtra(getString(R.string.string_phone), phone)
            putExtra(getString(R.string.string_url), url)

            putExtra("APUESTA", apuesta)
            putExtra("CHISTES", check)
            putExtra("TIEMPO TIRADAS", tiempoTiradas)
            putExtra("FECHA", fecha)

            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent )
    }
}