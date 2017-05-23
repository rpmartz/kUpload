package com.ryanpmartz.kupload.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class UploadService @Autowired
constructor(private val s3Client: AmazonS3Client, @Value("\${aws.s3.bucket}") private val s3Bucket: String) {

    fun uploadFile(multipartFile: MultipartFile): String {
        val extension = determineExtension(multipartFile)
        val filename = UUID.randomUUID().toString() + "." + extension

        multipartFile.inputStream.use { mis ->
            val req = PutObjectRequest(s3Bucket, filename, mis, ObjectMetadata())
            req.withCannedAcl(CannedAccessControlList.Private)

            s3Client.putObject(req)
        }

        return filename

    }

    private fun determineExtension(multipartFile: MultipartFile): String {
        val filename = multipartFile.originalFilename

        return filename.substring(filename.lastIndexOf(".") + 1)
    }

    companion object {

        private val log = Logger.getLogger(UploadService::class.java)
    }
}
