package mos.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MosException extends RuntimeException {

    protected MosException(String message) {
        super(message);
    }
}
