package com.capstone1.tutoryapi.controller.message

import com.capstone1.tutoryapi.entities.messager.ThreadMessage

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
data class ThreadMessageResponse(val threadMessages: List<ThreadMessage>? = null)
