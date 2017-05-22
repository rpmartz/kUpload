package com.ryanpmartz.kupload.web

import com.ryanpmartz.kupload.web.form.UploadForm
import org.apache.log4j.Logger
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class UploadController {


    @GetMapping("/uploads/new")
    fun uploadForm(@ModelAttribute uploadForm: UploadForm): String {
        return "upload-form"
    }

    @PostMapping("/upload")
    fun uploadFile(@ModelAttribute uploadForm: UploadForm, result: BindingResult): String {
        val file = uploadForm.file
        if (file == null) {
            result.rejectValue("file", null, "Must include file to upload")
            return uploadForm(uploadForm)
        }

        log.info("Uploading file ${file.originalFilename}")

        return "redirect:uploads/new"

    }

    companion object {

        private val log = Logger.getLogger(UploadController::class.java)

    }


}

