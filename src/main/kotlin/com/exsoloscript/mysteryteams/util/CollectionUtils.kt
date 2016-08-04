package com.exsoloscript.mysteryteams.util

import java.util.ArrayList

object CollectionUtils {

    fun <T> split(toSplit: List<T>, howOften: Int): List<List<T>> {
        val list = ArrayList<MutableList<T>>(howOften)
        for (i in 0..howOften - 1) {
            list.add(ArrayList<T>())
        }

        var i = 0

        for (t in toSplit) {
            list[i].add(t)
            i = (i + 1) % howOften
        }

        return list
    }

}
