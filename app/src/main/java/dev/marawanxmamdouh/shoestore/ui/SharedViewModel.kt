package dev.marawanxmamdouh.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.marawanxmamdouh.shoestore.models.Shoe

class SharedViewModel : ViewModel() {

    var shoe = Shoe("", 0.0, "", "", 0)
    private var _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    init {
        _shoeList.value = ArrayList()
    }

    fun addShoe() {
        _shoeList.value?.add(shoe)
        _shoeList.value = _shoeList.value
    }

    fun createEmptyShoe(): Shoe {
        shoe = Shoe("", 0.0, "", "", 0)
        return shoe
    }

    fun checkIfNotEmptyShoe(): Boolean {
        return shoe.name.isNotEmpty() && shoe.size != 0.0 && shoe.company.isNotEmpty() && shoe.description.isNotEmpty()
    }
}