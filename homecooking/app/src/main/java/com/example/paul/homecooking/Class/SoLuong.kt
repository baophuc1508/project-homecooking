package com.example.paul.homecooking.Class

class SoLuong{

    private var TenThucAn : String = ""
    private var Gia: Long = 0
    private var SoLuong: Int = 0
    private var ThanhTien: Long = 0

    constructor()

    constructor(tenThucAn: String, gia : Long, soLuong: Int, thanhTien: Long){
        TenThucAn = tenThucAn
        Gia = gia
        SoLuong = soLuong
        ThanhTien = thanhTien
    }

    fun setTenThucAn (tenThucAn: String){
        TenThucAn = tenThucAn
    }

    fun getTenThucAn(): String{
        return TenThucAn
    }

    fun setGia (gia: Long){
        Gia = gia
    }

    fun getGia(): Long{
        return Gia
    }

    fun setSoLuong (soLuong: Int){
        SoLuong = soLuong
    }

    fun getSoLuong(): Int{
        return SoLuong
    }

    fun setThanhTien (thanhTien: Long){
        ThanhTien = thanhTien
    }

    fun getThanhTien(): Long{
        return ThanhTien
    }

}