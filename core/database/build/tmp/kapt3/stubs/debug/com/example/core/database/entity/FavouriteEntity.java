package com.example.core.database.entity;

/**
 * Room entity representing a favourite game association.
 * Maps to the 'favourites' table with foreign key to users and games.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\'\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\u0006\u0010\u0019\u001a\u00020\u001aJ\t\u0010\u001b\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u00a8\u0006\u001d"}, d2 = {"Lcom/example/core/database/entity/FavouriteEntity;", "", "id", "", "userId", "gameId", "addedAt", "", "(JJJLjava/lang/String;)V", "getAddedAt", "()Ljava/lang/String;", "getGameId", "()J", "getId", "getUserId", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toDomain", "Lcom/example/core/model/Favourite;", "toString", "Companion", "database_debug"})
@androidx.room.Entity(tableName = "favourites", foreignKeys = {@androidx.room.ForeignKey(entity = com.example.core.database.entity.UserEntity.class, parentColumns = {"id"}, childColumns = {"userId"}, onDelete = 5), @androidx.room.ForeignKey(entity = com.example.core.database.entity.GameEntity.class, parentColumns = {"id"}, childColumns = {"gameId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"userId"}), @androidx.room.Index(value = {"gameId"}), @androidx.room.Index(value = {"userId", "gameId"}, unique = true)})
public final class FavouriteEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long userId = 0L;
    private final long gameId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String addedAt = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.core.database.entity.FavouriteEntity.Companion Companion = null;
    
    public FavouriteEntity(long id, long userId, long gameId, @org.jetbrains.annotations.NotNull()
    java.lang.String addedAt) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getUserId() {
        return 0L;
    }
    
    public final long getGameId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAddedAt() {
        return null;
    }
    
    /**
     * Converts entity to domain model
     */
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.model.Favourite toDomain() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.entity.FavouriteEntity copy(long id, long userId, long gameId, @org.jetbrains.annotations.NotNull()
    java.lang.String addedAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/core/database/entity/FavouriteEntity$Companion;", "", "()V", "fromDomain", "Lcom/example/core/database/entity/FavouriteEntity;", "favourite", "Lcom/example/core/model/Favourite;", "database_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Creates entity from domain model
         */
        @org.jetbrains.annotations.NotNull()
        public final com.example.core.database.entity.FavouriteEntity fromDomain(@org.jetbrains.annotations.NotNull()
        com.example.core.model.Favourite favourite) {
            return null;
        }
    }
}