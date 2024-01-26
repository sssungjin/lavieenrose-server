package org.dongguk.lavieenrosehotel.utility

import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.util.*


@Component
class ImageUtil {
    companion object {
        private const val IMAGE_CONTENT_PREFIX = "image"
    }

    @Value("\${spring.image.path}")
    private val resourcePath: String? = null

    fun uploadImageFile(file: MultipartFile): String {
        val contentType = file.contentType
        val type = "." + contentType!!.substring(contentType.indexOf("/") + 1)

        if (!contentType.startsWith(IMAGE_CONTENT_PREFIX)) {
            throw CommonException(ErrorCode.MISSING_REQUEST_PARAMETER)
        }
        val uuid: String = UUID.randomUUID().toString()
        try {
            file.transferTo(
                File(resourcePath + uuid + type)
            )
        } catch (e: IOException) {
            throw CommonException(ErrorCode.UPLOAD_FILE_ERROR)
        }

        return uuid + type
    }

    fun uploadImageFiles(files: List<MultipartFile>) : List<String> {
        val uuids = mutableListOf<String>()

        for (file in files) {
            println(file)
        }

        files.forEach { file ->
            val contentType = file.contentType
            val type = "." + contentType!!.substring(contentType.indexOf("/") + 1)

            if (!contentType.startsWith(IMAGE_CONTENT_PREFIX)) {
                throw CommonException(ErrorCode.MISSING_REQUEST_PARAMETER)
            }
            val uuid: String = UUID.randomUUID().toString()
            try {
                file.transferTo(
                    File(resourcePath + uuid + type)
                )
            } catch (e: IOException) {
                throw CommonException(ErrorCode.UPLOAD_FILE_ERROR)
            }
            uuids.add(uuid + type)
        }

        return uuids
    }
}