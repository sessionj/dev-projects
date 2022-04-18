package kr.co.delivery_v1.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kr.co.delivery_v1.models.LoginModelView;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BasicProcessDao_Impl implements BasicProcessDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LoginModelView> __insertionAdapterOfLoginModelView;

  private final SharedSQLiteStatement __preparedStmtOfApplicationData_deleteAll;

  public BasicProcessDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoginModelView = new EntityInsertionAdapter<LoginModelView>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tb_profile` (`id`,`phonenumber`,`agencycode`,`deliverycourse`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LoginModelView value) {
        stmt.bindLong(1, value.getId());
        if (value.getPhonenumber() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPhonenumber());
        }
        if (value.getAgencycode() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAgencycode());
        }
        if (value.getDeliverycourse() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDeliverycourse());
        }
      }
    };
    this.__preparedStmtOfApplicationData_deleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM tb_profile";
        return _query;
      }
    };
  }

  @Override
  public void applicationData_insert(final LoginModelView loginEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLoginModelView.insert(loginEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void applicationData_deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfApplicationData_deleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfApplicationData_deleteAll.release(_stmt);
    }
  }

  @Override
  public List<LoginModelView> getAll() {
    final String _sql = "SELECT * FROM tb_profile";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPhonenumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phonenumber");
      final int _cursorIndexOfAgencycode = CursorUtil.getColumnIndexOrThrow(_cursor, "agencycode");
      final int _cursorIndexOfDeliverycourse = CursorUtil.getColumnIndexOrThrow(_cursor, "deliverycourse");
      final List<LoginModelView> _result = new ArrayList<LoginModelView>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LoginModelView _item;
        _item = new LoginModelView();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpPhonenumber;
        if (_cursor.isNull(_cursorIndexOfPhonenumber)) {
          _tmpPhonenumber = null;
        } else {
          _tmpPhonenumber = _cursor.getString(_cursorIndexOfPhonenumber);
        }
        _item.setPhonenumber(_tmpPhonenumber);
        final String _tmpAgencycode;
        if (_cursor.isNull(_cursorIndexOfAgencycode)) {
          _tmpAgencycode = null;
        } else {
          _tmpAgencycode = _cursor.getString(_cursorIndexOfAgencycode);
        }
        _item.setAgencycode(_tmpAgencycode);
        final String _tmpDeliverycourse;
        if (_cursor.isNull(_cursorIndexOfDeliverycourse)) {
          _tmpDeliverycourse = null;
        } else {
          _tmpDeliverycourse = _cursor.getString(_cursorIndexOfDeliverycourse);
        }
        _item.setDeliverycourse(_tmpDeliverycourse);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
