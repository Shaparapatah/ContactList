package com.example.contactlist.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactlist.R
import com.example.contactlist.databinding.ActivityMainBinding
import com.example.contactlist.main.ui.fragments.ContactFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_holder_tablet, ContactFragment.newInstance())
            .commit()
    }
}