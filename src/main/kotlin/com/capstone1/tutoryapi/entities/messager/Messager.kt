package com.capstone1.tutoryapi.entities.messager

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
data class Messager(val idMessage: Int? = 0,
                    val idThread: Int? = 0,
                    val isSender: String? = "0",
                    val message: String? = "",
                    val reader: String? = "0",
                    val timeRead: String? = "0")
