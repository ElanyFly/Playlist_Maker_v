package com.example.playlistmaker.utils

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

@SuppressLint("RestrictedApi")
fun Fragment.navigateToDestination(@IdRes destinationID: Int){
    val controller = findNavController()
    val destination = controller.findDestination(destinationID)
    if (destination == null) {
        parentFragment?.navigateToDestination(destinationID)
        return
    }
    controller.navigate(destination.id)
}