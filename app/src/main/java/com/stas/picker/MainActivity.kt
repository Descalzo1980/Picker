package com.stas.picker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stas.picker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myBottomSheetFragment = PickerFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, myBottomSheetFragment)
        transaction.commit()
    }
}