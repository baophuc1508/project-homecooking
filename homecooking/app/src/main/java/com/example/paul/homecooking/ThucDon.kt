package com.example.paul.homecooking

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.paul.homecooking.Adapter.AdapterFood
import com.example.paul.homecooking.Class.Food
import com.example.paul.homecooking.Class.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_thuc_don.*
import kotlinx.android.synthetic.main.dialog_add_food.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ThucDon : AppCompatActivity() {

    var title: String = ""
    var code: String = ""
    var shatePre : SharedPreferences? = null
    var taikhoan: String = ""
    private val REQUEST_TAKE_PHOTO = 0
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    lateinit var imageMonAn: ImageView
    var database : DatabaseReference
    var storage : FirebaseStorage
    lateinit var storageRef : StorageReference
    var check: Boolean = false
    var listFood: ArrayList<Food> = ArrayList()
    var adapterFood: AdapterFood? = null
    var tenBan : String = ""

    init {
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thuc_don)

        storageRef  = storage.getReferenceFromUrl("gs://datahomecooking.appspot.com/food")

        val intent = intent
        title = intent.getStringExtra("Title")
        tenBan = intent.getStringExtra("TenBan")
        code = intent.getStringExtra("Code")

        shatePre = getSharedPreferences("sharePre", Context.MODE_PRIVATE)
        taikhoan = shatePre!!.getString("TaiKhoan","")

        txtTitleThucDon.setText(title)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        Toast.makeText(applicationContext,"taikhoan: " + taikhoan + " --- code: " + code + " --- Tên Bàn: " + tenBan,Toast.LENGTH_SHORT).show()

        prepareListFood()

        adapterFood = AdapterFood(this, listFood,tenBan,database)
        lvThucDon.adapter = adapterFood


    }

    private fun prepareListFood() {
        database.child(code).addChildEventListener(object : ChildEventListener {
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
                var food = p0!!.getValue(Food::class.java)
                listFood.add(food!!)
                adapterFood!!.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.item_menu_thuc_don,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.menuAdd -> {
                ThemMonAn()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun ThemMonAn() {
        val dialog = AlertDialog.Builder(this)
        var inflater : LayoutInflater = this.layoutInflater
        var view : View = inflater.inflate(R.layout.dialog_add_food, null)

        dialog.setView(view)

        val txtTenMonAn = view.findViewById(R.id.txtTenMonAn) as EditText
        val txtGiaMonAn = view.findViewById(R.id.txtGiaMonAn) as EditText
        imageMonAn = view.findViewById(R.id.imageMonAn) as ImageView

        imageMonAn.setOnClickListener {
            ChonHinhMonAn(imageMonAn)
        }

        dialog.setNegativeButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
            if(check){
                var tenhinh: StorageReference = storageRef.child(txtTenMonAn.text.toString() +".jpg")

                imageMonAn.isDrawingCacheEnabled()
                imageMonAn.buildDrawingCache()

                var bitmap : Bitmap = imageMonAn.getDrawingCache()
                var baos : ByteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
                var data : ByteArray = baos.toByteArray()

                var uploadTask : UploadTask = tenhinh.putBytes(data)
                uploadTask.addOnFailureListener {
                    Toast.makeText(applicationContext,"ko upload dc hinh",Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener { it : UploadTask.TaskSnapshot ->
                    var link: String = it.downloadUrl.toString()
                    Log.d("CCC",link)
                    var food : Food = Food(txtTenMonAn.text.toString(),link,txtGiaMonAn.text.toString())
                    database.child(code).push().setValue(food)
                }
                Toast.makeText(applicationContext,"Upload Thành Công",Toast.LENGTH_SHORT).show()
                dialogInterface.dismiss()
            }

        })


        var alertDialog: AlertDialog = dialog.create()
        alertDialog.show()

        var button : Button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        button.setTextColor(resources.getColor(R.color.colorBlack))
    }

    private fun ChonHinhMonAn(imageMonAn: ImageView) {
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
                imageMonAn.setImageBitmap(bitmap)
                check = true

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

}
