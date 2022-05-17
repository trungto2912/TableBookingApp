package com.example.appdoan.model

class CartData {
    var idCart:String? = null
    var idUser:String? = null
    var idRes:String? = null
    var nameRes:String? = null
    var imgRes:String? = null
    var adress:String? = null
    var day:String? = null
    var hour:String? = null
    var adult:String? = null
    var children:String? = null
    var note:String? = null

    constructor()
    constructor(
        idCart : String?,
        idUser : String?,
        idRes : String?,
        nameRes : String?,
        imgRes : String?,
        adress : String?,
        day : String?,
        hour : String?,
        adult : String?,
        children : String?,
        note : String?
    ) {
        this.idCart = idCart
        this.idUser = idUser
        this.idRes = idRes
        this.nameRes = nameRes
        this.imgRes = imgRes
        this.adress = adress
        this.day = day
        this.hour = hour
        this.adult = adult
        this.children = children
        this.note = note
    }

}