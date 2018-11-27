package com.example.paul.homecooking

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    var mAuth : FirebaseAuth
    var shatePre : SharedPreferences? = null
    init {
        mAuth = FirebaseAuth.getInstance()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        shatePre = getSharedPreferences("sharePre",Context.MODE_PRIVATE)

        Load()
        DangNhap()
        Register()
    }

    private fun Load() {
        var kt = shatePre!!.getBoolean("check",false)
        if(kt){
            var nhanTen: String = shatePre!!.getString("TenDangNhap","")
            var nhanMKhau: String = shatePre!!.getString("MatKhau","")
            txtTaiKhoan.setText(nhanTen)
            txtMatKhau.setText(nhanMKhau)
            cbSave.isChecked = true
        }else{
            cbSave.isChecked = false
        }

    }

    private fun DangNhap() {
        btnDangNhap.setOnClickListener {
            var email : String = txtTaiKhoan.text.toString()
            var mkhau : String = txtMatKhau.text.toString()
            mAuth.signInWithEmailAndPassword(email,mkhau).addOnCompleteListener { task: Task<AuthResult> ->
                if(task.isSuccessful){
                    LuuTaiKhoan(email,mkhau)

                }else{
                    Toast.makeText(applicationContext,"Đăng Nhập Thất Bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun LuuTaiKhoan(email: String, mkhau: String) {
        if(cbSave.isChecked){
            var editor : SharedPreferences.Editor = shatePre!!.edit()
            editor.putString("TenDangNhap",email)
            editor.putString("MatKhau",mkhau)
            editor.putBoolean("check",cbSave.isChecked)
            editor.commit()
            ThanhCong(email)
        }else{
            ThanhCong(email)
        }
    }

    private fun ThanhCong(email: String) {
        Toast.makeText(applicationContext,"Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show()
        var editor : SharedPreferences.Editor = shatePre!!.edit()
        editor.putString("TaiKhoan",email)
        editor.commit()
        var intent: Intent = Intent(this,TableActivity::class.java)
        intent.putExtra("TaiKhoan",email)
        startActivity(intent)
    }

    private fun Register() {
        btnDangKi.setOnClickListener {
            val intent : Intent = Intent(this,Register::class.java)
            startActivity(intent)
        }
    }
}
