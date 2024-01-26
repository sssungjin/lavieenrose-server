package org.dongguk.lavieenrosehotel.dto.response

import jakarta.servlet.http.HttpServletResponse
import net.minidev.json.JSONValue
import org.dongguk.lavieenrosehotel.dto.common.ExceptionDto
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import java.io.IOException
import kotlin.Any
import kotlin.String
import kotlin.Throws


open class JwtErrorDto {
    @Throws(IOException::class)
    protected fun setErrorResponse(response: HttpServletResponse, errorCode: ErrorCode?) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = if (errorCode == ErrorCode.ACCESS_DENIED) 403 else 401
        val map: MutableMap<String, Any?> = HashMap()
        map["success"] = false
        map["data"] = null
        map["error"] = ExceptionDto(errorCode!!)
        response.writer.print(JSONValue.toJSONString(map))
    }
}