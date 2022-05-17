package com.example.appdoan.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoan.R
import com.example.appdoan.databinding.ListItemCategoryBinding
import com.example.appdoan.model.CategoryData
import com.example.appdoan.ui.detail.detailCateActivity


class CateAdapter(var c:Context,var cateList:ArrayList<CategoryData>):
    RecyclerView.Adapter<CateAdapter.cateViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : cateViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v =  DataBindingUtil.inflate<ListItemCategoryBinding>(
            inflter, R.layout.list_item_category,parent,false
        )
        return cateViewHolder(v)
    }

    override fun onBindViewHolder(holder : cateViewHolder, position : Int) {
        val deTailCateList = cateList[position]
        holder.v.isCategory =  cateList[position]
        holder.v.root.setOnClickListener {
            val cateID = deTailCateList.cid
            val name = deTailCateList.cname
            val intent = Intent(c, detailCateActivity::class.java)
            intent.putExtra("cid",cateID)
            intent.putExtra("cname",name)
            c.startActivity(intent)
        }

    }

    override fun getItemCount() : Int {
        return cateList.size
    }
    class cateViewHolder(var v: ListItemCategoryBinding): RecyclerView.ViewHolder(v.root) {

    }

}