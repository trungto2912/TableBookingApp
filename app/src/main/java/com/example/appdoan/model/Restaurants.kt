package com.example.appdoan.model

class Restaurants {
    var id:String? = null
    var name:String? = null
    var img:String? = null
    var adress:String? = null
    var describe:String? = null
    var price:String? = null
    var clientTime:String? = null
    var discount:String? = null
    var cateID:String? = null

    constructor()
    constructor(
        id : String?,
        name : String?,
        img : String?,
        adress : String?,
        describe : String?,
        price : String?,
        clientTime : String?,
        discount : String?,
        cateID : String?
    ) {
        this.id = id
        this.name = name
        this.img = img
        this.adress = adress
        this.describe = describe
        this.price = price
        this.clientTime = clientTime
        this.discount = discount
        this.cateID = cateID
    }

}