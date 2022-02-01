package com.gmail.weronikapios7.catchat

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import catchat.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private const val  NUM_PAGES = 2
class JoinActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val viewPager: ViewPager2 = findViewById(R.id.joinViewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabNames = listOf("Login", "Register")
            tab.text = tabNames[position]
        }.attach()
    }


    override fun onBackPressed() {
        if(viewPager.currentItem ==0) {
            //if the user is looking at the first step, handle the back button
            super.onBackPressed()
        }else {
            //otherwise select the previous step
            viewPager.currentItem = viewPager.currentItem -1
        }
    }

    private inner class ScreenSlidePagerAdapter(fragment: JoinActivity) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position){
                0 -> LoginFragment()
                1 -> RegisterFragment()
                else -> RegisterFragment()
            }
        }
    }
}