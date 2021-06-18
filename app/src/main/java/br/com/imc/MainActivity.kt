package br.com.imc

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.imc.extensions.format
import br.com.imc.watchers.DecimalTextWatcher

class MainActivity : AppCompatActivity() {

    lateinit var inputPeso: EditText
    lateinit var inputAltura: EditText
    lateinit var btnCalcular: Button
    lateinit var imcIndice: TextView
    lateinit var imcImage: ImageView
    lateinit var imcPeso: TextView
    lateinit var footer: RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpView()

        footer.visibility = View.GONE

        btnCalcular.setOnClickListener {
            if (inputPeso.text.toString() != "" && inputAltura.text.toString() != "" ) {
                inputAltura.onEditorAction(EditorInfo.IME_ACTION_DONE)
                calcular()
            }
        }
    }

    private fun calcular() {
        val peso = inputPeso.text.toString().toDouble()
        val altura = inputAltura.text.toString().toDouble()

        val imc = String.format("%.1f", peso / (altura * altura)).toDouble()

        when (imc) {
            in 0.0..18.5 -> calculateClassification(imc, R.drawable.fem_abaixo, R.string.magreza)
            in 18.6..24.9 -> calculateClassification(imc, R.drawable.fem_ideal, R.string.peso_normal)
            in 25.0..29.9 -> calculateClassification(imc, R.drawable.fem_sobre, R.string.sobre_peso)
            in 30.0..34.9 -> calculateClassification(imc, R.drawable.fem_sobre, R.string.obesidade_grau_i)
            in 35.0..39.9 -> calculateClassification(imc, R.drawable.fem_obeso, R.string.obesidade_grau_ii)
            else -> calculateClassification(imc, R.drawable.fem_extremo_obeso, R.string.obesidade_grau_iii)

        }
    }

    private fun calculateClassification(imc: Double, drawbleId: Int, stringId: Int ) {
        imcIndice.text = getString(R.string.status_imc, imc.format(2))
        imcImage.setImageDrawable(
            ContextCompat.getDrawable(this, drawbleId)
        )
        imcPeso.text = getString(stringId)

        footer.visibility = View.VISIBLE
    }

    private fun setUpView() {
        inputPeso = findViewById(R.id.inputPeso)
        inputAltura = findViewById(R.id.inputAltura)
        btnCalcular = findViewById(R.id.btnCalcular)
        imcIndice = findViewById(R.id.imcIndice)
        imcImage = findViewById(R.id.imcImage)
        imcPeso = findViewById(R.id.imcPeso)
        footer = findViewById(R.id.footer)

        inputAltura.addTextChangedListener(DecimalTextWatcher(inputAltura))
        inputPeso.addTextChangedListener(DecimalTextWatcher(inputPeso))
    }
}