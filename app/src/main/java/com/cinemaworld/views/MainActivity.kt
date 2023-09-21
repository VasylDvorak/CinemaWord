package com.cinemaworld.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cinemaworld.R
import com.cinemaworld.databinding.ActivityMainBinding
import com.cinemaworld.views.description.IOnBackPressed
import com.cinemaworld.views.main_fragment.MainFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setCurrentFragment(MainFragment())
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            addToBackStack("")
            commitAllowingStateLoss()
        }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.flFragment)
        if ((fragment as? IOnBackPressed)?.onBackPressed()?.not() == true) {
            super.onBackPressed()
        }
    }
}