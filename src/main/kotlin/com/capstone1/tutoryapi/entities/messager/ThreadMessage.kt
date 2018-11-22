package com.capstone1.tutoryapi.entities.messager

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
data class ThreadMessage(val idThread: Int? = 0,
                         val createTime: String? = null,
                         val receiverIdProfile: Int? = 0,
                         val senderIdProfile: Int? = 0,
                         val status: String? = "0")
