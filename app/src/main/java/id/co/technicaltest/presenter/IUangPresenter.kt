package id.co.technicaltest.presenter

import id.co.technicaltest.model.Uang

interface IUangPresenter {
    fun insertData(uang: Uang)
    fun getAllData()
    fun getLastId(): String
}