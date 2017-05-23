package com.ryanpmartz.kupload.service

import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManager
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@Service
class UploadService @Autowired
constructor(private val transferManager: TransferManager, @param:Value("s3.bucket.name") private val s3Bucket: String) {

    fun uploadFile(multipartFile: MultipartFile) {
        val extension = determineExtension(multipartFile)
        val filename = UUID.randomUUID().toString() + extension

        try {
            multipartFile.inputStream.use { `is` ->

                val req = PutObjectRequest(s3Bucket, filename, `is`, ObjectMetadata())
                req.withCannedAcl(CannedAccessControlList.Private)

                transferManager.upload(req)

            }
        } catch (e: IOException) {
            log.error("IOException occurred uploading file", e)
            throw RuntimeException(e)
        }

    }

    private fun determineExtension(multipartFile: MultipartFile): String {
        val filename = multipartFile.originalFilename

        return filename.substring(filename.lastIndexOf(".") + 1)
    }

    companion object {

        private val log = Logger.getLogger(UploadService::class.java)
    }
}
