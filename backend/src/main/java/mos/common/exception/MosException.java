package mos.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MosException extends Throwable {

    protected MosException(String message) {
        super(message);
    }
}
