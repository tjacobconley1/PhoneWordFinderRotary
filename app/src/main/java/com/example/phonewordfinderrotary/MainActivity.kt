package com.example.phonewordfinderrotary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import com.example.phonewordfinderrotary.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val wordAdapter = Adapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // removes the bar at the top of the activity
        try { this.supportActionBar!!.hide()
        } catch (e: NullPointerException) { }



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wordRecyclerview.adapter = wordAdapter

        binding.rotraryDialer.setDialer(object : Dialer.KeyDialler {
            override fun dial(number: Int) {
                binding.inputEt.append(number.toString())
            }
        })

        binding.imageView.setOnClickListener {
            binding.inputEt.text.clear()
        }
        binding.inputEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                wordAdapter.words = if (p0.toString().isEmpty()) listOf() else FindWord.findWords(
                    p0.toString().toCharArray()
                )
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }





}


