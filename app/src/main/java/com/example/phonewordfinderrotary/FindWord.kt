package com.example.phonewordfinderrotary

import java.util.*

class FindWord {


    companion object{
        private val wordMap = mapOf(
            0 to "0",
            1 to "1",
            2 to "ABC",
            3 to "DEF",
            4 to "GHI",
            5 to "JKL",
            6 to "MNO",
            7 to "PRS",
            8 to "TUV",
            9 to "WXY"
        )

        fun findWords(input: CharArray): List<String>{

            val listOfWords = mutableListOf<String>()
            val queue = LinkedList<String>()
            queue.add("")

            while (queue.isNotEmpty()){
                val s = queue.remove()
                if(s.length == input.size) {
                    listOfWords.add(s)
                } else {
                    val word = wordMap[((input[s.length]) - '0')]
                    word?.let {
                        it.forEach { c ->
                            queue.add("$s$c")
                        }
                    }
                }

            }
            return listOfWords.toList()
        }
    }

}