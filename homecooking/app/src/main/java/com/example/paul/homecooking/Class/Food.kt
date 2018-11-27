package com.example.paul.homecooking.Class

class Food{

    private var TenThucAn : String = ""
    private var LinkHinh: String = ""
    private var Gia: String = ""

    constructor()

    constructor(tenThucAn: String,linkHinh: String, gia : String){
        TenThucAn = tenThucAn
        LinkHinh = linkHinh
        Gia = gia
    }

    fun setTenThucAn (tenThucAn: String){
        TenThucAn = tenThucAn
    }

    fun getTenThucAn(): String{
        return TenThucAn
    }

    fun setLinkHinh (linkHinh: String){
        LinkHinh = linkHinh
    }

    fun getLinkHinh(): String{
        return LinkHinh
    }

    fun setGia (gia: String){
        Gia = gia
    }

    fun getGia(): String{
        return Gia
    }

}