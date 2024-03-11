package com.avi.transaction.aspect;

import com.avi.transaction.dto.TransactionRequest;
import com.avi.transaction.exception.TransactionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Aspect
@Component
public class ValidationAspect {

    @Pointcut("execution(* com.avi.transaction.controller.*.*(..))")
    public void controllerPointcut() {
    }


    @Before("controllerPointcut()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof TransactionRequest) {
                validateComplaintRequest((TransactionRequest) arg);
            }
        }
    }

    private void validateComplaintRequest(TransactionRequest transactionRequest) {
        if (Objects.isNull(transactionRequest.getAmount())) {
            throw new TransactionException("Invalid amount", HttpStatus.BAD_REQUEST);
        }
        else if (Objects.isNull(transactionRequest.getTransactionDate())
                || transactionRequest.getTransactionDate().isAfter(LocalDate.now())) {
            throw new TransactionException("Invalid date", HttpStatus.BAD_REQUEST);
        }
        else if (Objects.isNull(transactionRequest.getDescription())
                || transactionRequest.getDescription().isEmpty()) {
            throw new TransactionException("Must have description", HttpStatus.BAD_REQUEST);
        }
        else if (Objects.isNull(transactionRequest.getType())
                || (!transactionRequest.getType().equalsIgnoreCase("debit"))
                && !transactionRequest.getType().equalsIgnoreCase("credit")) {
            throw new TransactionException("Must have a valid type(credit or debit)", HttpStatus.BAD_REQUEST);
        }
    }
}
