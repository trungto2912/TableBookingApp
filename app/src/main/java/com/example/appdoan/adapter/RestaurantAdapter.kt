package com.example.appdoan.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoan.R
import com.example.appdoan.databinding.ListItemRestaurantBinding
import com.example.appdoan.model.Restaurants
import com.example.appdoan.ui.detail.detailResActivity


class RestaurantAdapter (var c:Context,var RestaurantList:ArrayList<Restaurants>):
    RecyclerView.Adapter<RestaurantAdapter.ResViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ResViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v =  DataBindingUtil.inflate<ListItemRestaurantBinding>(
            inflter, R.layout.list_item_restaurant,parent,false
        )
        return ResViewHolder(v)
    }

    override fun onBindViewHolder(holder : ResViewHolder, position : Int) {
        val deTailList = RestaurantList[position]
        holder.v.isRestaurant =  RestaurantList[position]
        holder.v.root.setOnClickListener {
            val id = deTailList.id
            val name = deTailList.name
            val img = deTailList.img
            val adress = deTailList.adress
            val describe = deTailList.describe
            val price = deTailList.price
            val clientTime = deTailList.clientTime
            val discount = deTailList.discount
            val cateID = deTailList.cateID

            val intent = Intent(c, detailResActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("name",name)
            intent.putExtra("img",img)
            intent.putExtra("adress",adress)
            intent.putExtra("describe",describe)
            intent.putExtra("price",price)
            intent.putExtra("clientTime",clientTime)
            intent.putExtra("discount",discount)
            intent.putExtra("cateID",cateID)
            c.startActivity(intent)
        }

    }

    override fun getItemCount() : Int {
        return RestaurantList.size
    }

    class ResViewHolder(var v: ListItemRestaurantBinding): RecyclerView.ViewHolder(v.root) {

    }

}