package com.example.firstmacros.service.listener

import com.example.firstmacros.service.model.ItemModel

interface ItemListener {
    fun onClick(item: ItemModel)
    fun onLongClick(item: ItemModel)
}