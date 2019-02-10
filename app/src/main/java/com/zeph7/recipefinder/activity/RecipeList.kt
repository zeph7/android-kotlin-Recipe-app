package com.zeph7.recipefinder.activity

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.zeph7.recipefinder.R
import com.zeph7.recipefinder.model.RecipeListAdapter
import com.zeph7.recipefinder.data.LEFT_LINK
import com.zeph7.recipefinder.data.QUERY
import com.zeph7.recipefinder.data.Recipe
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var recipeList: ArrayList<Recipe>? = null
    var adapter: RecipeListAdapter? = null
    var listView: ListView? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Slow Network Loading...")
        progressDialog!!.show()

        var urlString = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"

        var url: String?

        var extras = intent.extras
        var ingredients = extras.get("ingredients")
        var searchTerm = extras.get("search")

        if (extras != null && !ingredients.equals("")
            && !searchTerm.equals("")) {
            //construct our url
            var tempUrl = LEFT_LINK +ingredients + QUERY +searchTerm
            url = tempUrl

        }else {
            url = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"
        }

        recipeList = ArrayList<Recipe>()

        volleyRequest = Volley.newRequestQueue(this)

        getRecipe(url)

    }

    fun getRecipe(url: String) {
        val recipeRequest = JsonObjectRequest(Request.Method.GET,
            url, Response.Listener {
                    response: JSONObject ->
                try {

                    val resultArray = response.getJSONArray("results")

                    for (i in 0..resultArray.length() - 1) {
                        var recipeObj = resultArray.getJSONObject(i)

                        var title = recipeObj.getString("title")
                        var link = recipeObj.getString("href")
                        var thumbnail = recipeObj.getString("thumbnail")
                        var ingredients = recipeObj.getString("ingredients")

                        var recipe = Recipe()
                        recipe.title = title
                        recipe.link = link
                        recipe.thumbnail = thumbnail
                        recipe.ingredients = "List of all the Ingredients needed :\n\n ${ingredients}"

                        recipeList!!.add(recipe)

                        adapter = RecipeListAdapter(this, R.layout.list_row, recipeList!!)
                        listView = findViewById(R.id.listViewId)
                        listView!!.adapter = adapter

                    }
                    adapter!!.notifyDataSetChanged()
                    progressDialog!!.cancel()

                }catch (e: JSONException) { e.printStackTrace() }

            },
            Response.ErrorListener {
                    error: VolleyError? ->
                try {
                    Log.d("Error:", error.toString())

                } catch (e: JSONException) { e.printStackTrace() }
            })

        volleyRequest!!.add(recipeRequest)
    }
}
