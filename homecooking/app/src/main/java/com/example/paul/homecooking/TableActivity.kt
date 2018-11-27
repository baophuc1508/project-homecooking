package com.example.paul.homecooking

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.paul.homecooking.Adapter.AdapterTable
import com.example.paul.homecooking.Class.BanAn
import kotlinx.android.synthetic.main.activity_table.*
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.squareup.picasso.Picasso
import java.util.*


class TableActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var listMonAn : ArrayList<BanAn> = ArrayList()
    var database : DatabaseReference
    var  quyenAdmin: Int = 0
    var taikhoan: String = ""
    var linkHinhAnh: String = ""
    var keyUser: String = ""
    var adapterTable: AdapterTable? = null
    var shatePre : SharedPreferences? = null

    init {
        database = FirebaseDatabase.getInstance().reference
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        shatePre = getSharedPreferences("sharePre", Context.MODE_PRIVATE)
        taikhoan = shatePre!!.getString("TaiKhoan","")



        setDrawerLayout()
        navigation_view.setNavigationItemSelectedListener(this)

        prepareListTable()
//        listMonAn.add(BanAn("0","Bàn Số 1","1"))
//        listMonAn.add(BanAn("1","Bàn Số 2","0"))
//        listMonAn.add(BanAn("2","Bàn Số 3","0"))
//        listMonAn.add(BanAn("3","Bàn Số 4","1"))
//        listMonAn.add(BanAn("4","Bàn Số 5","0"))
//        listMonAn.add(BanAn("5","Bàn Số 6","1"))
//        listMonAn.add(BanAn("6","Bàn Số 7","0"))
//        listMonAn.add(BanAn("7","Bàn Số 8","1"))

        adapterTable = AdapterTable(this, listMonAn)
        lvTable.adapter = adapterTable


    }

    private fun prepareListTable() {
        database.child("Table").addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

                var banAn = p0!!.getValue(BanAn::class.java)

                var i : Int = banAn!!.getSTT().toInt()

                listMonAn.set(i,banAn)
                adapterTable!!.notifyDataSetChanged()

            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                var banAn = p0!!.getValue(BanAn::class.java)
                listMonAn.add(banAn!!)
                adapterTable!!.notifyDataSetChanged()

            }

            override fun onChildRemoved(p0: DataSnapshot?) {

                var banAn = p0!!.getValue(BanAn::class.java)

                var i : Int = banAn!!.getSTT().toInt()
                listMonAn.removeAt(i)
                adapterTable!!.notifyDataSetChanged()
            }

        })

    }

    private fun GetLink(imageUser: ImageView) {
        database.child("User")
                .orderByChild("email")
                .equalTo(taikhoan)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        for (objSnapshot in p0!!.getChildren()) {
                            keyUser = objSnapshot.getKey().toString()
                        }

                        database.child("User").child(keyUser).child("link").addValueEventListener(object :ValueEventListener{
                            override fun onCancelled(p0: DatabaseError?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onDataChange(p0: DataSnapshot?) {
                                //Log.d("DDD",p0!!.getValue().toString())
                                linkHinhAnh = p0!!.getValue().toString()
                                Picasso.get().load(linkHinhAnh).into(imageUser);
                            }
                        })
                    }

                })
    }

    private fun setDrawerLayout() {

        setSupportActionBar(toolbar)
        var actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setTitle("")

        var nav_view = findViewById(R.id.navigation_view) as NavigationView
        var header = nav_view.getHeaderView(0)



        var txtTaiKhoan : TextView = header.findViewById(R.id.txtEmail)
        var imageUser : ImageView = header.findViewById(R.id.imageUser)
        GetLink(imageUser)

        Log.d("EEE",linkHinhAnh)
        txtTaiKhoan.setText(taikhoan)



        var actionBarDrawerToggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        actionBarDrawerToggle.syncState()

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.menuThemBan -> {

                if(quyenAdmin == 1){
                    Toast.makeText(applicationContext,"thêm bàn",Toast.LENGTH_SHORT).show()
                    ThemBan()
                }else{
                    Toast.makeText(applicationContext,"Bạn Không Phải Admin",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.menuXoaBan -> {
                if(quyenAdmin == 1){
                    Toast.makeText(applicationContext,"Xóa bàn",Toast.LENGTH_SHORT).show()
                    XoaBan()
                }else{
                    Toast.makeText(applicationContext,"Bạn Không Phải Admin",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.menuAdmin -> {
                LayQuyenAdmin()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun XoaBan() {
        var keyTable : String = ""
        database.child("Table")
                .orderByChild("stt")
                .equalTo((listMonAn.size-1).toString())
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        for (objSnapshot in p0!!.getChildren()) {
                             keyTable = objSnapshot.getKey().toString()
                        }

                        database.child("Table").child(keyTable).removeValue()
                    }

                })
    }

    private fun ThemBan() {
        var count : Int = listMonAn.size
        var banAn : BanAn = BanAn(count.toString(),"Bàn Số " +(count+1),"0")
        database.child("Table").push().setValue(banAn)
    }

    private fun LayQuyenAdmin() {
        val dialog = AlertDialog.Builder(this)
        var inflater : LayoutInflater = this.layoutInflater
        var view : View = inflater.inflate(R.layout.layout_quyen_admin, null)

        dialog.setView(view)

        val txtCodeAdmin = view.findViewById(R.id.txtCodeAdmin) as EditText

        dialog.setNegativeButton("OK",DialogInterface.OnClickListener { dialogInterface, i ->
            var code: String = txtCodeAdmin.text.toString()
            database.child("Admin").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if(code.equals(p0!!.value.toString())){
                        quyenAdmin = 1
                        Toast.makeText(applicationContext,"Bạn Đã Là Admin",Toast.LENGTH_SHORT).show()
                        dialogInterface.dismiss()
                    }else{
                        Toast.makeText(applicationContext,"Bạn Nhập Sai Code Admin",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        })


        var alertDialog: AlertDialog = dialog.create()
        alertDialog.show()

        var button : Button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        button.setTextColor(resources.getColor(R.color.colorBlack))
    }
}


