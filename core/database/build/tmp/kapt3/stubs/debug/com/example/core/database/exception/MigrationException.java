package com.example.core.database.exception;

/**
 * Exception thrown when migration fails
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\r"}, d2 = {"Lcom/example/core/database/exception/MigrationException;", "Lcom/example/core/database/exception/DatabaseException;", "message", "", "oldVersion", "", "newVersion", "(Ljava/lang/String;II)V", "getMessage", "()Ljava/lang/String;", "getNewVersion", "()I", "getOldVersion", "database_debug"})
public final class MigrationException extends com.example.core.database.exception.DatabaseException {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String message = null;
    private final int oldVersion = 0;
    private final int newVersion = 0;
    
    public MigrationException(@org.jetbrains.annotations.NotNull()
    java.lang.String message, int oldVersion, int newVersion) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getMessage() {
        return null;
    }
    
    public final int getOldVersion() {
        return 0;
    }
    
    public final int getNewVersion() {
        return 0;
    }
}