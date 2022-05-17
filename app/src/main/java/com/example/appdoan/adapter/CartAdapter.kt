package com.example.appdoan.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoan.R
import com.example.appdoan.databinding.ListCartItemBinding
import com.example.appdoan.model.CartData
import com.example.appdoan.ui.cart.detailCartActivity


class CartAdapter (var c: Context, var CartList:ArrayList<CartData>):
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CartViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v =  DataBindingUtil.inflate<ListCartItemBinding>(
            inflter, R.layout.list_cart_item,parent,false
        )
        return CartViewHolder(v)
    }

    override fun onBindViewHolder(holder : CartViewHolder, position : Int) {
        val detailCart = CartList[position]
        holder.v.isCart =  CartList[position]
        holder.v.root.setOnClickListener {
            val idCart = detailCart.idCart
            val name = detailCart.nameRes
            val img = detailCart.imgRes
            val adress = detailCart.adress
            val day = detailCart.day
            val hour = detailCart.hour
            val children = detailCart.children
            val adult = detailCart.adult
            val note = detailCart.note

            val intent = Intent(c, detailCartActivity::class.java)
            intent.putExtra("idCart",idCart)
            intent.putExtra("name",name)
            intent.putExtra("img",img)
            intent.putExtra("adress",adress)
            intent.putExtra("day",day)
            intent.putExtra("hour",hour)
            intent.putExtra("children",children)
            intent.putExtra("adult",adult)
            intent.putExtra("note",note)
            c.startActivity(intent)
        }

    }

    override fun getItemCount() : Int {
        return CartList.size
    }

    class CartViewHolder (var v: ListCartItemBinding): RecyclerView.ViewHolder(v.root){

    }

}