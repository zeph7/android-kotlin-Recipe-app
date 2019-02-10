package com.zeph7.recipefinder.model

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.zeph7.recipefinder.R
import com.zeph7.recipefinder.activity.ShowLink
import com.zeph7.recipefinder.data.Recipe

class RecipeListAdapter (var mCtx: Context, var resource: Int, var items: List<Recipe>):
    ArrayAdapter<Recipe>(mCtx, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)

        // from this view we will get views like ImageView, TextView, etc
        val view: View = layoutInflater.inflate(resource, null)

        var title: TextView = view.findViewById(R.id.recipeTitle)
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        var ingredients: TextView = view.findViewById(R.id.recipeIngredients)
        var linkButton: Button = view.findViewById(R.id.linkButton)

        val recipe: Recipe = items[position]
        title.text = recipe.title
        ingredients.text = recipe.ingredients

        // to import images to ImageView from string url
        if (!TextUtils.isEmpty(recipe.thumbnail)) {
            Picasso.with(mCtx)
                .load(recipe.thumbnail)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(thumbnail)
        } else {
            Picasso.with(mCtx).load(R.drawable.img_food).into(thumbnail)
        }

        // link to website for the full recipe
        linkButton.setOnClickListener {

            var intent = Intent(mCtx, ShowLink::class.java)
            intent.putExtra("link", recipe.link.toString())
            mCtx.startActivity(intent)
        }
        return view
    }
}