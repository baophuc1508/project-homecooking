package com.example.paul.homecooking.Class

class OrderFood{

    private var LoaiThucAn : String = ""
    private var HinhLoaiThucAn: Int = 0
    private var Code: String = ""

    constructor()

    constructor(loaiThucAn: String,hinhThucAn: Int, code : String){
        LoaiThucAn = loaiThucAn
        HinhLoaiThucAn = hinhThucAn
        Code = code
    }

    fun setLoaiThucAn (loaiThucAn: String){
        LoaiThucAn = loaiThucAn
    }

    fun getLoaiThucAn(): String{
        return LoaiThucAn
    }

    fun setHinhThucAn (hinhThucAn: Int){
        HinhLoaiThucAn = hinhThucAn
    }

    fun getHinhThucAn(): Int{
        return HinhLoaiThucAn
    }

    fun setCode (code: String){
        Code = code
    }

    fun getCode(): String{
        return Code
    }

}