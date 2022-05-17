package com.example.appdoan.uitel

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appdoan.R


fun getProgressDrawable(c: Context): CircularProgressDrawable {
    return CircularProgressDrawable(c).apply {
        strokeWidth = 5f
        centerRadius = 40f
        start()
    }
}
/* set image */
fun ImageView.loadImage(url:String?, progressDrawable: CircularProgressDrawable){
    val option = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)
    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)


}
@BindingAdapter("android:imageUrl")
fun loadImage(view : ImageView,url:String){
    view.loadImage(url, getProgressDrawable(view.context))
}