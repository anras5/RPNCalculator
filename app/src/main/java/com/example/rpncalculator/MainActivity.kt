package com.example.rpncalculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import java.security.AccessController.getContext
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private var stack = ArrayDeque<Double>()
    private var undoStack = ArrayDeque<ArrayDeque<Double>>()

    var background: LinearLayout? = null

    var tvStack1: TextView? = null
    var tvStack2: TextView? = null
    var tvStack3: TextView? = null
    var tvStack4: TextView? = null

    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null
    var btn4: Button? = null
    var btn5: Button? = null
    var btn6: Button? = null
    var btn7: Button? = null
    var btn8: Button? = null
    var btn9: Button? = null
    var btn0: Button? = null

    var btnAc: Button? = null
    var btnDrop: Button? = null
    var btnSwap: Button? = null
    var btnExp: Button? = null
    var btnUndo: Button? = null

    var btnSqrt: Button? = null
    var btnMul: Button? = null
    var btnDiv: Button? = null
    var btnPlus: Button? = null
    var btnMinus: Button? = null

    var btnPlusMinus: Button? = null
    var btnDot: Button? = null

    var btnMenu: Button? = null
    var btnEnter: Button? = null

    var entering: Boolean = false
    var lastDot: Boolean = false
    var darkTheme: Int? = null
    var precision: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("hello")

        background = findViewById(R.id.mainLayout)
        tvStack1 = findViewById(R.id.txtStack1)
        tvStack2 = findViewById(R.id.txtStack2)
        tvStack3 = findViewById(R.id.txtStack3)
        tvStack4 = findViewById(R.id.txtStack4)
        btn1 = findViewById(R.id.btnOne)
        btn2 = findViewById(R.id.btnTwo)
        btn3 = findViewById(R.id.btnThree)
        btn4 = findViewById(R.id.btnFour)
        btn5 = findViewById(R.id.btnFive)
        btn6 = findViewById(R.id.btnSix)
        btn7 = findViewById(R.id.btnSeven)
        btn8 = findViewById(R.id.btnEight)
        btn9 = findViewById(R.id.btnNine)
        btn0 = findViewById(R.id.btnZero)
        btnAc = findViewById(R.id.btnAc)
        btnDrop = findViewById(R.id.btnDrop)
        btnSwap = findViewById(R.id.btnSwap)
        btnUndo = findViewById(R.id.btnUndo)
        btnExp = findViewById(R.id.btnExponentiation)
        btnSqrt = findViewById(R.id.btnSquareRoot)
        btnMul = findViewById(R.id.btnMultiplication)
        btnDiv = findViewById(R.id.btnDivision)
        btnPlus = findViewById(R.id.btnAddition)
        btnMinus = findViewById(R.id.btnSubtraction)
        btnPlusMinus = findViewById(R.id.btnChangeSymbol)
        btnDot = findViewById(R.id.btnDot)
        btnEnter = findViewById(R.id.btnEnter)
        btnMenu = findViewById(R.id.btnMenu)
        darkTheme = 0
        precision = 4
        displayStack(true)
    }

    fun roundNumber(number: Double, precision: Int): Double {
        return round(number * 10.0.pow(precision))/10.0.pow(precision)
    }

    private fun displayStack(operation: Boolean) {
        if (operation) {
            when (stack.size) {
                0 -> {
                    tvStack4?.text = "4:"
                    tvStack3?.text = "3:"
                    tvStack2?.text = "2:"
                    tvStack1?.text = "1:"
                    tvStack1?.gravity = Gravity.BOTTOM
                }
                1 -> {
                    tvStack4?.text = "4:"
                    tvStack3?.text = "3:"
                    tvStack2?.text = "2:"
                    tvStack1?.text = "1: ${roundNumber(stack[0], precision)}"
                    tvStack1?.gravity = Gravity.BOTTOM
                }
                2 -> {
                    tvStack4?.text = "4:"
                    tvStack3?.text = "3:"
                    tvStack2?.text = "2: ${roundNumber(stack[1], precision)}"
                    tvStack1?.text = "1: ${roundNumber(stack[0], precision)}"
                    tvStack1?.gravity = Gravity.BOTTOM
                }
                3 -> {
                    tvStack4?.text = "4:"
                    tvStack3?.text = "3: ${roundNumber(stack[2], precision)}"
                    tvStack2?.text = "2: ${roundNumber(stack[1], precision)}"
                    tvStack1?.text = "1: ${roundNumber(stack[0], precision)}"
                    tvStack1?.gravity = Gravity.BOTTOM
                }
                else -> {
                    tvStack4?.text = "4: ${roundNumber(stack[3], precision)}"
                    tvStack3?.text = "3: ${roundNumber(stack[2], precision)}"
                    tvStack2?.text = "2: ${roundNumber(stack[1], precision)}"
                    tvStack1?.text = "1: ${roundNumber(stack[0], precision)}"
                    tvStack1?.gravity = Gravity.BOTTOM
                }
            }
        } else if (entering) {
            when (stack.size) {
                0 -> {
                    tvStack4?.text = "3:"
                    tvStack3?.text = "2:"
                    tvStack2?.text = "1:"
                }
                1 -> {
                    tvStack4?.text = "3:"
                    tvStack3?.text = "2:"
                    tvStack2?.text = "1: ${roundNumber(stack[0], precision)}"
                }
                2 -> {
                    tvStack4?.text = "3:"
                    tvStack3?.text = "2: ${roundNumber(stack[1], precision)}"
                    tvStack2?.text = "1: ${stack[0]}"
                }
                else -> {
                    tvStack4?.text = "3: ${roundNumber(stack[2], precision)}"
                    tvStack3?.text = "2: ${roundNumber(stack[1], precision)}"
                    tvStack2?.text = "1: ${roundNumber(stack[0], precision)}"
                }
            }
        } else {
            when (stack.size) {
                0 -> {
                    tvStack4?.text = "3:"
                    tvStack3?.text = "2:"
                    tvStack2?.text = "1:"
                    tvStack1?.text = ""
                    tvStack1?.gravity = Gravity.END
                }
                1 -> {
                    tvStack4?.text = "3:"
                    tvStack3?.text = "2:"
                    tvStack2?.text = "1: ${roundNumber(stack[0], precision)}"
                    tvStack1?.text = ""
                    tvStack1?.gravity = Gravity.END
                }
                2 -> {
                    tvStack4?.text = "3:"
                    tvStack3?.text = "2: ${roundNumber(stack[1], precision)}"
                    tvStack2?.text = "1: ${roundNumber(stack[0], precision)}"
                    tvStack1?.text = ""
                    tvStack1?.gravity = Gravity.END
                }
                else -> {
                    tvStack4?.text = "3: ${roundNumber(stack[2], precision)}"
                    tvStack3?.text = "2: ${roundNumber(stack[1], precision)}"
                    tvStack2?.text = "1: ${roundNumber(stack[0], precision)}"
                    tvStack1?.text = ""
                    tvStack1?.gravity = Gravity.END
                }
            }
        }
    }

    fun onDigit(view: View) {
        displayStack(false)
        entering = true
        lastDot = false
        tvStack1?.append((view as Button).text)
    }

    fun onDot(view: View) {
        if (!lastDot) {
            displayStack(false)
            entering = true
            lastDot = true
            tvStack1?.append((view as Button).text)
        }
    }

    fun onUndo(view: View) {
        if (!entering && stack.size > 0 && undoStack.size > 0) {
            stack = ArrayDeque(undoStack[0])
            undoStack.removeFirst()
            displayStack(true)
//            Toast.makeText(this, "${undoStack}", Toast.LENGTH_SHORT).show()
        }
    }

    fun onEnter(view: View) {
        if (entering) {
            undoStack.addFirst(ArrayDeque(stack))
            stack.addFirst(tvStack1?.text.toString().toDouble())
            displayStack(true)
            entering = false
        }
    }

    fun onOperationOneArgument(view: View) {
        if (!entering && stack.size > 0) {
            undoStack.addFirst(ArrayDeque(stack))
            val operationString = (view as Button).text
            var answer = 0.0
            var succesful: Boolean = true
            try {
                when (operationString) {
                    "+/-" -> {
                        answer = if (stack[0] == 0.0) {
                            stack[0]
                        } else {
                            -stack[0]
                        }
                    }
                    "√" -> {
                        if (stack[0] > 0) {
                            answer = sqrt(stack[0])
                        } else {
                            succesful = false
                        }
                    }
                }
                if (succesful) {
                    stack.removeFirst()
                    stack.addFirst(answer)
                } else {
                    Toast.makeText(this, "Cannot perform this operation", Toast.LENGTH_SHORT).show()
                }
                displayStack(true)
            } catch (e: ArithmeticException) {
                Toast.makeText(this, "Cannot perform this operation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onOperationTwoArguments(view: View) {
        if (!entering && stack.size > 1) {
            undoStack.addFirst(ArrayDeque(stack))
            val operationString = (view as Button).text
            var answer = 0.0
            var successful: Boolean = true
            try {
                when (operationString) {
                    "+" -> {
                        answer = stack[1] + stack[0]
                    }
                    "-" -> {
                        answer = stack[1] - stack[0]
                    }
                    "×" -> {
                        answer = stack[1] * stack[0]
                    }
                    "÷" -> {
                        if (stack[0] != 0.0) {
                            answer = stack[1] / stack[0]
                        } else {
                            successful = false
                            Toast.makeText(
                                this,
                                "Cannot perform this operation",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    "y^x" -> {
                        answer = stack[1].pow(stack[0])
                    }
                }
                if (successful) {
                    stack.removeFirst()
                    stack.removeFirst()
                    stack.addFirst(answer)
                }
                displayStack(true)
            } catch (e: ArithmeticException) {
                Toast.makeText(this, "Cannot perform this operation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onAC(view: View) {
        stack.clear()
        undoStack.clear()
        displayStack(true)
    }

    fun onDROP(view: View) {
        if (!entering && stack.size > 0) {
            undoStack.addFirst(ArrayDeque(stack))
            stack.removeFirst()
            displayStack(true)
        }
    }

    fun onSWAP(view: View) {
        if (!entering && stack.size > 1) {
            undoStack.addFirst(ArrayDeque(stack))
            val temp = stack[0]
            stack[0] = stack[1]
            stack[1] = temp
            displayStack(true)
        }
    }

    fun onMENU(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra("darkTheme", darkTheme)
        intent.putExtra("precisionPrev", precision)
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
//                Toast.makeText(this, data!!.getIntExtra("dark", 0).toString(), Toast.LENGTH_SHORT).show()
                darkTheme = data!!.getIntExtra("dark", 0)
                println("darktheme $darkTheme")
                precision = data!!.getIntExtra("precision", 4)
                println("precision $precision")
//                Toast.makeText(this, precision, Toast.LENGTH_SHORT).show()
                if(darkTheme == 1){
                    background!!.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundColorDark))

                    tvStack1!!.setTextColor(ContextCompat.getColor(this, R.color.white))
                    tvStack2!!.setTextColor(ContextCompat.getColor(this, R.color.white))
                    tvStack3!!.setTextColor(ContextCompat.getColor(this, R.color.white))
                    tvStack4!!.setTextColor(ContextCompat.getColor(this, R.color.white))

                    btn1!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn2!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn3!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn4!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn7!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn8!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn9!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))
                    btn0!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColorDark))

                    btnAc!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnInstructionColorDark))
                    btnDrop!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnInstructionColorDark))
                    btnSwap!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnInstructionColorDark))
                    btnUndo!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnInstructionColorDark))
                    btnExp!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColorDark))
                    btnSqrt!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColorDark))
                    btnMul!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColorDark))
                    btnDiv!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColorDark))
                    btnPlus!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColorDark))
                    btnMinus!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColorDark))
                    btnPlusMinus!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColorDark))
                    btnDot!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColorDark))
                    btnEnter!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnMenuColorDark))
                    btnMenu!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnMenuColorDark))
                }else{
                    background!!.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundColor))

                    tvStack1!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                    tvStack2!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                    tvStack3!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                    tvStack4!!.setTextColor(ContextCompat.getColor(this, R.color.black))

                    btn1!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn2!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn3!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn4!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn7!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn8!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn9!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))
                    btn0!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnDigitColor))

                    btnAc!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnInstructionColor))
                    btnDrop!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnInstructionColor))
                    btnSwap!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnInstructionColor))
                    btnUndo!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnInstructionColor))
                    btnExp!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColor))
                    btnSqrt!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColor))
                    btnMul!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColor))
                    btnDiv!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColor))
                    btnPlus!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColor))
                    btnMinus!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColor))
                    btnPlusMinus!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColor))
                    btnDot!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnOperationColor))
                    btnEnter!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnMenuColor))
                    btnMenu!!.setBackgroundColor(ContextCompat.getColor(this, R.color.btnMenuColor))
                }
            }
        }
}