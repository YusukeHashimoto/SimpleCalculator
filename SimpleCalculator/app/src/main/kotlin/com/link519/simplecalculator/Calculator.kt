package com.link519.simplecalculator

import android.widget.TextView
import java.util.*

/**
 * Created by yusuke on 10/25/15.
 */
class Calculator(label: TextView) {
    val label = label
    var expression = StringBuilder("")

    fun input(c: Char) {
        expression.append(c)
        show()
    }

    fun input(i: Int) {
        input('0' + i)
    }

    fun show() {
        val text = expression.toString()
        label.text = if(text == "") "0" else text
    }

    fun delete() {
        if(expression.length > 0) {
            expression.deleteCharAt(expression.length - 1)
            show()
        }
    }

    fun calculate() {
        if(expression.length < 1 || !expression[expression.length-1].isDigit()) {
            //if expression is empty or ends with operator
            return
        }

        val array = arrayListOf("(")

        val builder = StringBuilder(expression.pop().toString())
        while (expression.charAt(0).isDigit() || expression.charAt(0) == '.') {
            builder.append(expression.pop().toString()) //add first number
        }
        array.add(builder.toString())

        while (expression.length > 0) {
            array.add(expression.pop().toString()) //add operator

            val builder = StringBuilder("")
            while (expression.length > 0
                    && (expression.charAt(0).isDigit() || expression.charAt(0).equals('.'))) {
                builder.append(expression.pop())
            }
            array.add(builder.toString())
        }
        array.add(")")
        try {
            //val revPolish = array.toRevPolish()
            label.text = exec(array.toRevPolish()).toString()
        } catch(e: Exception) {
            label.text = e.toString()
        }

        expression = StringBuilder("") //initialize expression after calculation
    }

     fun exec(revPolish: List<String>): Double {
         val stack = Stack<Double>()
         for(x in revPolish) {
             if(x.isNumber()) { //if x is number
                 stack.push(x.toDouble())
             } else { //if x is operator
                 val a = stack.pop()
                 val b = stack.pop()
                 var result = when(x) {
                     "+" -> b + a
                     "-" -> b - a
                     "*" -> b * a
                     "/" -> b / a
                     else -> -1.0
                 }
                 stack.push(result)
             }
         }
         return stack.pop()
     }

    fun clear() {
        expression = StringBuilder("")
        show()
    }

    /**
     * return and remove first character
     */
    fun StringBuilder.pop() : Char {
        val c = this[0]
        deleteCharAt(0)
        return c
    }


    val orderOfOperator = HashMap<String, Int>()

    fun ArrayList<String>.toRevPolish(): List<String> {
        val stack = Stack<String>()
        val list = ArrayList<String>()

        if(orderOfOperator.isEmpty()) {
            orderOfOperator.put("(", 0)
            orderOfOperator.put(")", 0)
            orderOfOperator.put("+", 1)
            orderOfOperator.put("-", 1)
            orderOfOperator.put("*", 2)
            orderOfOperator.put("/", 2)
        }

        stack.push(this.removeAt(0))

        while(this.size > 0) {
            val x = this.removeAt(0)
            if(x.isNumber()) { //if x is number
                list.add(x)
            } else { //if x is operator
                while(!stack.isEmpty() && orderOfOperator[stack[stack.size-1]] as Int >= orderOfOperator[x] as Int) {
                    list.add(stack.pop())
                }
                stack.push(x)
            }
        }

        list.removeAt(list.size - 1)
        return list
    }

    fun String.isNumber(): Boolean {
        for(s in this) {
            if(!s.isDigit() && !s.equals('.')) return false;
        }
        return true;
    }

    fun StringBuilder.charAt(n : Int): Char {
        return toString()[n]
    }
}