package com.ryanpmartz.kupload.web

import com.ryanpmartz.kupload.auth.UserBeanUtil
import com.ryanpmartz.kupload.domain.Document
import com.ryanpmartz.kupload.service.DocumentService
import com.ryanpmartz.kupload.service.UploadService
import com.ryanpmartz.kupload.web.form.UploadForm
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.transaction.Transactional

@Controller
class UploadController @Autowired
constructor(private val uploadService: UploadService, private val documentService: DocumentService) {

    @GetMapping("/uploads/new")
    fun uploadForm(@ModelAttribute uploadForm: UploadForm): String {
        return "upload-form"
    }

    @Transactional
    @PostMapping("/upload")
    fun uploadFile(@ModelAttribute uploadForm: UploadForm, result: BindingResult): String {
        val file = uploadForm.file
        if (file == null) {
            result.rejectValue("file", null, "Must include file to upload")
            return uploadForm(uploadForm)
        }

        val filename = uploadService.uploadFile(file)

        val document = Document()
        document.originalFilename = file.originalFilename
        document.url = filename
        document.user = UserBeanUtil.userFromSecurityContext().get()

        documentService.save(document)

        return "redirect:uploads/new"

    }

    companion object {

        private val log = Logger.getLogger(UploadController::class.java)

    }


}

