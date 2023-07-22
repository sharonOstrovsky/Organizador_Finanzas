package com.example.organizador.ui.views

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.echo.holographlibrary.PieGraph
import com.echo.holographlibrary.PieSlice
import com.example.organizador.R
import com.example.organizador.databinding.ActivityGraficoBinding
import kotlin.math.roundToInt

class GraficoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraficoBinding
    private lateinit var pieGraph: PieGraph
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraficoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pieGraph = findViewById<PieGraph>(R.id.graphPie)
        graficar()
    }

    fun graficar(){
        val slice = PieSlice()
        var hex = generarColorHexAleatorio()
        slice.color = Color.parseColor(hex)
        var cant = 30
        slice.value = cant.toFloat()
        pieGraph.addSlice(slice)
        var drawable = ContextCompat.getDrawable(this, R.drawable.uncheck_box)
        var drawableTintList = ColorStateList.valueOf(Color.parseColor(hex))
        var drawableCompat = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTintList(drawableCompat, drawableTintList)
        binding.gastos.setCompoundDrawablesWithIntrinsicBounds(drawableCompat, null, null, null)

        val slice2 = PieSlice()
        hex = generarColorHexAleatorio()
        slice2.color = Color.parseColor(hex)
        cant = 70
        slice2.value = cant.toFloat()
        pieGraph.addSlice(slice2)
        drawable = ContextCompat.getDrawable(this, R.drawable.uncheck_box)
        drawableTintList = ColorStateList.valueOf(Color.parseColor(hex))
        drawableCompat = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTintList(drawableCompat, drawableTintList)
        binding.ahorro.setCompoundDrawablesWithIntrinsicBounds(drawableCompat, null, null, null)       /* for (i in 0 until 2){
            val slice = PieSlice()
            slice.color = Color.parseColor(generarColorHexAleatorio())
            slice.value = i.toString().toFloat()
            pieGraph.addSlice(slice)
        }

        */
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