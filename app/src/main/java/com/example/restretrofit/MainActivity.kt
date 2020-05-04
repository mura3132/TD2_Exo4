package com.example.restretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var service: TacheService
    var listTaches = arrayListOf<Tache>()
    lateinit var adapter: TacheAdapter
    lateinit var layoutManager : LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = TacheAdapter(this)
        recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<TacheService>(TacheService::class.java)


        initData()
    }

    fun initData(){

        service.getAllTeches().enqueue(object: Callback<List<Tache>> {
            override fun onResponse(call: Call<List<Tache>>, response: retrofit2.Response<List<Tache>>?) {
                if ((response != null) && (response.code() == 200)) {
                    Log.i("hehehhe", response.body()!![0].title)
                    listTaches.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Tache>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Echec", Toast.LENGTH_LONG).show()
            }
        })
    }



    class TacheAdapter(val activity : MainActivity) : RecyclerView.Adapter<TacheAdapter.TacheViewHolder>(){
        class TacheViewHolder(v : View) : RecyclerView.ViewHolder(v){
            val titleTache = v.findViewById<TextView>(R.id.TacheTitle)
            val dateTache = v.findViewById<TextView>(R.id.TacheId)
            val layoutTache = v.findViewById<RelativeLayout>(R.id.itemLayout)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TacheViewHolder {
            return TacheViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_layout, parent, false))
        }

        override fun getItemCount(): Int {
            return activity.listTaches.size
        }

        override fun onBindViewHolder(holder: TacheViewHolder, position: Int) {
            val title = activity.listTaches[position].title
            val tacheId = activity.listTaches[position].id

            holder.titleTache.text = title
            holder.dateTache.text = tacheId.toString()

            holder.layoutTache.setOnClickListener {
                val intent = Intent(activity, TacheActivity::class.java)
                intent.putExtra("tache", activity.listTaches[position])
                activity.startActivity(intent)
            }
        }
    }
}
