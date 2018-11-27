package com.capstone1.tutoryapi.dao.message

import org.springframework.http.HttpEntity
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import java.util.*
import java.util.concurrent.CompletableFuture


/**
 * Created by Nguyen Van Phuc on 11/26/18
 */
@Service
class AndroidPushNotificationService {

    companion object {
        private const val FIRE_BASE_SERVER_KEY = "AAAAeiTYvGk:APA91bHioxlA1a8uoWM50sUZJIFrc68VE3gumdpcY-EhVlFcVPZmvZQGttPTjEFNDOPgT5c7CouPhVcYD7YkjfyaPYi99OMK47QUkFscG0hQ8G9DUGNtmzfaZ9DJ20CjGdeW2I_OQ134"
        private const val FIRE_BASE_API_URL = "https://fcm.googleapis.com/fcm/send"
    }

    @Async
    internal fun send(entity: HttpEntity<String>): CompletableFuture<String> {
        val restTemplate = RestTemplate()
        /**
        https://fcm.googleapis.com/fcm/send
        Content-Type:application/json
        Authorization:key=FIRE_BASE_SERVER_KEY*/
        val interceptors = ArrayList<ClientHttpRequestInterceptor>()
        interceptors.add(HeaderRequestInterceptor("Authorization", "key=$FIRE_BASE_SERVER_KEY"))
        interceptors.add(HeaderRequestInterceptor("Content-Type", "application/json"))
        restTemplate.interceptors = interceptors

        val fireBaseResponse = restTemplate.postForObject(FIRE_BASE_API_URL, entity, String::class.java)

        return CompletableFuture.completedFuture(fireBaseResponse)
    }
}
