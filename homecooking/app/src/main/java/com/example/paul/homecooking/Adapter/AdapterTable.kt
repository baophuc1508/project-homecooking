package com.example.paul.homecooking.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.paul.homecooking.Class.BanAn
import com.example.paul.homecooking.GoiMon
import com.example.paul.homecooking.R
import com.example.paul.homecooking.ThanhToan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AdapterTable (var context: Context, var listTable: ArrayList<BanAn>): BaseAdapter(){

    class ViewHolder(row: View){
        var txtTable: TextView
        var imageTable: ImageView
        var layoutTable: LinearLayout

        init {
            txtTable = row.findViewById(R.id.txtTable)
            imageTable = row.findViewById(R.id.imageTable)
            layoutTable = row.findViewById(R.id.layoutTable)
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, converView: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(converView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_ban_an,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = converView
            viewHolder = converView.tag as ViewHolder
        }

        var  banAn : BanAn = getItem(position) as BanAn
        viewHolder.txtTable.text = banAn.getTenBan()
        if(banAn.getTrangThai().toString().equals("1")){
            viewHolder.imageTable.setImageResource(R.drawable.tableon)
        }else{
            viewHolder.imageTable.setImageResource(R.drawable.tableoff)
        }

        viewHolder.layoutTable.setOnClickListener {
            val dialog = AlertDialog.Builder(context)

            val dialogview = LayoutInflater.from(context).inflate(R.layout.dialog_goi_mon, null)

            dialog.setView(dialogview)

            val txtTitleGoiMon = dialogview.findViewById(R.id.txtTitleGoiMon) as TextView
            val btnGoiMon = dialogview.findViewById(R.id.btnGoiMon) as Button
            val btnThanhToan = dialogview.findViewById(R.id.btnThanhToan) as Button

            txtTitleGoiMon.setText(banAn.getTenBan())

            btnGoiMon.setOnClickListener {
                Toast.makeText(context,"Gọi Món",Toast.LENGTH_SHORT).show()
                var intent : Intent = Intent(context,GoiMon::class.java)
                intent.putExtra("TenBan",banAn.getTenBan())
                context.startActivity(intent)
            }

            btnThanhToan.setOnClickListener {
                Toast.makeText(context,"Thanh Toán",Toast.LENGTH_SHORT).show()
                var intent : Intent = Intent(context,ThanhToan::class.java)
                intent.putExtra("TenBan",banAn.getTenBan())
                context.startActivity(intent)
            }

            dialog.setNegativeButton("Hủy", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })

            var alertDialog: AlertDialog = dialog.create()
            alertDialog.show()
            var button : Button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            button.setTextColor(R.color.colorBlack)

        }
        return  view as View
    }

    override fun getItem(p0: Int): Any {
        return listTable.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return listTable.size
    }

}