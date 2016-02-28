package com.link519.simplecalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import butterknife.bindView
import kotlinx.android.synthetic.main.calculator.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)

        var calculator = Calculator(label)
        val numbers = arrayListOf(zero, one, two, three,
                four, five, six, seven, eight, nine)

        for(num in numbers) {
            num.setOnClickListener{ v -> calculator.input(numbers.indexOf(num)) }
        }

        plus.setOnClickListener{ v -> calculator.input('+') }
        minus.setOnClickListener{ v -> calculator.input('-') }
        mul.setOnClickListener{ v -> calculator.input('*') }
        div.setOnClickListener{ v -> calculator.input('/') }
        del.setOnClickListener{ v -> calculator.delete() }
        ac.setOnClickListener{ v -> calculator.clear() }
        equal.setOnClickListener{ v -> calculator.calculate() }
        point.setOnClickListener{ v -> calculator.input('.') }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
