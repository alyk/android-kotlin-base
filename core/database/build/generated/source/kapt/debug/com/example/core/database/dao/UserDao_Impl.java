package com.example.core.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.core.database.entity.FavouriteEntity;
import com.example.core.database.entity.FavouriteWithGameEntity;
import com.example.core.database.entity.UserEntity;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserEntity> __insertionAdapterOfUserEntity;

  private final EntityInsertionAdapter<FavouriteEntity> __insertionAdapterOfFavouriteEntity;

  private final EntityDeletionOrUpdateAdapter<UserEntity> __deletionAdapterOfUserEntity;

  private final EntityDeletionOrUpdateAdapter<FavouriteEntity> __deletionAdapterOfFavouriteEntity;

  private final EntityDeletionOrUpdateAdapter<UserEntity> __updateAdapterOfUserEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllUsers;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFavouriteByIds;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllFavouritesForUser;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllFavourites;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserEntity = new EntityInsertionAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`username`,`email`,`avatarUrl`,`createdAt`,`favouriteGenres`,`favouritePlatforms`,`notificationsEnabled`,`darkModeEnabled`,`cachedAt`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUsername() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUsername());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmail());
        }
        if (entity.getAvatarUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAvatarUrl());
        }
        if (entity.getCreatedAt() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCreatedAt());
        }
        if (entity.getFavouriteGenres() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFavouriteGenres());
        }
        if (entity.getFavouritePlatforms() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFavouritePlatforms());
        }
        final int _tmp = entity.getNotificationsEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final int _tmp_1 = entity.getDarkModeEnabled() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getCachedAt());
      }
    };
    this.__insertionAdapterOfFavouriteEntity = new EntityInsertionAdapter<FavouriteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favourites` (`id`,`userId`,`gameId`,`addedAt`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavouriteEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getGameId());
        if (entity.getAddedAt() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAddedAt());
        }
      }
    };
    this.__deletionAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `users` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfFavouriteEntity = new EntityDeletionOrUpdateAdapter<FavouriteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `favourites` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavouriteEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`username` = ?,`email` = ?,`avatarUrl` = ?,`createdAt` = ?,`favouriteGenres` = ?,`favouritePlatforms` = ?,`notificationsEnabled` = ?,`darkModeEnabled` = ?,`cachedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUsername() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUsername());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmail());
        }
        if (entity.getAvatarUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAvatarUrl());
        }
        if (entity.getCreatedAt() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCreatedAt());
        }
        if (entity.getFavouriteGenres() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFavouriteGenres());
        }
        if (entity.getFavouritePlatforms() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFavouritePlatforms());
        }
        final int _tmp = entity.getNotificationsEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final int _tmp_1 = entity.getDarkModeEnabled() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getCachedAt());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllUsers = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM users";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteFavouriteByIds = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favourites WHERE userId = ? AND gameId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllFavouritesForUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favourites WHERE userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllFavourites = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favourites";
        return _query;
      }
    };
  }

  @Override
  public Object insertUser(final UserEntity user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserEntity.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertFavourite(final FavouriteEntity favourite,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFavouriteEntity.insertAndReturnId(favourite);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteUser(final UserEntity user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUserEntity.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFavourite(final FavouriteEntity favourite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavouriteEntity.handle(favourite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUser(final UserEntity user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserEntity.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllUsers(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllUsers.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllUsers.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFavouriteByIds(final long userId, final long gameId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFavouriteByIds.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, gameId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteFavouriteByIds.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllFavouritesForUser(final long userId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllFavouritesForUser.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllFavouritesForUser.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllFavourites(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllFavourites.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllFavourites.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserById(final long userId, final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFavouriteGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "favouriteGenres");
          final int _cursorIndexOfFavouritePlatforms = CursorUtil.getColumnIndexOrThrow(_cursor, "favouritePlatforms");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfDarkModeEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "darkModeEnabled");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            final String _tmpCreatedAt;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmpCreatedAt = null;
            } else {
              _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final String _tmpFavouriteGenres;
            if (_cursor.isNull(_cursorIndexOfFavouriteGenres)) {
              _tmpFavouriteGenres = null;
            } else {
              _tmpFavouriteGenres = _cursor.getString(_cursorIndexOfFavouriteGenres);
            }
            final String _tmpFavouritePlatforms;
            if (_cursor.isNull(_cursorIndexOfFavouritePlatforms)) {
              _tmpFavouritePlatforms = null;
            } else {
              _tmpFavouritePlatforms = _cursor.getString(_cursorIndexOfFavouritePlatforms);
            }
            final boolean _tmpNotificationsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp != 0;
            final boolean _tmpDarkModeEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfDarkModeEnabled);
            _tmpDarkModeEnabled = _tmp_1 != 0;
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _result = new UserEntity(_tmpId,_tmpUsername,_tmpEmail,_tmpAvatarUrl,_tmpCreatedAt,_tmpFavouriteGenres,_tmpFavouritePlatforms,_tmpNotificationsEnabled,_tmpDarkModeEnabled,_tmpCachedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserEntity> observeUserById(final long userId) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFavouriteGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "favouriteGenres");
          final int _cursorIndexOfFavouritePlatforms = CursorUtil.getColumnIndexOrThrow(_cursor, "favouritePlatforms");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfDarkModeEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "darkModeEnabled");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            final String _tmpCreatedAt;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmpCreatedAt = null;
            } else {
              _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final String _tmpFavouriteGenres;
            if (_cursor.isNull(_cursorIndexOfFavouriteGenres)) {
              _tmpFavouriteGenres = null;
            } else {
              _tmpFavouriteGenres = _cursor.getString(_cursorIndexOfFavouriteGenres);
            }
            final String _tmpFavouritePlatforms;
            if (_cursor.isNull(_cursorIndexOfFavouritePlatforms)) {
              _tmpFavouritePlatforms = null;
            } else {
              _tmpFavouritePlatforms = _cursor.getString(_cursorIndexOfFavouritePlatforms);
            }
            final boolean _tmpNotificationsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp != 0;
            final boolean _tmpDarkModeEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfDarkModeEnabled);
            _tmpDarkModeEnabled = _tmp_1 != 0;
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _result = new UserEntity(_tmpId,_tmpUsername,_tmpEmail,_tmpAvatarUrl,_tmpCreatedAt,_tmpFavouriteGenres,_tmpFavouritePlatforms,_tmpNotificationsEnabled,_tmpDarkModeEnabled,_tmpCachedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUserByEmail(final String email,
      final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFavouriteGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "favouriteGenres");
          final int _cursorIndexOfFavouritePlatforms = CursorUtil.getColumnIndexOrThrow(_cursor, "favouritePlatforms");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfDarkModeEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "darkModeEnabled");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            final String _tmpCreatedAt;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmpCreatedAt = null;
            } else {
              _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final String _tmpFavouriteGenres;
            if (_cursor.isNull(_cursorIndexOfFavouriteGenres)) {
              _tmpFavouriteGenres = null;
            } else {
              _tmpFavouriteGenres = _cursor.getString(_cursorIndexOfFavouriteGenres);
            }
            final String _tmpFavouritePlatforms;
            if (_cursor.isNull(_cursorIndexOfFavouritePlatforms)) {
              _tmpFavouritePlatforms = null;
            } else {
              _tmpFavouritePlatforms = _cursor.getString(_cursorIndexOfFavouritePlatforms);
            }
            final boolean _tmpNotificationsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp != 0;
            final boolean _tmpDarkModeEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfDarkModeEnabled);
            _tmpDarkModeEnabled = _tmp_1 != 0;
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _result = new UserEntity(_tmpId,_tmpUsername,_tmpEmail,_tmpAvatarUrl,_tmpCreatedAt,_tmpFavouriteGenres,_tmpFavouritePlatforms,_tmpNotificationsEnabled,_tmpDarkModeEnabled,_tmpCachedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllUsers(final Continuation<? super List<UserEntity>> $completion) {
    final String _sql = "SELECT * FROM users";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UserEntity>>() {
      @Override
      @NonNull
      public List<UserEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFavouriteGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "favouriteGenres");
          final int _cursorIndexOfFavouritePlatforms = CursorUtil.getColumnIndexOrThrow(_cursor, "favouritePlatforms");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfDarkModeEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "darkModeEnabled");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final List<UserEntity> _result = new ArrayList<UserEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            final String _tmpCreatedAt;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmpCreatedAt = null;
            } else {
              _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final String _tmpFavouriteGenres;
            if (_cursor.isNull(_cursorIndexOfFavouriteGenres)) {
              _tmpFavouriteGenres = null;
            } else {
              _tmpFavouriteGenres = _cursor.getString(_cursorIndexOfFavouriteGenres);
            }
            final String _tmpFavouritePlatforms;
            if (_cursor.isNull(_cursorIndexOfFavouritePlatforms)) {
              _tmpFavouritePlatforms = null;
            } else {
              _tmpFavouritePlatforms = _cursor.getString(_cursorIndexOfFavouritePlatforms);
            }
            final boolean _tmpNotificationsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp != 0;
            final boolean _tmpDarkModeEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfDarkModeEnabled);
            _tmpDarkModeEnabled = _tmp_1 != 0;
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _item = new UserEntity(_tmpId,_tmpUsername,_tmpEmail,_tmpAvatarUrl,_tmpCreatedAt,_tmpFavouriteGenres,_tmpFavouritePlatforms,_tmpNotificationsEnabled,_tmpDarkModeEnabled,_tmpCachedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<UserEntity>> observeAllUsers() {
    final String _sql = "SELECT * FROM users";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<List<UserEntity>>() {
      @Override
      @NonNull
      public List<UserEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFavouriteGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "favouriteGenres");
          final int _cursorIndexOfFavouritePlatforms = CursorUtil.getColumnIndexOrThrow(_cursor, "favouritePlatforms");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfDarkModeEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "darkModeEnabled");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final List<UserEntity> _result = new ArrayList<UserEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            final String _tmpCreatedAt;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmpCreatedAt = null;
            } else {
              _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final String _tmpFavouriteGenres;
            if (_cursor.isNull(_cursorIndexOfFavouriteGenres)) {
              _tmpFavouriteGenres = null;
            } else {
              _tmpFavouriteGenres = _cursor.getString(_cursorIndexOfFavouriteGenres);
            }
            final String _tmpFavouritePlatforms;
            if (_cursor.isNull(_cursorIndexOfFavouritePlatforms)) {
              _tmpFavouritePlatforms = null;
            } else {
              _tmpFavouritePlatforms = _cursor.getString(_cursorIndexOfFavouritePlatforms);
            }
            final boolean _tmpNotificationsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp != 0;
            final boolean _tmpDarkModeEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfDarkModeEnabled);
            _tmpDarkModeEnabled = _tmp_1 != 0;
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _item = new UserEntity(_tmpId,_tmpUsername,_tmpEmail,_tmpAvatarUrl,_tmpCreatedAt,_tmpFavouriteGenres,_tmpFavouritePlatforms,_tmpNotificationsEnabled,_tmpDarkModeEnabled,_tmpCachedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUserCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM users";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getFavouritesByUser(final long userId,
      final Continuation<? super List<FavouriteEntity>> $completion) {
    final String _sql = "SELECT * FROM favourites WHERE userId = ? ORDER BY addedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FavouriteEntity>>() {
      @Override
      @NonNull
      public List<FavouriteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfGameId = CursorUtil.getColumnIndexOrThrow(_cursor, "gameId");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<FavouriteEntity> _result = new ArrayList<FavouriteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavouriteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final long _tmpGameId;
            _tmpGameId = _cursor.getLong(_cursorIndexOfGameId);
            final String _tmpAddedAt;
            if (_cursor.isNull(_cursorIndexOfAddedAt)) {
              _tmpAddedAt = null;
            } else {
              _tmpAddedAt = _cursor.getString(_cursorIndexOfAddedAt);
            }
            _item = new FavouriteEntity(_tmpId,_tmpUserId,_tmpGameId,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FavouriteEntity>> observeFavouritesByUser(final long userId) {
    final String _sql = "SELECT * FROM favourites WHERE userId = ? ORDER BY addedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favourites"}, new Callable<List<FavouriteEntity>>() {
      @Override
      @NonNull
      public List<FavouriteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfGameId = CursorUtil.getColumnIndexOrThrow(_cursor, "gameId");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<FavouriteEntity> _result = new ArrayList<FavouriteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavouriteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final long _tmpGameId;
            _tmpGameId = _cursor.getLong(_cursorIndexOfGameId);
            final String _tmpAddedAt;
            if (_cursor.isNull(_cursorIndexOfAddedAt)) {
              _tmpAddedAt = null;
            } else {
              _tmpAddedAt = _cursor.getString(_cursorIndexOfAddedAt);
            }
            _item = new FavouriteEntity(_tmpId,_tmpUserId,_tmpGameId,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getFavouritesWithGamesByUser(final long userId,
      final Continuation<? super List<FavouriteWithGameEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            f.id as favouriteId,\n"
            + "            f.userId,\n"
            + "            f.gameId,\n"
            + "            f.addedAt,\n"
            + "            g.title as gameTitle,\n"
            + "            g.description as gameDescription,\n"
            + "            g.thumbnailUrl as gameThumbnailUrl,\n"
            + "            g.genre as gameGenre,\n"
            + "            g.platform as gamePlatform,\n"
            + "            g.releaseDate as gameReleaseDate,\n"
            + "            g.rating as gameRating,\n"
            + "            g.developer as gameDeveloper,\n"
            + "            g.publisher as gamePublisher,\n"
            + "            g.isFeatured as gameIsFeatured\n"
            + "        FROM favourites f\n"
            + "        INNER JOIN games g ON f.gameId = g.id\n"
            + "        WHERE f.userId = ?\n"
            + "        ORDER BY f.addedAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FavouriteWithGameEntity>>() {
      @Override
      @NonNull
      public List<FavouriteWithGameEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFavouriteId = 0;
          final int _cursorIndexOfUserId = 1;
          final int _cursorIndexOfGameId = 2;
          final int _cursorIndexOfAddedAt = 3;
          final int _cursorIndexOfGameTitle = 4;
          final int _cursorIndexOfGameDescription = 5;
          final int _cursorIndexOfGameThumbnailUrl = 6;
          final int _cursorIndexOfGameGenre = 7;
          final int _cursorIndexOfGamePlatform = 8;
          final int _cursorIndexOfGameReleaseDate = 9;
          final int _cursorIndexOfGameRating = 10;
          final int _cursorIndexOfGameDeveloper = 11;
          final int _cursorIndexOfGamePublisher = 12;
          final int _cursorIndexOfGameIsFeatured = 13;
          final List<FavouriteWithGameEntity> _result = new ArrayList<FavouriteWithGameEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavouriteWithGameEntity _item;
            final long _tmpFavouriteId;
            _tmpFavouriteId = _cursor.getLong(_cursorIndexOfFavouriteId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final long _tmpGameId;
            _tmpGameId = _cursor.getLong(_cursorIndexOfGameId);
            final String _tmpAddedAt;
            if (_cursor.isNull(_cursorIndexOfAddedAt)) {
              _tmpAddedAt = null;
            } else {
              _tmpAddedAt = _cursor.getString(_cursorIndexOfAddedAt);
            }
            final String _tmpGameTitle;
            if (_cursor.isNull(_cursorIndexOfGameTitle)) {
              _tmpGameTitle = null;
            } else {
              _tmpGameTitle = _cursor.getString(_cursorIndexOfGameTitle);
            }
            final String _tmpGameDescription;
            if (_cursor.isNull(_cursorIndexOfGameDescription)) {
              _tmpGameDescription = null;
            } else {
              _tmpGameDescription = _cursor.getString(_cursorIndexOfGameDescription);
            }
            final String _tmpGameThumbnailUrl;
            if (_cursor.isNull(_cursorIndexOfGameThumbnailUrl)) {
              _tmpGameThumbnailUrl = null;
            } else {
              _tmpGameThumbnailUrl = _cursor.getString(_cursorIndexOfGameThumbnailUrl);
            }
            final String _tmpGameGenre;
            if (_cursor.isNull(_cursorIndexOfGameGenre)) {
              _tmpGameGenre = null;
            } else {
              _tmpGameGenre = _cursor.getString(_cursorIndexOfGameGenre);
            }
            final String _tmpGamePlatform;
            if (_cursor.isNull(_cursorIndexOfGamePlatform)) {
              _tmpGamePlatform = null;
            } else {
              _tmpGamePlatform = _cursor.getString(_cursorIndexOfGamePlatform);
            }
            final String _tmpGameReleaseDate;
            if (_cursor.isNull(_cursorIndexOfGameReleaseDate)) {
              _tmpGameReleaseDate = null;
            } else {
              _tmpGameReleaseDate = _cursor.getString(_cursorIndexOfGameReleaseDate);
            }
            final float _tmpGameRating;
            _tmpGameRating = _cursor.getFloat(_cursorIndexOfGameRating);
            final String _tmpGameDeveloper;
            if (_cursor.isNull(_cursorIndexOfGameDeveloper)) {
              _tmpGameDeveloper = null;
            } else {
              _tmpGameDeveloper = _cursor.getString(_cursorIndexOfGameDeveloper);
            }
            final String _tmpGamePublisher;
            if (_cursor.isNull(_cursorIndexOfGamePublisher)) {
              _tmpGamePublisher = null;
            } else {
              _tmpGamePublisher = _cursor.getString(_cursorIndexOfGamePublisher);
            }
            final boolean _tmpGameIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfGameIsFeatured);
            _tmpGameIsFeatured = _tmp != 0;
            _item = new FavouriteWithGameEntity(_tmpFavouriteId,_tmpUserId,_tmpGameId,_tmpAddedAt,_tmpGameTitle,_tmpGameDescription,_tmpGameThumbnailUrl,_tmpGameGenre,_tmpGamePlatform,_tmpGameReleaseDate,_tmpGameRating,_tmpGameDeveloper,_tmpGamePublisher,_tmpGameIsFeatured);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FavouriteWithGameEntity>> observeFavouritesWithGamesByUser(final long userId) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            f.id as favouriteId,\n"
            + "            f.userId,\n"
            + "            f.gameId,\n"
            + "            f.addedAt,\n"
            + "            g.title as gameTitle,\n"
            + "            g.description as gameDescription,\n"
            + "            g.thumbnailUrl as gameThumbnailUrl,\n"
            + "            g.genre as gameGenre,\n"
            + "            g.platform as gamePlatform,\n"
            + "            g.releaseDate as gameReleaseDate,\n"
            + "            g.rating as gameRating,\n"
            + "            g.developer as gameDeveloper,\n"
            + "            g.publisher as gamePublisher,\n"
            + "            g.isFeatured as gameIsFeatured\n"
            + "        FROM favourites f\n"
            + "        INNER JOIN games g ON f.gameId = g.id\n"
            + "        WHERE f.userId = ?\n"
            + "        ORDER BY f.addedAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favourites",
        "games"}, new Callable<List<FavouriteWithGameEntity>>() {
      @Override
      @NonNull
      public List<FavouriteWithGameEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFavouriteId = 0;
          final int _cursorIndexOfUserId = 1;
          final int _cursorIndexOfGameId = 2;
          final int _cursorIndexOfAddedAt = 3;
          final int _cursorIndexOfGameTitle = 4;
          final int _cursorIndexOfGameDescription = 5;
          final int _cursorIndexOfGameThumbnailUrl = 6;
          final int _cursorIndexOfGameGenre = 7;
          final int _cursorIndexOfGamePlatform = 8;
          final int _cursorIndexOfGameReleaseDate = 9;
          final int _cursorIndexOfGameRating = 10;
          final int _cursorIndexOfGameDeveloper = 11;
          final int _cursorIndexOfGamePublisher = 12;
          final int _cursorIndexOfGameIsFeatured = 13;
          final List<FavouriteWithGameEntity> _result = new ArrayList<FavouriteWithGameEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavouriteWithGameEntity _item;
            final long _tmpFavouriteId;
            _tmpFavouriteId = _cursor.getLong(_cursorIndexOfFavouriteId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final long _tmpGameId;
            _tmpGameId = _cursor.getLong(_cursorIndexOfGameId);
            final String _tmpAddedAt;
            if (_cursor.isNull(_cursorIndexOfAddedAt)) {
              _tmpAddedAt = null;
            } else {
              _tmpAddedAt = _cursor.getString(_cursorIndexOfAddedAt);
            }
            final String _tmpGameTitle;
            if (_cursor.isNull(_cursorIndexOfGameTitle)) {
              _tmpGameTitle = null;
            } else {
              _tmpGameTitle = _cursor.getString(_cursorIndexOfGameTitle);
            }
            final String _tmpGameDescription;
            if (_cursor.isNull(_cursorIndexOfGameDescription)) {
              _tmpGameDescription = null;
            } else {
              _tmpGameDescription = _cursor.getString(_cursorIndexOfGameDescription);
            }
            final String _tmpGameThumbnailUrl;
            if (_cursor.isNull(_cursorIndexOfGameThumbnailUrl)) {
              _tmpGameThumbnailUrl = null;
            } else {
              _tmpGameThumbnailUrl = _cursor.getString(_cursorIndexOfGameThumbnailUrl);
            }
            final String _tmpGameGenre;
            if (_cursor.isNull(_cursorIndexOfGameGenre)) {
              _tmpGameGenre = null;
            } else {
              _tmpGameGenre = _cursor.getString(_cursorIndexOfGameGenre);
            }
            final String _tmpGamePlatform;
            if (_cursor.isNull(_cursorIndexOfGamePlatform)) {
              _tmpGamePlatform = null;
            } else {
              _tmpGamePlatform = _cursor.getString(_cursorIndexOfGamePlatform);
            }
            final String _tmpGameReleaseDate;
            if (_cursor.isNull(_cursorIndexOfGameReleaseDate)) {
              _tmpGameReleaseDate = null;
            } else {
              _tmpGameReleaseDate = _cursor.getString(_cursorIndexOfGameReleaseDate);
            }
            final float _tmpGameRating;
            _tmpGameRating = _cursor.getFloat(_cursorIndexOfGameRating);
            final String _tmpGameDeveloper;
            if (_cursor.isNull(_cursorIndexOfGameDeveloper)) {
              _tmpGameDeveloper = null;
            } else {
              _tmpGameDeveloper = _cursor.getString(_cursorIndexOfGameDeveloper);
            }
            final String _tmpGamePublisher;
            if (_cursor.isNull(_cursorIndexOfGamePublisher)) {
              _tmpGamePublisher = null;
            } else {
              _tmpGamePublisher = _cursor.getString(_cursorIndexOfGamePublisher);
            }
            final boolean _tmpGameIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfGameIsFeatured);
            _tmpGameIsFeatured = _tmp != 0;
            _item = new FavouriteWithGameEntity(_tmpFavouriteId,_tmpUserId,_tmpGameId,_tmpAddedAt,_tmpGameTitle,_tmpGameDescription,_tmpGameThumbnailUrl,_tmpGameGenre,_tmpGamePlatform,_tmpGameReleaseDate,_tmpGameRating,_tmpGameDeveloper,_tmpGamePublisher,_tmpGameIsFeatured);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object isFavourited(final long userId, final long gameId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM favourites WHERE userId = ? AND gameId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, gameId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Boolean> observeIsFavourited(final long userId, final long gameId) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM favourites WHERE userId = ? AND gameId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, gameId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favourites"}, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getFavouriteCount(final long userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM favourites WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> observeFavouriteCount(final long userId) {
    final String _sql = "SELECT COUNT(*) FROM favourites WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favourites"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
