package com.insiderser.mars.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.insiderser.mars.model.MarsImage

class HomeViewModel @ViewModelInject constructor(

) : ViewModel() {

    val images: LiveData<PagedList<MarsImage>> = TODO()
}
