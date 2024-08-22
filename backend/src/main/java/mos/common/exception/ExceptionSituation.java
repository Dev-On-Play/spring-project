package mos.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionSituation {

    private final String message;
    private final HttpStatus statusCode;

    public static ExceptionSituation of(String message, HttpStatus statusCode) {
        return new ExceptionSituation(message, statusCode);
    }
}
