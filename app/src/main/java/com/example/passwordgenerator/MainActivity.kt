package com.example.passwordgenerator


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.security.SecureRandom
import java.util.Collections

class MainActivity : AppCompatActivity() {
    private val LOWER = "abcdefghijklmnopqrstuvwxyz"
    private val UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val DIGITS = "0123456789"
    private val SYMBOLS = "!@#\$%^&*()-_=+[]{};:,.<>?/"

    private val secureRandom = SecureRandom()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvPassword = findViewById<TextView>(R.id.tvPassword)
        val btnGenerate = findViewById<Button>(R.id.btnGenerate)
        val btnCopy = findViewById<Button>(R.id.btnCopy)
        val seekBar = findViewById<SeekBar>(R.id.seekBarLength)
        val tvLength = findViewById<TextView>(R.id.tvLength)
        val cbLower = findViewById<CheckBox>(R.id.cbLower)
        val cbUpper = findViewById<CheckBox>(R.id.cbUpper)
        val cbDigits = findViewById<CheckBox>(R.id.cbDigits)
        val cbSymbols = findViewById<CheckBox>(R.id.cbSymbols)

        // show length while sliding (min displayed length = 4)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val display = maxOf(4, progress)
                tvLength.text = "Length: $display"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnGenerate.setOnClickListener {
            val length = maxOf(4, seekBar.progress)
            val includeLower = cbLower.isChecked
            val includeUpper = cbUpper.isChecked
            val includeDigits = cbDigits.isChecked
            val includeSymbols = cbSymbols.isChecked

            val password = generatePassword(length, includeLower, includeUpper, includeDigits, includeSymbols)
            if (password.isEmpty()) {
                Toast.makeText(this, "Select at least one character type", Toast.LENGTH_SHORT).show()
            } else {
                tvPassword.text = password
            }
        }

        btnCopy.setOnClickListener {
            val password = tvPassword.text.toString()
            if (password.isEmpty() || password == "Your password will appear here") {
                Toast.makeText(this, "Nothing to copy", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("password", password)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePassword(length: Int,
                                 lowerOn: Boolean,
                                 upperOn: Boolean,
                                 digitsOn: Boolean,
                                 symbolsOn: Boolean): String {
        val pools = mutableListOf<String>()
        if (lowerOn) pools.add(LOWER)
        if (upperOn) pools.add(UPPER)
        if (digitsOn) pools.add(DIGITS)
        if (symbolsOn) pools.add(SYMBOLS)

        if (pools.isEmpty()) return ""

        val actualLength = maxOf(length, pools.size) // ensure room for one of each selected
        val chars = mutableListOf<Char>()

        // ensure one char from each selected pool
        for (pool in pools) {
            chars.add(pool[secureRandom.nextInt(pool.length)])
        }

        val combined = pools.joinToString(separator = "")
        for (i in chars.size until actualLength) {
            chars.add(combined[secureRandom.nextInt(combined.length)])
        }

        // shuffle using SecureRandom
        Collections.shuffle(chars, secureRandom)
        return chars.joinToString(separator = "")
    }
}
