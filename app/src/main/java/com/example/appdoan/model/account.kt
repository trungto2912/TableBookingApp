package com.example.appdoan.model

class account {
    var idUser: String? = null
    var userName:String? = null
    var phone:String? = null
    var email:String? = null
    var passWord:String? = null

    constructor()
    constructor(
        idUser : String?,
        userName : String?,
        phone : String?,
        email : String?,
        passWord : String?
    ) {
        this.idUser = idUser
        this.userName = userName
        this.phone = phone
        this.email = email
        this.passWord = passWord
    }

}