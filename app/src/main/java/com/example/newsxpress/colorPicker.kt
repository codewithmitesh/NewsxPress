package com.example.newsxpress

object colorPicker {
    val colors =
        arrayOf("#B070FB","#FBF684","#E1AC59","#11F695","#204EB9","#B3097F","#8A1F93","#69A42A")
    var ColorIndex = 1;
    fun getColor():String{
        return colors[ColorIndex++ % colors.size]       // Clanging the colors Without Using Loop
    }

}