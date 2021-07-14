package id.co.technicaltest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.technicaltest.R
import id.co.technicaltest.databinding.ItemUangBinding
import id.co.technicaltest.model.Uang

class UangAdapter: RecyclerView.Adapter<UangAdapter.ViewHolder>() {

    val listUang = ArrayList<Uang>()

    fun setListUang(listUang: List<Uang>){
        this.listUang.clear()
        this.listUang.addAll(listUang)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding: ItemUangBinding = DataBindingUtil.inflate(inflate, R.layout.item_uang, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uang = listUang[position]
        holder.bind(uang)
    }

    override fun getItemCount(): Int = listUang.size

    inner class ViewHolder(val binding: ItemUangBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(uang: Uang){
            with(binding){
                tvId.text = uang.uangMasukId
                tvUang.text = uang.jumlah
                tvTerima.text = uang.terimaDari
            }
        }
    }

}