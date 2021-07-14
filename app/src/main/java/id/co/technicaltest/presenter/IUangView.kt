package id.co.technicaltest.presenter

import id.co.technicaltest.model.Uang

interface IUangView {
    fun setData(listUang: List<Uang>)
    fun setEmpty()
    fun setResult(message: String)
    fun onLoad(isLoad: Boolean)
}