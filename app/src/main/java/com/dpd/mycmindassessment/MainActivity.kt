package com.dpd.mycmindassessment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.dpd.mycmindassessment.databinding.ActivityMainBinding
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val specialCharactersPattern = Pattern.compile("[^a-zA-Z0-9\\s]")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding) {
            textInputEditText.addTextChangedListener {
                textInputLength.text = it?.length.toString()
                textInputReverse.text = it?.reversed()
                textInputAppend.text = getString(R.string.text_hello_content, it.toString())
                textInputNumeric.text = it?.isDigitsOnly().toString()
                if (it.toString().isNotEmpty() && containsSpecialCharacters(it.toString())) {
                    textInputEditText.error = getString(R.string.error_special_char_detection)
                }
            }
            clearAction.setOnClickListener {
                textInputEditText.apply {
                    text = null
                    error = null
                }
            }
        }
    }
    private fun containsSpecialCharacters(text: String): Boolean {
        return specialCharactersPattern.matcher(text).find()
    }
}