package com.example.core.database.exception;

/**
 * Exception thrown when there's a concurrency conflict
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/core/database/exception/ConcurrencyException;", "Lcom/example/core/database/exception/DatabaseException;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "database_debug"})
public final class ConcurrencyException extends com.example.core.database.exception.DatabaseException {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String message = null;
    
    public ConcurrencyException(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getMessage() {
        return null;
    }
    
    public ConcurrencyException() {
        super(null, null);
    }
}