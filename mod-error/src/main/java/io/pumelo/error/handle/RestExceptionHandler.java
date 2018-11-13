package io.pumelo.error.handle;


import io.pumelo.common.errorcode.Syscode;
import io.pumelo.common.web.ApiErrorResponse;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.error.exception.LogicException;
import io.pumelo.utils.LOG;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 　　　　　　　　 NoSuchMethodException,IOException,IndexOutOfBoundsException
 　　　　　　　　 以及springmvc自定义异常等，如下：
 SpringMVC自定义异常对应的status code
 Exception                               HTTP Status Code
 ConversionNotSupportedException         500 (Internal Server Error)
 HttpMessageNotWritableException         500 (Internal Server Error)
 HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 NoSuchRequestHandlingMethodException    404 (Not Found)
 TypeMismatchException                   400 (Bad Request)
 HttpMessageNotReadableException         400 (Bad Request)
 MissingServletRequestParameterException 400 (Bad Request)
 *java 的异常 分为两种
 *1、继承 RuntimeException 的异常 即 交由jvm处理的异常 如常见的 NullPointerException，ClassCastException，IndexOutOfBoundsException等
 *2、检查式异常  如IO异常 与 SQL异常  必须 try  catch。
 *
 */
@ControllerAdvice
public class RestExceptionHandler{

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ApiResponse runtimeExceptionHandler(RuntimeException ex) {
        LOG.error(this,"运行时异常:",ex);
        return ApiResponse.prompt(Syscode.SERVER_ERROR);
    }


    //IO异常
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ApiResponse IOExceptionHandle(IOException ex) {
        LOG.error(this,"IO异常:",ex);
        return ApiResponse.prompt(Syscode.SERVER_ERROR);
    }

    //SQL异常
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ApiResponse SQLExceptionHandle(IOException ex) {
        LOG.error(this,"SQL异常:",ex);
        return ApiResponse.prompt(Syscode.SERVER_ERROR);
    }


    //乐观锁错误
    @ExceptionHandler({ObjectOptimisticLockingFailureException.class})
    @ResponseBody
    public ApiResponse OptimisticLockingFailureExceptionHandle(ObjectOptimisticLockingFailureException ex) {
        LOG.error(this,"乐观锁数据锁定失败:",ex);
        return ApiResponse.prompt(Syscode.SERVER_OPTIMISTIC_LOCKING_FAILURE);
    }

    //数据状态不一致
    @ExceptionHandler({StaleObjectStateException.class})
    @ResponseBody
    public ApiResponse OptimisticLockingFailureExceptionHandle(StaleObjectStateException ex) {
        LOG.error(this,"数据版本不一致:",ex);
        return ApiResponse.prompt(Syscode.SERVER_DATA_CHANGE);
    }


    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ApiResponse requestNotReadable(HttpMessageNotReadableException ex) {
        LOG.error(this,"400错误:",ex);
        return ApiResponse.prompt(Syscode.CLIENT_BAD_REQUEST);
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ApiResponse requestTypeMismatch(TypeMismatchException ex) {
        LOG.error(this,"400错误:",ex);
        return ApiResponse.prompt(Syscode.CLIENT_BAD_REQUEST);
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ApiResponse requestMissingServletRequest(MissingServletRequestParameterException ex) {
        LOG.error(this,"400错误:",ex);
        return ApiResponse.prompt(Syscode.CLIENT_BAD_REQUEST);
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ApiResponse request405() {
        LOG.error(this,"405错误");
        return ApiResponse.prompt(Syscode.CLIENT_METHOD_NOT_ALLOWED);
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ApiResponse request406() {
        LOG.error(this,"406错误");
        return ApiResponse.prompt(Syscode.CLIENT_NO_ACCEPTABLE);
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public ApiResponse server500(RuntimeException ex) {
        LOG.error(this,"500错误",ex);
        return ApiResponse.prompt(Syscode.SERVER_ERROR);
    }

    //Valid 校验统一处理
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ApiErrorResponse bindExceptionHandler(BindException  ex) {
        LOG.error(this,"参数校验错误",ex);
        List<ValidateErrorResult> validateErrorResults = new ArrayList<>();
        for(ObjectError objectError : ex.getAllErrors() ) {
            validateErrorResults.add(new ValidateErrorResult(objectError.getCodes()[1], objectError.getDefaultMessage()));
        }
        return ApiErrorResponse.prompt(Syscode.VALIDATE_EXCEPTION, validateErrorResults);
    }

    // Validated 校验统一处理
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ApiErrorResponse bindExceptionHandler(ConstraintViolationException  ex) {
        LOG.error(this,"参数校验错误",ex);
        List<ValidateErrorResult> validateErrorResults = new ArrayList<>();
        for(ConstraintViolation constraintViolation : ex.getConstraintViolations()) {
            validateErrorResults.add(new ValidateErrorResult(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
        }
        return ApiErrorResponse.prompt(Syscode.VALIDATE_EXCEPTION, validateErrorResults);
    }

    // valid requestBody 参数校验错误处理
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiErrorResponse handleException(MethodArgumentNotValidException ex) {
        LOG.error(this,"校验错误",ex);
        BindingResult bindingResult = ex.getBindingResult();
        List<ValidateErrorResult> validateErrorResults = new ArrayList<>();
        for(ObjectError objectError : bindingResult.getAllErrors() ) {
            validateErrorResults.add(new ValidateErrorResult(objectError.getCodes()[1], objectError.getDefaultMessage()));
        }
        return ApiErrorResponse.prompt(Syscode.VALIDATE_EXCEPTION, validateErrorResults);
    }

    //逻辑异常处理
    @ExceptionHandler({LogicException.class})
    @ResponseBody
    public ApiResponse LogicExceptionHandle(LogicException ex) {
        LOG.error(this,"逻辑错误:",ex);
        return ex.getApiResponse();
    }
}

