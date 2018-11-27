package com.example.paul.homecooking.Adapter

import android.support.v7.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.paul.homecooking.Class.Food
import com.example.paul.homecooking.Class.OrderFood
import com.example.paul.homecooking.Class.SoLuong
import com.example.paul.homecooking.R
import com.example.paul.homecooking.ThucDon
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class AdapterFood (var context: Context, var listFood: ArrayList<Food>, var tenBan: String, var database: DatabaseReference) : BaseAdapter() {

    class ViewHolder(row: View){
        var txtCardTenMonAn: TextView
        var txtCardGiaMonAn: TextView
        var imageCardFood: ImageView
        var cardFood: FrameLayout
        init {
            txtCardTenMonAn = row.findViewById(R.id.txtCardTenMonAn)
            txtCardGiaMonAn = row.findViewById(R.id.txtCardGiaMonAn)
            imageCardFood = row.findViewById(R.id.imageCardFood)
            cardFood = row.findViewById(R.id.cardFood)

        }
    }

    override fun getView(position: Int, converView: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(converView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_thuc_an,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = converView
            viewHolder = converView.tag as ViewHolder
        }

        var food: Food = getItem(position) as Food

        viewHolder.txtCardTenMonAn.setText(food.getTenThucAn())
        viewHolder.txtCardGiaMonAn.setText(food.getGia())

        Picasso.get().load(food.getLinkHinh()).into(viewHolder.imageCardFood);

        viewHolder.cardFood.setOnClickListener {

            val dialog = AlertDialog.Builder(context)

            val dialogview1 = LayoutInflater.from(context).inflate(R.layout.dialog_dat_thuc_an, null)

            dialog.setView(dialogview1)

            val txtDialogTenMon = dialogview1.findViewById(R.id.txtDialogTenMon) as TextView
            val imageDialogMonAn = dialogview1.findViewById(R.id.imageDialogMonAn) as ImageView
            val imageAddMon = dialogview1.findViewById(R.id.imageAddMon) as ImageView
            val txtDatMon = dialogview1.findViewById(R.id.txtDatMon) as TextView
            val imageSubMon = dialogview1.findViewById(R.id.imageSubMon) as ImageView
            val btnDatMon = dialogview1.findViewById(R.id.btnDatMon) as Button

            txtDialogTenMon.setText(food.getTenThucAn())

            Picasso.get().load(food.getLinkHinh()).into(imageDialogMonAn);

            imageAddMon.setOnClickListener {
                var i : Int = txtDatMon.text.toString().toInt()
                i = i +1
                txtDatMon.setText(i.toString())
            }

            imageSubMon.setOnClickListener {
                var i : Int = txtDatMon.text.toString().toInt()
                if (i > 0){
                    var i : Int = txtDatMon.text.toString().toInt()
                    i = i - 1
                    txtDatMon.setText(i.toString())
                }else{
                    txtDatMon.setText("0")
                }
            }




            var alertDialog: AlertDialog = dialog.create()
            alertDialog.show()
            var keyUser : String = ""
            btnDatMon.setOnClickListener {

                database.child("Table")
                        .orderByChild("tenBan")
                        .equalTo(tenBan)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onDataChange(p0: DataSnapshot?) {
                                for (objSnapshot in p0!!.getChildren()) {
                                    keyUser = objSnapshot.getKey().toString()
                                }

                                database.child("Table").child(keyUser).child("trangThai").setValue("1")
                            }

                        })
                var soluong: SoLuong = SoLuong(food.getTenThucAn(),food.getGia().toLong(),txtDatMon.text.toString().toInt(),food.getGia().toLong()*txtDatMon.text.toString().toInt())
                database.child("ThanhToan").child(tenBan).push().setValue(soluong)
                alertDialog.dismiss()

            }
        }
        return view as View
    }

    override fun getItem(p0: Int): Any {
        return listFood.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return listFood.size
    }

}