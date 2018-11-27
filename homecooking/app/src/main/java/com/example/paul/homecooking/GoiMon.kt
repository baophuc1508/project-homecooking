package com.example.paul.homecooking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.paul.homecooking.Adapter.AdapterOrderFood
import com.example.paul.homecooking.Adapter.AdapterTable
import com.example.paul.homecooking.Class.BanAn
import com.example.paul.homecooking.Class.OrderFood
import kotlinx.android.synthetic.main.activity_goi_mon.*
import java.util.ArrayList

class GoiMon : AppCompatActivity() {

    var listOrderFood : ArrayList<OrderFood> = ArrayList()
    var adapterOrderFood: AdapterOrderFood? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goi_mon)

        val intent = intent
        var tenBan = intent.getStringExtra("TenBan")

        listOrderFood.add(OrderFood("Món Chính",R.drawable.mainfood,"MainFood"))
        listOrderFood.add(OrderFood("Món Lẩu",R.drawable.hotpot,"HotPot"))
        listOrderFood.add(OrderFood("Món Nướng",R.drawable.grill,"Grill"))
        listOrderFood.add(OrderFood("Giải Khát",R.drawable.drink,"Drink"))
        listOrderFood.add(OrderFood("Tráng Miệng",R.drawable.dessert,"Dessert"))
        listOrderFood.add(OrderFood("Trái Cây",R.drawable.fruits,"Fruits"))

        adapterOrderFood = AdapterOrderFood(this,listOrderFood,tenBan)
        gripGoiMon.adapter = adapterOrderFood



    }
}
