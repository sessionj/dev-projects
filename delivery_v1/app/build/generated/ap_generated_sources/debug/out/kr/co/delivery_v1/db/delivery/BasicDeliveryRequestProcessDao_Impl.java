package kr.co.delivery_v1.db.delivery;

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
import kr.co.delivery_v1.models.DeliveryRequestModelView;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BasicDeliveryRequestProcessDao_Impl implements BasicDeliveryRequestProcessDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DeliveryRequestModelView> __insertionAdapterOfDeliveryRequestModelView;

  private final SharedSQLiteStatement __preparedStmtOfIsDeliveryRequestDel;

  public BasicDeliveryRequestProcessDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDeliveryRequestModelView = new EntityInsertionAdapter<DeliveryRequestModelView>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tb_delivery_request` (`reqdate`,`deliverycourse`,`deliverycoursenm`,`requestdate`,`delivery_count`,`combination_key`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DeliveryRequestModelView value) {
        if (value.getReqdate() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getReqdate());
        }
        if (value.getDeliverycourse() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDeliverycourse());
        }
        if (value.getDeliverycoursenm() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDeliverycoursenm());
        }
        if (value.getRequestdate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRequestdate());
        }
        stmt.bindLong(5, value.getDelivery_count());
        stmt.bindLong(6, value.getCombination_key());
      }
    };
    this.__preparedStmtOfIsDeliveryRequestDel = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM tb_delivery_request WHERE reqdate > ? ";
        return _query;
      }
    };
  }

  @Override
  public void isDeliveryRequestAdd(final DeliveryRequestModelView deliveryRequestModelView) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDeliveryRequestModelView.insert(deliveryRequestModelView);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void isDeliveryRequestDel(final String sysdate) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIsDeliveryRequestDel.acquire();
    int _argIndex = 1;
    if (sysdate == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, sysdate);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfIsDeliveryRequestDel.release(_stmt);
    }
  }

  @Override
  public List<DeliveryRequestModelView> getDeliveryRequestList() {
    final String _sql = "SELECT * FROM tb_delivery_request";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfReqdate = CursorUtil.getColumnIndexOrThrow(_cursor, "reqdate");
      final int _cursorIndexOfDeliverycourse = CursorUtil.getColumnIndexOrThrow(_cursor, "deliverycourse");
      final int _cursorIndexOfDeliverycoursenm = CursorUtil.getColumnIndexOrThrow(_cursor, "deliverycoursenm");
      final int _cursorIndexOfRequestdate = CursorUtil.getColumnIndexOrThrow(_cursor, "requestdate");
      final int _cursorIndexOfDeliveryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_count");
      final int _cursorIndexOfCombinationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "combination_key");
      final List<DeliveryRequestModelView> _result = new ArrayList<DeliveryRequestModelView>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DeliveryRequestModelView _item;
        _item = new DeliveryRequestModelView();
        final String _tmpReqdate;
        if (_cursor.isNull(_cursorIndexOfReqdate)) {
          _tmpReqdate = null;
        } else {
          _tmpReqdate = _cursor.getString(_cursorIndexOfReqdate);
        }
        _item.setReqdate(_tmpReqdate);
        final String _tmpDeliverycourse;
        if (_cursor.isNull(_cursorIndexOfDeliverycourse)) {
          _tmpDeliverycourse = null;
        } else {
          _tmpDeliverycourse = _cursor.getString(_cursorIndexOfDeliverycourse);
        }
        _item.setDeliverycourse(_tmpDeliverycourse);
        final String _tmpDeliverycoursenm;
        if (_cursor.isNull(_cursorIndexOfDeliverycoursenm)) {
          _tmpDeliverycoursenm = null;
        } else {
          _tmpDeliverycoursenm = _cursor.getString(_cursorIndexOfDeliverycoursenm);
        }
        _item.setDeliverycoursenm(_tmpDeliverycoursenm);
        final String _tmpRequestdate;
        if (_cursor.isNull(_cursorIndexOfRequestdate)) {
          _tmpRequestdate = null;
        } else {
          _tmpRequestdate = _cursor.getString(_cursorIndexOfRequestdate);
        }
        _item.setRequestdate(_tmpRequestdate);
        final int _tmpDelivery_count;
        _tmpDelivery_count = _cursor.getInt(_cursorIndexOfDeliveryCount);
        _item.setDelivery_count(_tmpDelivery_count);
        final int _tmpCombination_key;
        _tmpCombination_key = _cursor.getInt(_cursorIndexOfCombinationKey);
        _item.setCombination_key(_tmpCombination_key);
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
