package org.dongguk.lavieenrosehotel.exception

import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.MultipartException
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log by lazy { LoggerFactory.getLogger(GlobalExceptionHandler::class.java) }

    // 지원되지 않는 미디어 타입을 요청했을 때 발생하는 예외
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class, MultipartException::class)
    fun handleHttpMediaTypeNotSupportedException(e: HttpMediaTypeNotSupportedException): ResponseDto<*> {
        log.error("Handler in HttpMediaTypeNotSupportedException Error Message = ${e.message}")
        return ResponseDto.fail(CommonException(ErrorCode.UNSUPPORTED_MEDIA_TYPE))
    }

    // NohandlerFoundException 예외
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException): ResponseDto<*> {
        log.error("Handler in NoHandlerFoundException Error Message = ${e.message}")
        return ResponseDto.fail(CommonException(ErrorCode.NOT_END_POINT))
    }


    // HttpMessageConverter가 요청 본문을 읽을 수 없을 때 발생하는 예외
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseDto<*> {
        log.error("Handler in HttpMessageNotReadableException Error Message = ${e.message}")
        return ResponseDto.fail(CommonException(ErrorCode.INVALID_ARGUMENT))
    }

    // @Validated 어노테이션을 사용하여 검증을 수행할 때 발생하는 예외
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseDto<*> {
        log.error("Handler in MethodArgumentNotValidException Error Message = ${e.message}")
        return ResponseDto.fail(e)
    }

    // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseDto<*> {
        log.error("Handler in HttpRequestMethodNotSupportedException Error Message = ${e.message}")
        return ResponseDto.fail(CommonException(ErrorCode.METHOD_NOT_ALLOWED))
    }

    // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseDto<*> {
        log.error("Handler in MethodArgumentTypeMismatchException Error Message = ${e.message}")
        return ResponseDto.fail(CommonException(ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH))
    }

    // 필수 파라미터가 누락되었을 때 발생하는 예외
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseDto<*> {
        log.error("Handler in MissingServletRequestParameterException Error Message = ${e.message}")
        return ResponseDto.fail(CommonException(ErrorCode.MISSING_REQUEST_PARAMETER))
    }

    // 개발자가 직접 정의한 예외
    @ExceptionHandler(CommonException::class)
    fun handleApiException(e: CommonException): ResponseDto<*> {
        log.error("Handler in CommonException Error Message = ${e.message}")
        return ResponseDto.fail(e)
    }

    // 서버, DB 예외
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseDto<*> {
        log.error("Handler in Exception Error Message = ${e.message}")
        e.printStackTrace()
        return ResponseDto.fail(CommonException(ErrorCode.INTERNAL_SERVER_ERROR))
    }
}