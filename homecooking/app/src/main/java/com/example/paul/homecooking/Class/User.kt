package com.example.paul.homecooking.Class

import kotlin.math.E

class User {
    private var Email : String = ""
    private var Link : String = ""

    constructor()

    constructor(email: String , link: String){
        Email = email
        Link = link
    }



    fun setEmail(email : String){
        Email = email
    }

    fun getEmail(): String{
        return Email
    }

    fun setLink (link : String){
        Link = link
    }

    fun getLink(): String{
        return Link
    }
}