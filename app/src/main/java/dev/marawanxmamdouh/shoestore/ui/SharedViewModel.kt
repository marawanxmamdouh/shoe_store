package dev.marawanxmamdouh.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.marawanxmamdouh.shoestore.models.Shoe

class SharedViewModel: ViewModel() {

    private var _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    init {
        _shoeList.value = ArrayList()
    }

    fun addShoe(shoe: Shoe){
        _shoeList.value?.add(shoe)
        _shoeList.value = _shoeList.value
    }

    fun createEmptyShoe(): Shoe {
        shoe = Shoe("", 0.0, "", "", 0)
        return shoe
    }
}