package com.aravind.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aravind.quiz.R
import com.aravind.quiz.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    private var binding: ActivityCalculatorBinding? = null
    var lastNumeric: Boolean = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator)
    }

    fun onDigit(view: View) {
        val value = (view as Button).text
        binding?.tv?.append(value)
        lastNumeric = true
    }

    fun oPeration(view: View) {
        val value = (view as Button).text

        binding?.tv?.text?.let {
            if (lastNumeric && !isOperandAdded(it.toString())){
                binding?.tv?.append(value)
                lastNumeric = false
            }
        }
    }

    fun dot(view: View){
        if (!lastDot ){
            binding?.tv?.append((view as Button).text)
            lastDot = true
        }
    }

    fun clear(view: View){
        binding?.tv?.text = ""
    }
    fun answer(view: View) {
        val value = binding?.tv?.text

        if (value != null) {
            if (value.contains("+") && lastNumeric && lastDot) {
                val splitValue = value?.split("+")
                val one = splitValue?.get(0)?.toDouble()
                val two = splitValue?.get(1)?.toDouble()

                if (one != null) {
                    val length: Int? = binding?.tv?.text?.length
                    length?.let {
                        binding?.tv?.text = (one + two!!).toString()
                    }
                }
            } else if (value.contains("-") && lastNumeric) {
                val splitValue = value?.split("-")
                val one = splitValue?.get(0)?.toDouble()
                val two = splitValue?.get(1)?.toDouble()

                if (one != null) {
                    val length: Int? = binding?.tv?.text?.length
                    length?.let {
                        binding?.tv?.text = (one - two!!).toString()
                    }
                }
            } else if (value.contains("*") && lastNumeric) {
                val splitValue = value?.split("*")
                val one = splitValue?.get(0)?.toDouble()
                val two = splitValue?.get(1)?.toDouble()

                if (one != null) {
                    val length: Int? = binding?.tv?.text?.length
                    length?.let {
                        binding?.tv?.text = (one * two!!).toString()
                    }
                }
            } else if (value.contains("/") && lastNumeric) {
                val splitValue = value?.split("/")
                val one = splitValue?.get(0)?.toDouble()
                val two = splitValue?.get(1)?.toDouble()

                if (one != null) {
                    val length: Int? = binding?.tv?.text?.length
                    length?.let {
                        binding?.tv?.text = (one / two!!).toString()
                    }
                }
            }
        }
    }

    fun isOperandAdded(value: String): Boolean {
        return if (value.startsWith("-"))
            return false
        else (value.contains("+") || value.contains("-")
                || value.contains("*") || value.contains("/"))
    }
}