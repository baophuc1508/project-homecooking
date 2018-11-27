package com.example.paul.homecooking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ImageView
import com.example.paul.homecooking.FragmentWelcome.Fragment_wel1
import com.example.paul.homecooking.FragmentWelcome.Fragment_wel2
import com.example.paul.homecooking.FragmentWelcome.Fragment_wel3
import com.example.paul.homecooking.FragmentWelcome.ViewPagerWel
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.activity_wellcome.*

class Wellcome : AppCompatActivity() {

    var viewPagerWel : ViewPagerWel? =null
    var fragment_wel1 : Fragment_wel1 = Fragment_wel1()
    var fragment_wel2 : Fragment_wel2 = Fragment_wel2()
    var fragment_wel3 : Fragment_wel3 = Fragment_wel3()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wellcome)



        viewPagerWel = ViewPagerWel(supportFragmentManager)

        viewPagerWel!!.AddFragment(fragment_wel1)
        viewPagerWel!!.AddFragment(fragment_wel2)
        viewPagerWel!!.AddFragment(fragment_wel3)
        viewPager.adapter = viewPagerWel

        SlideDot()
    }

    private fun SlideDot() {
//        var dotsCount : Int = viewPagerWel!!.count
//
//        var dots: ArrayList<ImageView> = ArrayList(dotsCount)
//        Log.d("AAA",dots.size.toString() +  " 1111111aaaaaaaa "+ dotsCount)
//
//        for (j in 1..dotsCount)
//            dots[j] = ImageView(this)
            //dots[j].setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.dotswhite));



        spring_dots_indicator.setViewPager(viewPager)


    }
}
