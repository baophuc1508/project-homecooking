package com.example.paul.homecooking

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.paul.homecooking.Adapter.AdapterTable
import com.example.paul.homecooking.Adapter.AdapterThanhToan
import com.example.paul.homecooking.Class.SoLuong
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_table.*
import kotlinx.android.synthetic.main.activity_thanh_toan.*
import kotlinx.android.synthetic.main.item_thanh_toan.*

class ThanhToan : AppCompatActivity() {

    var listSoLuong : ArrayList<SoLuong> = ArrayList()
    var database : DatabaseReference
    var adaterThanhToan: AdapterThanhToan? = null
    var title: String = ""
    var tong : Long = 0
    var keyUser : String = ""

    init {
        database = FirebaseDatabase.getInstance().reference
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanh_toan)

        val intent = intent
        title = intent.getStringExtra("TenBan")
        txtTitleThanhToan.setText(title)

        prepareListSoLuong()

        adaterThanhToan = AdapterThanhToan(this, listSoLuong)
        lvThanhToan.adapter = adaterThanhToan

        btnThanhToanTien.setOnClickListener {
            ThanhToanTien()
        }
    }

    private fun ThanhToanTien() {
        database.child("Table")
                .orderByChild("tenBan")
                .equalTo(title)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        for (objSnapshot in p0!!.getChildren()) {
                            keyUser = objSnapshot.getKey().toString()
                        }

                        database.child("Table").child(keyUser).child("trangThai").setValue("0")
                    }

                })
        database.child("ThanhToan").child(title).removeValue()

        var intent : Intent = Intent(this,TableActivity::class.java)
        startActivity(intent)



    }

    private fun prepareListSoLuong() {
        database.child("ThanhToan").child(title).addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {

               // txtTongTien.setText("")
                var soLuong = p0!!.getValue(SoLuong::class.java)
                listSoLuong.add(soLuong!!)
                adaterThanhToan!!.notifyDataSetChanged()

                tong = tong + soLuong.getThanhTien()

                txtTongTien.setText(tong.toString() + " Đồng")
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                adaterThanhToan!!.notifyDataSetChanged()
            }

        })
    }


}
