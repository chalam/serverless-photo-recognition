package com.budilov.s3

import com.amazonaws.HttpMethod
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.budilov.Properties
import java.net.URL
import java.time.Duration
import java.time.Instant
import java.util.*

/**
 * Created by Vladimir Budilov
 */
object S3Service {

    private val s3Client = AmazonS3Client(EnvironmentVariableCredentialsProvider())

    /**
     * Returns the signed url of an object
     */
    fun getSignedUrl(bucketName: String, objectKey: String): URL {

        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(bucketName, objectKey)
        generatePresignedUrlRequest.method = HttpMethod.GET
        generatePresignedUrlRequest.expiration = getExpiration()

        return S3Service.s3Client.generatePresignedUrl(generatePresignedUrlRequest)
    }

    /**
     * Returns the default expiration
     *
     */
    private fun getExpiration(): Date {
        val expiration = Date()
        var msec = expiration.time
        msec += (1000 * 60 * 60).toLong() // 1 hour.
        expiration.time = msec

        return expiration
    }
}
