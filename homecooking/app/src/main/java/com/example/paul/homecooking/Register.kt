package com.example.paul.homecooking

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*

import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import com.example.paul.homecooking.Class.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class Register : AppCompatActivity() {

    var database : DatabaseReference
    var mAuth : FirebaseAuth
    var storage : FirebaseStorage
    private val REQUEST_TAKE_PHOTO = 0
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    var link: String = ""
    init {
        database = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var storageRef : StorageReference = storage.getReferenceFromUrl("gs://datahomecooking.appspot.com/user")
        Avatar()
        DangKi(storageRef)

    }

    private fun Avatar() {
        imageAvatar.setOnClickListener {
            selectImageInAlbum()

        }
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null)
        {
            val contentURI = data!!.data
            try
            {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                val path = saveImage(bitmap)
                imageAvatar.setImageBitmap(bitmap)

            }
            catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                    arrayOf(f.getPath()),
                    arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

    fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

    private fun DangKi(storageRef: StorageReference) {
        btnDangKiTaiKhoan.setOnClickListener {

            var email : String = txtTaiKhoan.text.toString()
            var mkhau : String = txtMatKhau2.text.toString()

            database.child("Admin").addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if(txtAdmin.text.toString().equals(p0!!.value.toString())){
                        if( email != null && txtMatKhau2.text.toString().length >= 6 && txtMatKhau2.text.toString().equals(txtMatKhauAgain.text.toString())){
                            ThemTaiKhoan(email,mkhau,storageRef)
                        }else{
                            Toast.makeText(applicationContext,"Mật Khẩu Của Bạn Không Trùng Nhau Hoặc Ít Hơn 6 Kí Tự hoặc Bạn Chưa Nhập Tài Khoản",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(applicationContext,"Bạn Không có Code Admin",Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun ThemTaiKhoan(email: String, mkhau: String, storageRef: StorageReference) {

        mAuth.createUserWithEmailAndPassword(email,mkhau).addOnCompleteListener(this) { task: Task<AuthResult> ->
            if(task.isSuccessful){
                var tenhinh: StorageReference = storageRef.child(email+".jpg")

                imageAvatar.isDrawingCacheEnabled()
                imageAvatar.buildDrawingCache()

                var bitmap : Bitmap = imageAvatar.getDrawingCache()
                var baos : ByteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
                var data : ByteArray = baos.toByteArray()

                var uploadTask : UploadTask = tenhinh.putBytes(data)
                uploadTask.addOnFailureListener {
                    Toast.makeText(applicationContext,"ko upload dc hinh",Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener { it : UploadTask.TaskSnapshot ->
                    link = it.downloadUrl.toString()
                    Log.d("CCC",link)
                    var taikhoan : User = User(email,link)
                    database.child("User").push().setValue(taikhoan)
                    var intent: Intent = Intent(this,Login::class.java)
                    startActivity(intent)
                }
                Toast.makeText(applicationContext,"Đăng Kí Thành Công",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext,"Email Đã Được Đăng Kí Trước Đây",Toast.LENGTH_SHORT).show()
            }

        }
    }
}


