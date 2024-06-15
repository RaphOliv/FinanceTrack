package com.hacksprint.financetrack.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hacksprint.financetrack.R
import com.hacksprint.financetrack.fragment.HomeFragment
import com.hacksprint.financetrack.fragment.MainFragment
import com.hacksprint.financetrack.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {


    private lateinit var binding:ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeIcon -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.listaIcon -> {
                    replaceFragment(MainFragment())
                    true
                }



                else -> false
            }
        }

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            binding.bottomNavigation.selectedItemId = R.id.homeIcon
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }

}