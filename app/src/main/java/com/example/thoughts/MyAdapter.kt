package com.example.thoughts

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar


class MyAdapter(private val thoughtList: ArrayList<Thought>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thoughtitem, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentThought = thoughtList[position]

        holder.thoughtTitleTV.text = currentThought.title
        if(currentThought.content!!.length > 45) {
            holder.thoughtContentTV.text = currentThought.content!!.substring(0, 45) + "..."
        } else {
            holder.thoughtContentTV.text = currentThought.content!!
        }
        holder.thoughtDateTV.text = currentThought.createdAt!!
        val color = currentThought.color;
        val redColor = color?.split(",")?.toTypedArray()?.get(0)?.toInt()
        val greenColor = color?.split(",")?.toTypedArray()?.get(1)?.toInt()
        val blueColor = color?.split(",")?.toTypedArray()?.get(2)?.toInt()
        Log.d("Date", Calendar.getInstance().get(Calendar.DATE).toString())
        holder.card.setBackgroundColor(Color.rgb(redColor!!, greenColor!!, blueColor!!))

    }

    override fun getItemCount(): Int {
        return thoughtList.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val thoughtTitleTV: TextView = itemView.findViewById(R.id.rvtitle)
        val thoughtContentTV: TextView = itemView.findViewById(R.id.rvcontent)
        val thoughtDateTV: TextView = itemView.findViewById(R.id.rvdate)
        val card: CardView = itemView.findViewById(R.id.thoughtcard)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}