package com.varsivo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.varsivo.app.bursaries.BursariesFragment
import com.varsivo.app.common.PlaceholderFragment
import com.varsivo.app.databinding.ActivityMainBinding
import com.varsivo.app.explore.ExploreFragment
import com.varsivo.app.home.HomeFragment
import com.varsivo.app.tracker.TrackerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            binding.tvScreenTitle.text = item.title
            showTab(item.itemId)
            true
        }

        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.nav_home
        }
    }

    fun openTab(itemId: Int) {
        binding.bottomNav.selectedItemId = itemId
    }

    private fun showTab(itemId: Int) {
        val fragment: Fragment = when (itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_explore -> ExploreFragment()
            R.id.nav_bursaries -> BursariesFragment()
            R.id.nav_tracker -> TrackerFragment()
            else -> PlaceholderFragment.newInstance(getString(R.string.nav_profile))
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}