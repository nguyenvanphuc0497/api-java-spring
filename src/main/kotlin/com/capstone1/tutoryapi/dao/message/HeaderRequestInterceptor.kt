package com.capstone1.tutoryapi.dao.message

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.support.HttpRequestWrapper

/**
 * Created by Nguyen Van Phuc on 11/26/18
 * Class is used Push Notification Service
 * CopyRight https://grokonez.com/spring-framework/spring-boot/firebase-cloud-messaging-server-spring-to-push-notification-example-spring-boot
 */
class HeaderRequestInterceptor(private val headerName: String?, private val headerValue: String?) : ClientHttpRequestInterceptor {

    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        val wrapper = HttpRequestWrapper(request)
        wrapper.headers.set(headerName.toString(), headerValue.toString())
        return execution.execute(wrapper, body)
    }
}
