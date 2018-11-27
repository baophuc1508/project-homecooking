package com.example.paul.homecooking.Class

class BanAn{
    private var STT : String = ""
    private var TenBan : String = ""
    private var TrangThai: String = ""

    constructor()

    constructor(stt: String , tenBan: String,trangThai: String){
        STT = stt
        TenBan = tenBan
        TrangThai = trangThai
    }



    fun setSTT(stt: String){
        STT = stt
    }

    fun getSTT(): String{
        return STT
    }

    fun setTenBan (tenBan: String){
        TenBan = tenBan
    }

    fun getTenBan(): String{
        return TenBan
    }

    fun setTrangThai (trangThai: String){
        TrangThai = trangThai
    }

    fun getTrangThai(): String{
        return TrangThai
    }

}