package com.example.paul.homecooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.paul.homecooking.Class.SoLuong
import com.example.paul.homecooking.R
import com.google.firebase.database.DatabaseReference

class AdapterThanhToan (var context: Context, var listThanhTien: ArrayList<SoLuong>) : BaseAdapter() {

    class ViewHolder(row: View){
        var txtTenMonAnThanhToan: TextView
        var txtGiaMonAnThanhToan: TextView
        var txtSoLuongThanhToan: TextView
        var txtThanhTienThanhToan: TextView

        init {
            txtTenMonAnThanhToan = row.findViewById(R.id.txtTenMonAnThanhToan)
            txtGiaMonAnThanhToan = row.findViewById(R.id.txtGiaMonAnThanhToan)
            txtSoLuongThanhToan = row.findViewById(R.id.txtSoLuongThanhToan)
            txtThanhTienThanhToan = row.findViewById(R.id.txtThanhTienThanhToan)
        }
    }

    override fun getView(position: Int, converView: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(converView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_thanh_toan,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = converView
            viewHolder = converView.tag as ViewHolder
        }

        var soLuong: SoLuong = getItem(position) as SoLuong

        viewHolder.txtTenMonAnThanhToan.setText(soLuong.getTenThucAn())
        viewHolder.txtGiaMonAnThanhToan.setText("Giá: " + soLuong.getGia().toString() + " Đồng")
        viewHolder.txtSoLuongThanhToan.setText("Số Lượng: " + soLuong.getSoLuong().toString())
        viewHolder.txtThanhTienThanhToan.setText("Tổng Tiền: " + soLuong.getThanhTien().toString() + " Đồng")


        return view as View
    }

    override fun getItem(p0: Int): Any {
        return listThanhTien.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return listThanhTien.size
    }

}