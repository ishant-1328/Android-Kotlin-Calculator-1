package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Checks whether the last input is a number or not
    private var lastNumeric : Boolean = false

    // Checks whether the last input is a decimal or not
    private var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    /* Appends the Numeric Values into the TextView Input */
    fun onDigit(view : View){

        lastNumeric = true
        binding.tvInput.append((view as Button).text)
    }

    /* Appends the Decimal into the TextView Input */
    fun onDecimal(view: View){

        // Check whether the last input is a numerical value or not!

        if(lastNumeric && !lastDot){
            binding.tvInput.append(".")
            lastNumeric = false // This is because now the last input is the decimal value
            lastDot = true
        }
    }

    fun onOperator(view: View){

        // Check whether the last input is a numerical value and previously an operator is present or not

        if(lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())){
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onClear(view: View){
        binding.tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }


    fun onEqual(view: View){
        if(lastNumeric){
            var value = binding.tvInput.text.toString()
            var prefix = ""

            try {
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                when {
                    value.contains("*") -> {
                        val splittedValue = value.split("*")

                        var firstNumber = splittedValue[0]
                        val secondNumber = splittedValue[1]

                        if(prefix.isNotEmpty()){
                            firstNumber = prefix + firstNumber
                        }
                        binding.tvInput.text = removeZeroAfterDot((firstNumber.toDouble() * secondNumber.toDouble()).toString())
                    }
                    value.contains("/") -> {
                        val splittedValue = value.split("/")

                        var firstNumber = splittedValue[0]
                        val secondNumber = splittedValue[1]

                        if(prefix.isNotEmpty()){
                            firstNumber = prefix + firstNumber
                        }
                        binding.tvInput.text = removeZeroAfterDot((firstNumber.toDouble() / secondNumber.toDouble()).toString())
                    }
                    value.contains("+") -> {
                        val splittedValue = value.split("+")

                        var firstNumber = splittedValue[0]
                        val secondNumber = splittedValue[1]

                        if(prefix.isNotEmpty()){
                            firstNumber = prefix + firstNumber
                        }
                        binding.tvInput.text = removeZeroAfterDot((firstNumber.toDouble() + secondNumber.toDouble()).toString())
                    }
                    value.contains("-") -> {
                        val splittedValue = value.split("-")

                        var firstNumber = splittedValue[0]
                        val secondNumber = splittedValue[1]

                        if(prefix.isNotEmpty()){
                            firstNumber = prefix + firstNumber
                        }
                        binding.tvInput.text = removeZeroAfterDot((firstNumber.toDouble() - secondNumber.toDouble()).toString())
                    }
                }

            }catch (e : ArithmeticException){
                e.printStackTrace()
                binding.tvInput.text = e.printStackTrace().toString()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length-2)
        }
        return value
    }

    private fun isOperatorAdded(value : String): Boolean {

        // Check if the equation starts with '-' then ignore it.

        return if(value.startsWith("-")){
            false
        }else{
            (value.contains("/") || value.contains("+") || value.contains("*") || value.contains("-"))
        }
    }
}