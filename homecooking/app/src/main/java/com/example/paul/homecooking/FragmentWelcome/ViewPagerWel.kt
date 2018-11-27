package com.example.paul.homecooking.FragmentWelcome

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerWel(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val fragmetList: ArrayList<Fragment> = ArrayList()

    override fun getItem(p0: Int): Fragment {
        return  fragmetList.get(p0)
    }

    override fun getCount(): Int {
        return fragmetList.size
    }

    fun AddFragment(fragment: Fragment){
        fragmetList.add(fragment)
    }



}