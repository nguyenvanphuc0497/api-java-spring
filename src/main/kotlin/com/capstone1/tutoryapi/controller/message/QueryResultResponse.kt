package com.capstone1.tutoryapi.controller.message

/**
 * Create by Nguyen Van Phuc on 2018-12-05
 **/
data class QueryResultResponse(val queryText: String? = null,
                               val detectedIntent: String? = null,
                               val fulfillmentText: String? = null)
