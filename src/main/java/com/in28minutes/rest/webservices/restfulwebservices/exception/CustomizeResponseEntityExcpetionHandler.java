package com.in28minutes.rest.webservices.restfulwebservices.exception;

import com.in28minutes.rest.webservices.restfulwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice //모든 컨트롤러에 아래 코드 적용
public class CustomizeResponseEntityExcpetionHandler extends ResponseEntityExceptionHandler {

    //예외 처리 메서드를 상황에 맞게 바꾸기

    //@Exceptionhandler에는 어떤 예외를 아래 메서드에서 처리할 건지 정의한다.
    @ExceptionHandler(Exception.class) // 모든 예외를 아래 메서드로 처리한다.
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception{
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        //errorDetails + 응답상태(SERVER_ERROR)
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class) // 모든 예외를 아래 메서드로 처리한다.
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception{
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        //errorDetails + 응답상태(SERVER_ERROR)
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                "Total Errors: " + ex.getErrorCount()+ " First Error: "+  ex.getFieldError().getDefaultMessage(), request.getDescription(false));
        /*
        * ex.getMessage() 대신 필드 관련 첫번째 에러
        * -> ex.getFieldError()를 호출하고 .getDefaultMessage()를 사용하면
        * 에러 message가 필드 디폴트 오류 메시지만 출력된다.
        * */
        //errorDetails + 응답상태(SERVER_ERROR)
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
