package com.example.organizador.ui.views

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.echo.holographlibrary.PieGraph
import com.echo.holographlibrary.PieSlice
import com.example.organizador.OrganizadorApplication.Companion.prefs
import com.example.organizador.R
import com.example.organizador.databinding.ActivityGraficoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.roundToInt

class GraficoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraficoBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var pieGraph: PieGraph
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraficoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottonNavigation()
        pieGraph = findViewById<PieGraph>(R.id.graphPie)


        binding.ingreso.setText(prefs.getIncome())

        income()


        if(prefs.getIncome().isEmpty()){
            prefs.saveIncome("0")
        }


        if(prefs.getTotalExpense().isNotEmpty() && prefs.getIncome().isNotEmpty()){
            pieGraph.removeSlices()
            graficar()
        }



        handler = Handler()
        runnable = Runnable {
            // Acción que se ejecutará después de que el usuario haya terminado de escribir
            val inputText = binding.ingreso.text.toString()
            if (inputText.isNotEmpty()) {
                prefs.saveIncome(binding.ingreso.text.toString())
                pieGraph.removeSlices()
                graficar()
            }
        }

    }

    fun income(){

        binding.ingreso.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Este método se invoca antes de que el texto cambie
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Este método se invoca mientras el texto cambia
            }

            override fun afterTextChanged(s: Editable?) {
                // Este método se invoca después de que el texto cambie
                handler.removeCallbacks(runnable)
                // Ejecutar la acción después de un retraso (por ejemplo, 2 segundos)
                handler.postDelayed(runnable, 2000)
            }
        })

    }

    fun bottonNavigation(){

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.graphPie
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.expense -> {
                    redirectGastos()
                    true
                }
                R.id.todo ->{
                    redirectToDo()
                    true
                }
                else -> false
            }
        }
    }


    fun redirectGastos(){
        val intent = Intent(this, GastosActivity::class.java)
        startActivity(intent)
    }

    fun redirectToDo(){
        val intent = Intent(this, ToDoListActivity::class.java)
        startActivity(intent)
    }

    fun graficar(){
        val slice = PieSlice()
        var hex = generarColorHexAleatorio()
        slice.color = Color.parseColor(hex)
        var cant = prefs.getIncome().toDouble()
        slice.value = cant.toFloat()
        pieGraph.addSlice(slice)
        var drawable = ContextCompat.getDrawable(this, R.drawable.uncheck_box)
        var drawableTintList = ColorStateList.valueOf(Color.parseColor(hex))
        var drawableCompat = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTintList(drawableCompat, drawableTintList)
        binding.ahorro.setCompoundDrawablesWithIntrinsicBounds(drawableCompat, null, null, null)

        val slice2 = PieSlice()
        hex = generarColorHexAleatorio()
        slice2.color = Color.parseColor(hex)
        cant = prefs.getTotalExpense().toDouble()
        slice2.value = cant.toFloat()
        pieGraph.addSlice(slice2)
        drawable = ContextCompat.getDrawable(this, R.drawable.uncheck_box)
        drawableTintList = ColorStateList.valueOf(Color.parseColor(hex))
        drawableCompat = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTintList(drawableCompat, drawableTintList)
        binding.gastos.setCompoundDrawablesWithIntrinsicBounds(drawableCompat, null, null, null)



        // Calcular el total de los valores para obtener el porcentaje
        val totalValue = slice.value.toDouble() + slice2.value.toDouble()

        val ahorroNum = (slice.value.toDouble() / totalValue) * 100
        val ahorroString = String.format("%.2f", ahorroNum)
        val gastosNUm = (slice2.value.toDouble() / totalValue) * 100
        val gastosString = String.format("%.2f", gastosNUm)
        binding.ahorro.text = "Ahorros $ahorroString %"
        binding.gastos.text = "Gastos $gastosString %"


    }

    fun generarColorHexAleatorio():String{
        var letras = arrayOf("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F")
        var color = "#"
        for (i in 0..5){
            color += letras[(Math.random()*15).roundToInt()]
        }
        return color
    }

    private fun generarColorAleatorio(): String {
        val r = (0..255).random()
        val g = (0..255).random()
        val b = (0..255).random()
        return String.format("#%02x%02x%02x", r, g, b)
    }
}