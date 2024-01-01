package com.rajit.todoapp_cleanarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rajit.todoapp_cleanarchitecture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }
}