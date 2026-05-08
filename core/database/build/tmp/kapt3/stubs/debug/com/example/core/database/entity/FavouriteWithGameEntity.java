package com.example.core.database.entity;

/**
 * Composite entity for favourite with game details.
 * Used for querying favourites with full game information.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b%\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bu\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0007\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0014J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0007H\u00c6\u0003J\t\u0010)\u001a\u00020\u000fH\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003J\t\u0010+\u001a\u00020\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\u0013H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0007H\u00c6\u0003J\t\u00100\u001a\u00020\u0007H\u00c6\u0003J\t\u00101\u001a\u00020\u0007H\u00c6\u0003J\t\u00102\u001a\u00020\u0007H\u00c6\u0003J\t\u00103\u001a\u00020\u0007H\u00c6\u0003J\t\u00104\u001a\u00020\u0007H\u00c6\u0003J\u0095\u0001\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u00c6\u0001J\u0013\u00106\u001a\u00020\u00132\b\u00107\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00108\u001a\u000209H\u00d6\u0001J\t\u0010:\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0010\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\f\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0011\u0010\u0011\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0016R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0016R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0018\u00a8\u0006;"}, d2 = {"Lcom/example/core/database/entity/FavouriteWithGameEntity;", "", "favouriteId", "", "userId", "gameId", "addedAt", "", "gameTitle", "gameDescription", "gameThumbnailUrl", "gameGenre", "gamePlatform", "gameReleaseDate", "gameRating", "", "gameDeveloper", "gamePublisher", "gameIsFeatured", "", "(JJJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;FLjava/lang/String;Ljava/lang/String;Z)V", "getAddedAt", "()Ljava/lang/String;", "getFavouriteId", "()J", "getGameDescription", "getGameDeveloper", "getGameGenre", "getGameId", "getGameIsFeatured", "()Z", "getGamePlatform", "getGamePublisher", "getGameRating", "()F", "getGameReleaseDate", "getGameThumbnailUrl", "getGameTitle", "getUserId", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "database_debug"})
public final class FavouriteWithGameEntity {
    private final long favouriteId = 0L;
    private final long userId = 0L;
    private final long gameId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String addedAt = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String gameTitle = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String gameDescription = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String gameThumbnailUrl = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String gameGenre = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String gamePlatform = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String gameReleaseDate = null;
    private final float gameRating = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String gameDeveloper = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String gamePublisher = null;
    private final boolean gameIsFeatured = false;
    
    public FavouriteWithGameEntity(long favouriteId, long userId, long gameId, @org.jetbrains.annotations.NotNull()
    java.lang.String addedAt, @org.jetbrains.annotations.NotNull()
    java.lang.String gameTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String gameDescription, @org.jetbrains.annotations.NotNull()
    java.lang.String gameThumbnailUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String gameGenre, @org.jetbrains.annotations.NotNull()
    java.lang.String gamePlatform, @org.jetbrains.annotations.NotNull()
    java.lang.String gameReleaseDate, float gameRating, @org.jetbrains.annotations.NotNull()
    java.lang.String gameDeveloper, @org.jetbrains.annotations.NotNull()
    java.lang.String gamePublisher, boolean gameIsFeatured) {
        super();
    }
    
    public final long getFavouriteId() {
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGameTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGameDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGameThumbnailUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGameGenre() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGamePlatform() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGameReleaseDate() {
        return null;
    }
    
    public final float getGameRating() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGameDeveloper() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGamePublisher() {
        return null;
    }
    
    public final boolean getGameIsFeatured() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    public final float component11() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
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
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.entity.FavouriteWithGameEntity copy(long favouriteId, long userId, long gameId, @org.jetbrains.annotations.NotNull()
    java.lang.String addedAt, @org.jetbrains.annotations.NotNull()
    java.lang.String gameTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String gameDescription, @org.jetbrains.annotations.NotNull()
    java.lang.String gameThumbnailUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String gameGenre, @org.jetbrains.annotations.NotNull()
    java.lang.String gamePlatform, @org.jetbrains.annotations.NotNull()
    java.lang.String gameReleaseDate, float gameRating, @org.jetbrains.annotations.NotNull()
    java.lang.String gameDeveloper, @org.jetbrains.annotations.NotNull()
    java.lang.String gamePublisher, boolean gameIsFeatured) {
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
}