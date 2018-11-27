package com.example.paul.homecooking.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.paul.homecooking.Class.OrderFood
import com.example.paul.homecooking.R
import com.example.paul.homecooking.ThucDon

class AdapterOrderFood (var context: Context, var listOrder: ArrayList<OrderFood>, var tenBan: String) : BaseAdapter() {

    class ViewHolder(row: View){
        var txtOrderFood: TextView
        var imageOrderFood: ImageView
        var layoutOrderFood: LinearLayout

        init {
            txtOrderFood = row.findViewById(R.id.txtOrderFood)
            imageOrderFood = row.findViewById(R.id.imageOrderFood)
            layoutOrderFood = row.findViewById(R.id.layoutOrderFood)
        }
    }

    override fun getView(position: Int, converView: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(converView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_order_food,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = converView
            viewHolder = converView.tag as ViewHolder
        }

        var orderFood: OrderFood = getItem(position) as OrderFood

        viewHolder.txtOrderFood.setText(orderFood.getLoaiThucAn())
        viewHolder.imageOrderFood.setImageResource(orderFood.getHinhThucAn())

        viewHolder.layoutOrderFood.setOnClickListener {
            var intent: Intent = Intent(context,ThucDon::class.java)
            intent.putExtra("Title",orderFood.getLoaiThucAn())
            intent.putExtra("Code",orderFood.getCode())
            intent.putExtra("TenBan",tenBan)
            context.startActivity(intent)
        }
        return view as View
    }

    override fun getItem(p0: Int): Any {
        return listOrder.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return listOrder.size
    }

}