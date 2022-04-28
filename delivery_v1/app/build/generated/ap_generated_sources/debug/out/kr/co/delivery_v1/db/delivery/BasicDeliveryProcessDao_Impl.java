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
import kr.co.delivery_v1.models.DeliveryModelView;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BasicDeliveryProcessDao_Impl implements BasicDeliveryProcessDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DeliveryModelView> __insertionAdapterOfDeliveryModelView;

  private final SharedSQLiteStatement __preparedStmtOfApplicationData_deleteAll;

  private final SharedSQLiteStatement __preparedStmtOfIsDeliveryStatusChange;

  public BasicDeliveryProcessDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDeliveryModelView = new EntityInsertionAdapter<DeliveryModelView>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tb_delivery` (`billno`,`input_date`,`input_time`,`transcode`,`sendingagencycode`,`arrivalagencycode`,`sendingmantel`,`sendingman`,`arrivalmantel`,`arrivalmantel2`,`arrivalman`,`zipcode`,`adress`,`prefare`,`fare`,`deliveryfare`,`ogideliveryfare`,`distance`,`payway`,`goods`,`pojang`,`qty`,`weight`,`memo`,`billstate`,`deliverycourse`,`creatdate`,`delivery_state`,`delivery_picture_path`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DeliveryModelView value) {
        if (value.getBillno() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getBillno());
        }
        if (value.getInput_date() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getInput_date());
        }
        if (value.getInput_time() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getInput_time());
        }
        if (value.getTranscode() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTranscode());
        }
        if (value.getSendingagencycode() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSendingagencycode());
        }
        if (value.getArrivalagencycode() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getArrivalagencycode());
        }
        if (value.getSendingmantel() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSendingmantel());
        }
        if (value.getSendingman() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getSendingman());
        }
        if (value.getArrivalmantel() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getArrivalmantel());
        }
        if (value.getArrivalmantel2() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getArrivalmantel2());
        }
        if (value.getArrivalman() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getArrivalman());
        }
        if (value.getZipcode() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getZipcode());
        }
        if (value.getAdress() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getAdress());
        }
        if (value.getPrefare() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getPrefare());
        }
        if (value.getFare() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getFare());
        }
        if (value.getDeliveryfare() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getDeliveryfare());
        }
        if (value.getOgideliveryfare() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getOgideliveryfare());
        }
        if (value.getDistance() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getDistance());
        }
        if (value.getPayway() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getPayway());
        }
        if (value.getGoods() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getGoods());
        }
        if (value.getPojang() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getPojang());
        }
        stmt.bindLong(22, value.getQty());
        if (value.getWeight() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindString(23, value.getWeight());
        }
        if (value.getMemo() == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.getMemo());
        }
        if (value.getBillstate() == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindString(25, value.getBillstate());
        }
        if (value.getDeliverycourse() == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.getDeliverycourse());
        }
        if (value.getCreatdate() == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindString(27, value.getCreatdate());
        }
        if (value.getDelivery_state() == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.getDelivery_state());
        }
        if (value.getDelivery_picture_path() == null) {
          stmt.bindNull(29);
        } else {
          stmt.bindString(29, value.getDelivery_picture_path());
        }
      }
    };
    this.__preparedStmtOfApplicationData_deleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM tb_delivery";
        return _query;
      }
    };
    this.__preparedStmtOfIsDeliveryStatusChange = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE tb_delivery SET delivery_state =? WHERE billno = ? ";
        return _query;
      }
    };
  }

  @Override
  public void applicationData_insert(final DeliveryModelView deliveryModelView) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDeliveryModelView.insert(deliveryModelView);
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
  public void isDeliveryStatusChange(final String billNo, final String deliveryStatus) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIsDeliveryStatusChange.acquire();
    int _argIndex = 1;
    if (deliveryStatus == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, deliveryStatus);
    }
    _argIndex = 2;
    if (billNo == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, billNo);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfIsDeliveryStatusChange.release(_stmt);
    }
  }

  @Override
  public List<DeliveryModelView> getDayList() {
    final String _sql = "SELECT * FROM tb_delivery";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfBillno = CursorUtil.getColumnIndexOrThrow(_cursor, "billno");
      final int _cursorIndexOfInputDate = CursorUtil.getColumnIndexOrThrow(_cursor, "input_date");
      final int _cursorIndexOfInputTime = CursorUtil.getColumnIndexOrThrow(_cursor, "input_time");
      final int _cursorIndexOfTranscode = CursorUtil.getColumnIndexOrThrow(_cursor, "transcode");
      final int _cursorIndexOfSendingagencycode = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingagencycode");
      final int _cursorIndexOfArrivalagencycode = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalagencycode");
      final int _cursorIndexOfSendingmantel = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingmantel");
      final int _cursorIndexOfSendingman = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingman");
      final int _cursorIndexOfArrivalmantel = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalmantel");
      final int _cursorIndexOfArrivalmantel2 = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalmantel2");
      final int _cursorIndexOfArrivalman = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalman");
      final int _cursorIndexOfZipcode = CursorUtil.getColumnIndexOrThrow(_cursor, "zipcode");
      final int _cursorIndexOfAdress = CursorUtil.getColumnIndexOrThrow(_cursor, "adress");
      final int _cursorIndexOfPrefare = CursorUtil.getColumnIndexOrThrow(_cursor, "prefare");
      final int _cursorIndexOfFare = CursorUtil.getColumnIndexOrThrow(_cursor, "fare");
      final int _cursorIndexOfDeliveryfare = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryfare");
      final int _cursorIndexOfOgideliveryfare = CursorUtil.getColumnIndexOrThrow(_cursor, "ogideliveryfare");
      final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
      final int _cursorIndexOfPayway = CursorUtil.getColumnIndexOrThrow(_cursor, "payway");
      final int _cursorIndexOfGoods = CursorUtil.getColumnIndexOrThrow(_cursor, "goods");
      final int _cursorIndexOfPojang = CursorUtil.getColumnIndexOrThrow(_cursor, "pojang");
      final int _cursorIndexOfQty = CursorUtil.getColumnIndexOrThrow(_cursor, "qty");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
      final int _cursorIndexOfBillstate = CursorUtil.getColumnIndexOrThrow(_cursor, "billstate");
      final int _cursorIndexOfDeliverycourse = CursorUtil.getColumnIndexOrThrow(_cursor, "deliverycourse");
      final int _cursorIndexOfCreatdate = CursorUtil.getColumnIndexOrThrow(_cursor, "creatdate");
      final int _cursorIndexOfDeliveryState = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_state");
      final int _cursorIndexOfDeliveryPicturePath = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_picture_path");
      final List<DeliveryModelView> _result = new ArrayList<DeliveryModelView>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DeliveryModelView _item;
        _item = new DeliveryModelView();
        final String _tmpBillno;
        if (_cursor.isNull(_cursorIndexOfBillno)) {
          _tmpBillno = null;
        } else {
          _tmpBillno = _cursor.getString(_cursorIndexOfBillno);
        }
        _item.setBillno(_tmpBillno);
        final String _tmpInput_date;
        if (_cursor.isNull(_cursorIndexOfInputDate)) {
          _tmpInput_date = null;
        } else {
          _tmpInput_date = _cursor.getString(_cursorIndexOfInputDate);
        }
        _item.setInput_date(_tmpInput_date);
        final String _tmpInput_time;
        if (_cursor.isNull(_cursorIndexOfInputTime)) {
          _tmpInput_time = null;
        } else {
          _tmpInput_time = _cursor.getString(_cursorIndexOfInputTime);
        }
        _item.setInput_time(_tmpInput_time);
        final String _tmpTranscode;
        if (_cursor.isNull(_cursorIndexOfTranscode)) {
          _tmpTranscode = null;
        } else {
          _tmpTranscode = _cursor.getString(_cursorIndexOfTranscode);
        }
        _item.setTranscode(_tmpTranscode);
        final String _tmpSendingagencycode;
        if (_cursor.isNull(_cursorIndexOfSendingagencycode)) {
          _tmpSendingagencycode = null;
        } else {
          _tmpSendingagencycode = _cursor.getString(_cursorIndexOfSendingagencycode);
        }
        _item.setSendingagencycode(_tmpSendingagencycode);
        final String _tmpArrivalagencycode;
        if (_cursor.isNull(_cursorIndexOfArrivalagencycode)) {
          _tmpArrivalagencycode = null;
        } else {
          _tmpArrivalagencycode = _cursor.getString(_cursorIndexOfArrivalagencycode);
        }
        _item.setArrivalagencycode(_tmpArrivalagencycode);
        final String _tmpSendingmantel;
        if (_cursor.isNull(_cursorIndexOfSendingmantel)) {
          _tmpSendingmantel = null;
        } else {
          _tmpSendingmantel = _cursor.getString(_cursorIndexOfSendingmantel);
        }
        _item.setSendingmantel(_tmpSendingmantel);
        final String _tmpSendingman;
        if (_cursor.isNull(_cursorIndexOfSendingman)) {
          _tmpSendingman = null;
        } else {
          _tmpSendingman = _cursor.getString(_cursorIndexOfSendingman);
        }
        _item.setSendingman(_tmpSendingman);
        final String _tmpArrivalmantel;
        if (_cursor.isNull(_cursorIndexOfArrivalmantel)) {
          _tmpArrivalmantel = null;
        } else {
          _tmpArrivalmantel = _cursor.getString(_cursorIndexOfArrivalmantel);
        }
        _item.setArrivalmantel(_tmpArrivalmantel);
        final String _tmpArrivalmantel2;
        if (_cursor.isNull(_cursorIndexOfArrivalmantel2)) {
          _tmpArrivalmantel2 = null;
        } else {
          _tmpArrivalmantel2 = _cursor.getString(_cursorIndexOfArrivalmantel2);
        }
        _item.setArrivalmantel2(_tmpArrivalmantel2);
        final String _tmpArrivalman;
        if (_cursor.isNull(_cursorIndexOfArrivalman)) {
          _tmpArrivalman = null;
        } else {
          _tmpArrivalman = _cursor.getString(_cursorIndexOfArrivalman);
        }
        _item.setArrivalman(_tmpArrivalman);
        final String _tmpZipcode;
        if (_cursor.isNull(_cursorIndexOfZipcode)) {
          _tmpZipcode = null;
        } else {
          _tmpZipcode = _cursor.getString(_cursorIndexOfZipcode);
        }
        _item.setZipcode(_tmpZipcode);
        final String _tmpAdress;
        if (_cursor.isNull(_cursorIndexOfAdress)) {
          _tmpAdress = null;
        } else {
          _tmpAdress = _cursor.getString(_cursorIndexOfAdress);
        }
        _item.setAdress(_tmpAdress);
        final String _tmpPrefare;
        if (_cursor.isNull(_cursorIndexOfPrefare)) {
          _tmpPrefare = null;
        } else {
          _tmpPrefare = _cursor.getString(_cursorIndexOfPrefare);
        }
        _item.setPrefare(_tmpPrefare);
        final String _tmpFare;
        if (_cursor.isNull(_cursorIndexOfFare)) {
          _tmpFare = null;
        } else {
          _tmpFare = _cursor.getString(_cursorIndexOfFare);
        }
        _item.setFare(_tmpFare);
        final String _tmpDeliveryfare;
        if (_cursor.isNull(_cursorIndexOfDeliveryfare)) {
          _tmpDeliveryfare = null;
        } else {
          _tmpDeliveryfare = _cursor.getString(_cursorIndexOfDeliveryfare);
        }
        _item.setDeliveryfare(_tmpDeliveryfare);
        final String _tmpOgideliveryfare;
        if (_cursor.isNull(_cursorIndexOfOgideliveryfare)) {
          _tmpOgideliveryfare = null;
        } else {
          _tmpOgideliveryfare = _cursor.getString(_cursorIndexOfOgideliveryfare);
        }
        _item.setOgideliveryfare(_tmpOgideliveryfare);
        final String _tmpDistance;
        if (_cursor.isNull(_cursorIndexOfDistance)) {
          _tmpDistance = null;
        } else {
          _tmpDistance = _cursor.getString(_cursorIndexOfDistance);
        }
        _item.setDistance(_tmpDistance);
        final String _tmpPayway;
        if (_cursor.isNull(_cursorIndexOfPayway)) {
          _tmpPayway = null;
        } else {
          _tmpPayway = _cursor.getString(_cursorIndexOfPayway);
        }
        _item.setPayway(_tmpPayway);
        final String _tmpGoods;
        if (_cursor.isNull(_cursorIndexOfGoods)) {
          _tmpGoods = null;
        } else {
          _tmpGoods = _cursor.getString(_cursorIndexOfGoods);
        }
        _item.setGoods(_tmpGoods);
        final String _tmpPojang;
        if (_cursor.isNull(_cursorIndexOfPojang)) {
          _tmpPojang = null;
        } else {
          _tmpPojang = _cursor.getString(_cursorIndexOfPojang);
        }
        _item.setPojang(_tmpPojang);
        final int _tmpQty;
        _tmpQty = _cursor.getInt(_cursorIndexOfQty);
        _item.setQty(_tmpQty);
        final String _tmpWeight;
        if (_cursor.isNull(_cursorIndexOfWeight)) {
          _tmpWeight = null;
        } else {
          _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
        }
        _item.setWeight(_tmpWeight);
        final String _tmpMemo;
        if (_cursor.isNull(_cursorIndexOfMemo)) {
          _tmpMemo = null;
        } else {
          _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
        }
        _item.setMemo(_tmpMemo);
        final String _tmpBillstate;
        if (_cursor.isNull(_cursorIndexOfBillstate)) {
          _tmpBillstate = null;
        } else {
          _tmpBillstate = _cursor.getString(_cursorIndexOfBillstate);
        }
        _item.setBillstate(_tmpBillstate);
        final String _tmpDeliverycourse;
        if (_cursor.isNull(_cursorIndexOfDeliverycourse)) {
          _tmpDeliverycourse = null;
        } else {
          _tmpDeliverycourse = _cursor.getString(_cursorIndexOfDeliverycourse);
        }
        _item.setDeliverycourse(_tmpDeliverycourse);
        final String _tmpCreatdate;
        if (_cursor.isNull(_cursorIndexOfCreatdate)) {
          _tmpCreatdate = null;
        } else {
          _tmpCreatdate = _cursor.getString(_cursorIndexOfCreatdate);
        }
        _item.setCreatdate(_tmpCreatdate);
        final String _tmpDelivery_state;
        if (_cursor.isNull(_cursorIndexOfDeliveryState)) {
          _tmpDelivery_state = null;
        } else {
          _tmpDelivery_state = _cursor.getString(_cursorIndexOfDeliveryState);
        }
        _item.setDelivery_state(_tmpDelivery_state);
        final String _tmpDelivery_picture_path;
        if (_cursor.isNull(_cursorIndexOfDeliveryPicturePath)) {
          _tmpDelivery_picture_path = null;
        } else {
          _tmpDelivery_picture_path = _cursor.getString(_cursorIndexOfDeliveryPicturePath);
        }
        _item.setDelivery_picture_path(_tmpDelivery_picture_path);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<DeliveryModelView> getDayList(final String createDt, final String deliveryCourse) {
    final String _sql = "SELECT * FROM tb_delivery  WHERE creatdate = ? AND deliverycourse =? ORDER BY deliverycourse DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (createDt == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, createDt);
    }
    _argIndex = 2;
    if (deliveryCourse == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, deliveryCourse);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfBillno = CursorUtil.getColumnIndexOrThrow(_cursor, "billno");
      final int _cursorIndexOfInputDate = CursorUtil.getColumnIndexOrThrow(_cursor, "input_date");
      final int _cursorIndexOfInputTime = CursorUtil.getColumnIndexOrThrow(_cursor, "input_time");
      final int _cursorIndexOfTranscode = CursorUtil.getColumnIndexOrThrow(_cursor, "transcode");
      final int _cursorIndexOfSendingagencycode = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingagencycode");
      final int _cursorIndexOfArrivalagencycode = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalagencycode");
      final int _cursorIndexOfSendingmantel = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingmantel");
      final int _cursorIndexOfSendingman = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingman");
      final int _cursorIndexOfArrivalmantel = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalmantel");
      final int _cursorIndexOfArrivalmantel2 = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalmantel2");
      final int _cursorIndexOfArrivalman = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalman");
      final int _cursorIndexOfZipcode = CursorUtil.getColumnIndexOrThrow(_cursor, "zipcode");
      final int _cursorIndexOfAdress = CursorUtil.getColumnIndexOrThrow(_cursor, "adress");
      final int _cursorIndexOfPrefare = CursorUtil.getColumnIndexOrThrow(_cursor, "prefare");
      final int _cursorIndexOfFare = CursorUtil.getColumnIndexOrThrow(_cursor, "fare");
      final int _cursorIndexOfDeliveryfare = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryfare");
      final int _cursorIndexOfOgideliveryfare = CursorUtil.getColumnIndexOrThrow(_cursor, "ogideliveryfare");
      final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
      final int _cursorIndexOfPayway = CursorUtil.getColumnIndexOrThrow(_cursor, "payway");
      final int _cursorIndexOfGoods = CursorUtil.getColumnIndexOrThrow(_cursor, "goods");
      final int _cursorIndexOfPojang = CursorUtil.getColumnIndexOrThrow(_cursor, "pojang");
      final int _cursorIndexOfQty = CursorUtil.getColumnIndexOrThrow(_cursor, "qty");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
      final int _cursorIndexOfBillstate = CursorUtil.getColumnIndexOrThrow(_cursor, "billstate");
      final int _cursorIndexOfDeliverycourse = CursorUtil.getColumnIndexOrThrow(_cursor, "deliverycourse");
      final int _cursorIndexOfCreatdate = CursorUtil.getColumnIndexOrThrow(_cursor, "creatdate");
      final int _cursorIndexOfDeliveryState = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_state");
      final int _cursorIndexOfDeliveryPicturePath = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_picture_path");
      final List<DeliveryModelView> _result = new ArrayList<DeliveryModelView>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DeliveryModelView _item;
        _item = new DeliveryModelView();
        final String _tmpBillno;
        if (_cursor.isNull(_cursorIndexOfBillno)) {
          _tmpBillno = null;
        } else {
          _tmpBillno = _cursor.getString(_cursorIndexOfBillno);
        }
        _item.setBillno(_tmpBillno);
        final String _tmpInput_date;
        if (_cursor.isNull(_cursorIndexOfInputDate)) {
          _tmpInput_date = null;
        } else {
          _tmpInput_date = _cursor.getString(_cursorIndexOfInputDate);
        }
        _item.setInput_date(_tmpInput_date);
        final String _tmpInput_time;
        if (_cursor.isNull(_cursorIndexOfInputTime)) {
          _tmpInput_time = null;
        } else {
          _tmpInput_time = _cursor.getString(_cursorIndexOfInputTime);
        }
        _item.setInput_time(_tmpInput_time);
        final String _tmpTranscode;
        if (_cursor.isNull(_cursorIndexOfTranscode)) {
          _tmpTranscode = null;
        } else {
          _tmpTranscode = _cursor.getString(_cursorIndexOfTranscode);
        }
        _item.setTranscode(_tmpTranscode);
        final String _tmpSendingagencycode;
        if (_cursor.isNull(_cursorIndexOfSendingagencycode)) {
          _tmpSendingagencycode = null;
        } else {
          _tmpSendingagencycode = _cursor.getString(_cursorIndexOfSendingagencycode);
        }
        _item.setSendingagencycode(_tmpSendingagencycode);
        final String _tmpArrivalagencycode;
        if (_cursor.isNull(_cursorIndexOfArrivalagencycode)) {
          _tmpArrivalagencycode = null;
        } else {
          _tmpArrivalagencycode = _cursor.getString(_cursorIndexOfArrivalagencycode);
        }
        _item.setArrivalagencycode(_tmpArrivalagencycode);
        final String _tmpSendingmantel;
        if (_cursor.isNull(_cursorIndexOfSendingmantel)) {
          _tmpSendingmantel = null;
        } else {
          _tmpSendingmantel = _cursor.getString(_cursorIndexOfSendingmantel);
        }
        _item.setSendingmantel(_tmpSendingmantel);
        final String _tmpSendingman;
        if (_cursor.isNull(_cursorIndexOfSendingman)) {
          _tmpSendingman = null;
        } else {
          _tmpSendingman = _cursor.getString(_cursorIndexOfSendingman);
        }
        _item.setSendingman(_tmpSendingman);
        final String _tmpArrivalmantel;
        if (_cursor.isNull(_cursorIndexOfArrivalmantel)) {
          _tmpArrivalmantel = null;
        } else {
          _tmpArrivalmantel = _cursor.getString(_cursorIndexOfArrivalmantel);
        }
        _item.setArrivalmantel(_tmpArrivalmantel);
        final String _tmpArrivalmantel2;
        if (_cursor.isNull(_cursorIndexOfArrivalmantel2)) {
          _tmpArrivalmantel2 = null;
        } else {
          _tmpArrivalmantel2 = _cursor.getString(_cursorIndexOfArrivalmantel2);
        }
        _item.setArrivalmantel2(_tmpArrivalmantel2);
        final String _tmpArrivalman;
        if (_cursor.isNull(_cursorIndexOfArrivalman)) {
          _tmpArrivalman = null;
        } else {
          _tmpArrivalman = _cursor.getString(_cursorIndexOfArrivalman);
        }
        _item.setArrivalman(_tmpArrivalman);
        final String _tmpZipcode;
        if (_cursor.isNull(_cursorIndexOfZipcode)) {
          _tmpZipcode = null;
        } else {
          _tmpZipcode = _cursor.getString(_cursorIndexOfZipcode);
        }
        _item.setZipcode(_tmpZipcode);
        final String _tmpAdress;
        if (_cursor.isNull(_cursorIndexOfAdress)) {
          _tmpAdress = null;
        } else {
          _tmpAdress = _cursor.getString(_cursorIndexOfAdress);
        }
        _item.setAdress(_tmpAdress);
        final String _tmpPrefare;
        if (_cursor.isNull(_cursorIndexOfPrefare)) {
          _tmpPrefare = null;
        } else {
          _tmpPrefare = _cursor.getString(_cursorIndexOfPrefare);
        }
        _item.setPrefare(_tmpPrefare);
        final String _tmpFare;
        if (_cursor.isNull(_cursorIndexOfFare)) {
          _tmpFare = null;
        } else {
          _tmpFare = _cursor.getString(_cursorIndexOfFare);
        }
        _item.setFare(_tmpFare);
        final String _tmpDeliveryfare;
        if (_cursor.isNull(_cursorIndexOfDeliveryfare)) {
          _tmpDeliveryfare = null;
        } else {
          _tmpDeliveryfare = _cursor.getString(_cursorIndexOfDeliveryfare);
        }
        _item.setDeliveryfare(_tmpDeliveryfare);
        final String _tmpOgideliveryfare;
        if (_cursor.isNull(_cursorIndexOfOgideliveryfare)) {
          _tmpOgideliveryfare = null;
        } else {
          _tmpOgideliveryfare = _cursor.getString(_cursorIndexOfOgideliveryfare);
        }
        _item.setOgideliveryfare(_tmpOgideliveryfare);
        final String _tmpDistance;
        if (_cursor.isNull(_cursorIndexOfDistance)) {
          _tmpDistance = null;
        } else {
          _tmpDistance = _cursor.getString(_cursorIndexOfDistance);
        }
        _item.setDistance(_tmpDistance);
        final String _tmpPayway;
        if (_cursor.isNull(_cursorIndexOfPayway)) {
          _tmpPayway = null;
        } else {
          _tmpPayway = _cursor.getString(_cursorIndexOfPayway);
        }
        _item.setPayway(_tmpPayway);
        final String _tmpGoods;
        if (_cursor.isNull(_cursorIndexOfGoods)) {
          _tmpGoods = null;
        } else {
          _tmpGoods = _cursor.getString(_cursorIndexOfGoods);
        }
        _item.setGoods(_tmpGoods);
        final String _tmpPojang;
        if (_cursor.isNull(_cursorIndexOfPojang)) {
          _tmpPojang = null;
        } else {
          _tmpPojang = _cursor.getString(_cursorIndexOfPojang);
        }
        _item.setPojang(_tmpPojang);
        final int _tmpQty;
        _tmpQty = _cursor.getInt(_cursorIndexOfQty);
        _item.setQty(_tmpQty);
        final String _tmpWeight;
        if (_cursor.isNull(_cursorIndexOfWeight)) {
          _tmpWeight = null;
        } else {
          _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
        }
        _item.setWeight(_tmpWeight);
        final String _tmpMemo;
        if (_cursor.isNull(_cursorIndexOfMemo)) {
          _tmpMemo = null;
        } else {
          _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
        }
        _item.setMemo(_tmpMemo);
        final String _tmpBillstate;
        if (_cursor.isNull(_cursorIndexOfBillstate)) {
          _tmpBillstate = null;
        } else {
          _tmpBillstate = _cursor.getString(_cursorIndexOfBillstate);
        }
        _item.setBillstate(_tmpBillstate);
        final String _tmpDeliverycourse;
        if (_cursor.isNull(_cursorIndexOfDeliverycourse)) {
          _tmpDeliverycourse = null;
        } else {
          _tmpDeliverycourse = _cursor.getString(_cursorIndexOfDeliverycourse);
        }
        _item.setDeliverycourse(_tmpDeliverycourse);
        final String _tmpCreatdate;
        if (_cursor.isNull(_cursorIndexOfCreatdate)) {
          _tmpCreatdate = null;
        } else {
          _tmpCreatdate = _cursor.getString(_cursorIndexOfCreatdate);
        }
        _item.setCreatdate(_tmpCreatdate);
        final String _tmpDelivery_state;
        if (_cursor.isNull(_cursorIndexOfDeliveryState)) {
          _tmpDelivery_state = null;
        } else {
          _tmpDelivery_state = _cursor.getString(_cursorIndexOfDeliveryState);
        }
        _item.setDelivery_state(_tmpDelivery_state);
        final String _tmpDelivery_picture_path;
        if (_cursor.isNull(_cursorIndexOfDeliveryPicturePath)) {
          _tmpDelivery_picture_path = null;
        } else {
          _tmpDelivery_picture_path = _cursor.getString(_cursorIndexOfDeliveryPicturePath);
        }
        _item.setDelivery_picture_path(_tmpDelivery_picture_path);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public DeliveryModelView getDayArticle(final String billNo) {
    final String _sql = "SELECT * FROM tb_delivery WHERE  billno = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (billNo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, billNo);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfBillno = CursorUtil.getColumnIndexOrThrow(_cursor, "billno");
      final int _cursorIndexOfInputDate = CursorUtil.getColumnIndexOrThrow(_cursor, "input_date");
      final int _cursorIndexOfInputTime = CursorUtil.getColumnIndexOrThrow(_cursor, "input_time");
      final int _cursorIndexOfTranscode = CursorUtil.getColumnIndexOrThrow(_cursor, "transcode");
      final int _cursorIndexOfSendingagencycode = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingagencycode");
      final int _cursorIndexOfArrivalagencycode = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalagencycode");
      final int _cursorIndexOfSendingmantel = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingmantel");
      final int _cursorIndexOfSendingman = CursorUtil.getColumnIndexOrThrow(_cursor, "sendingman");
      final int _cursorIndexOfArrivalmantel = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalmantel");
      final int _cursorIndexOfArrivalmantel2 = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalmantel2");
      final int _cursorIndexOfArrivalman = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalman");
      final int _cursorIndexOfZipcode = CursorUtil.getColumnIndexOrThrow(_cursor, "zipcode");
      final int _cursorIndexOfAdress = CursorUtil.getColumnIndexOrThrow(_cursor, "adress");
      final int _cursorIndexOfPrefare = CursorUtil.getColumnIndexOrThrow(_cursor, "prefare");
      final int _cursorIndexOfFare = CursorUtil.getColumnIndexOrThrow(_cursor, "fare");
      final int _cursorIndexOfDeliveryfare = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryfare");
      final int _cursorIndexOfOgideliveryfare = CursorUtil.getColumnIndexOrThrow(_cursor, "ogideliveryfare");
      final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
      final int _cursorIndexOfPayway = CursorUtil.getColumnIndexOrThrow(_cursor, "payway");
      final int _cursorIndexOfGoods = CursorUtil.getColumnIndexOrThrow(_cursor, "goods");
      final int _cursorIndexOfPojang = CursorUtil.getColumnIndexOrThrow(_cursor, "pojang");
      final int _cursorIndexOfQty = CursorUtil.getColumnIndexOrThrow(_cursor, "qty");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
      final int _cursorIndexOfBillstate = CursorUtil.getColumnIndexOrThrow(_cursor, "billstate");
      final int _cursorIndexOfDeliverycourse = CursorUtil.getColumnIndexOrThrow(_cursor, "deliverycourse");
      final int _cursorIndexOfCreatdate = CursorUtil.getColumnIndexOrThrow(_cursor, "creatdate");
      final int _cursorIndexOfDeliveryState = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_state");
      final int _cursorIndexOfDeliveryPicturePath = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_picture_path");
      final DeliveryModelView _result;
      if(_cursor.moveToFirst()) {
        _result = new DeliveryModelView();
        final String _tmpBillno;
        if (_cursor.isNull(_cursorIndexOfBillno)) {
          _tmpBillno = null;
        } else {
          _tmpBillno = _cursor.getString(_cursorIndexOfBillno);
        }
        _result.setBillno(_tmpBillno);
        final String _tmpInput_date;
        if (_cursor.isNull(_cursorIndexOfInputDate)) {
          _tmpInput_date = null;
        } else {
          _tmpInput_date = _cursor.getString(_cursorIndexOfInputDate);
        }
        _result.setInput_date(_tmpInput_date);
        final String _tmpInput_time;
        if (_cursor.isNull(_cursorIndexOfInputTime)) {
          _tmpInput_time = null;
        } else {
          _tmpInput_time = _cursor.getString(_cursorIndexOfInputTime);
        }
        _result.setInput_time(_tmpInput_time);
        final String _tmpTranscode;
        if (_cursor.isNull(_cursorIndexOfTranscode)) {
          _tmpTranscode = null;
        } else {
          _tmpTranscode = _cursor.getString(_cursorIndexOfTranscode);
        }
        _result.setTranscode(_tmpTranscode);
        final String _tmpSendingagencycode;
        if (_cursor.isNull(_cursorIndexOfSendingagencycode)) {
          _tmpSendingagencycode = null;
        } else {
          _tmpSendingagencycode = _cursor.getString(_cursorIndexOfSendingagencycode);
        }
        _result.setSendingagencycode(_tmpSendingagencycode);
        final String _tmpArrivalagencycode;
        if (_cursor.isNull(_cursorIndexOfArrivalagencycode)) {
          _tmpArrivalagencycode = null;
        } else {
          _tmpArrivalagencycode = _cursor.getString(_cursorIndexOfArrivalagencycode);
        }
        _result.setArrivalagencycode(_tmpArrivalagencycode);
        final String _tmpSendingmantel;
        if (_cursor.isNull(_cursorIndexOfSendingmantel)) {
          _tmpSendingmantel = null;
        } else {
          _tmpSendingmantel = _cursor.getString(_cursorIndexOfSendingmantel);
        }
        _result.setSendingmantel(_tmpSendingmantel);
        final String _tmpSendingman;
        if (_cursor.isNull(_cursorIndexOfSendingman)) {
          _tmpSendingman = null;
        } else {
          _tmpSendingman = _cursor.getString(_cursorIndexOfSendingman);
        }
        _result.setSendingman(_tmpSendingman);
        final String _tmpArrivalmantel;
        if (_cursor.isNull(_cursorIndexOfArrivalmantel)) {
          _tmpArrivalmantel = null;
        } else {
          _tmpArrivalmantel = _cursor.getString(_cursorIndexOfArrivalmantel);
        }
        _result.setArrivalmantel(_tmpArrivalmantel);
        final String _tmpArrivalmantel2;
        if (_cursor.isNull(_cursorIndexOfArrivalmantel2)) {
          _tmpArrivalmantel2 = null;
        } else {
          _tmpArrivalmantel2 = _cursor.getString(_cursorIndexOfArrivalmantel2);
        }
        _result.setArrivalmantel2(_tmpArrivalmantel2);
        final String _tmpArrivalman;
        if (_cursor.isNull(_cursorIndexOfArrivalman)) {
          _tmpArrivalman = null;
        } else {
          _tmpArrivalman = _cursor.getString(_cursorIndexOfArrivalman);
        }
        _result.setArrivalman(_tmpArrivalman);
        final String _tmpZipcode;
        if (_cursor.isNull(_cursorIndexOfZipcode)) {
          _tmpZipcode = null;
        } else {
          _tmpZipcode = _cursor.getString(_cursorIndexOfZipcode);
        }
        _result.setZipcode(_tmpZipcode);
        final String _tmpAdress;
        if (_cursor.isNull(_cursorIndexOfAdress)) {
          _tmpAdress = null;
        } else {
          _tmpAdress = _cursor.getString(_cursorIndexOfAdress);
        }
        _result.setAdress(_tmpAdress);
        final String _tmpPrefare;
        if (_cursor.isNull(_cursorIndexOfPrefare)) {
          _tmpPrefare = null;
        } else {
          _tmpPrefare = _cursor.getString(_cursorIndexOfPrefare);
        }
        _result.setPrefare(_tmpPrefare);
        final String _tmpFare;
        if (_cursor.isNull(_cursorIndexOfFare)) {
          _tmpFare = null;
        } else {
          _tmpFare = _cursor.getString(_cursorIndexOfFare);
        }
        _result.setFare(_tmpFare);
        final String _tmpDeliveryfare;
        if (_cursor.isNull(_cursorIndexOfDeliveryfare)) {
          _tmpDeliveryfare = null;
        } else {
          _tmpDeliveryfare = _cursor.getString(_cursorIndexOfDeliveryfare);
        }
        _result.setDeliveryfare(_tmpDeliveryfare);
        final String _tmpOgideliveryfare;
        if (_cursor.isNull(_cursorIndexOfOgideliveryfare)) {
          _tmpOgideliveryfare = null;
        } else {
          _tmpOgideliveryfare = _cursor.getString(_cursorIndexOfOgideliveryfare);
        }
        _result.setOgideliveryfare(_tmpOgideliveryfare);
        final String _tmpDistance;
        if (_cursor.isNull(_cursorIndexOfDistance)) {
          _tmpDistance = null;
        } else {
          _tmpDistance = _cursor.getString(_cursorIndexOfDistance);
        }
        _result.setDistance(_tmpDistance);
        final String _tmpPayway;
        if (_cursor.isNull(_cursorIndexOfPayway)) {
          _tmpPayway = null;
        } else {
          _tmpPayway = _cursor.getString(_cursorIndexOfPayway);
        }
        _result.setPayway(_tmpPayway);
        final String _tmpGoods;
        if (_cursor.isNull(_cursorIndexOfGoods)) {
          _tmpGoods = null;
        } else {
          _tmpGoods = _cursor.getString(_cursorIndexOfGoods);
        }
        _result.setGoods(_tmpGoods);
        final String _tmpPojang;
        if (_cursor.isNull(_cursorIndexOfPojang)) {
          _tmpPojang = null;
        } else {
          _tmpPojang = _cursor.getString(_cursorIndexOfPojang);
        }
        _result.setPojang(_tmpPojang);
        final int _tmpQty;
        _tmpQty = _cursor.getInt(_cursorIndexOfQty);
        _result.setQty(_tmpQty);
        final String _tmpWeight;
        if (_cursor.isNull(_cursorIndexOfWeight)) {
          _tmpWeight = null;
        } else {
          _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
        }
        _result.setWeight(_tmpWeight);
        final String _tmpMemo;
        if (_cursor.isNull(_cursorIndexOfMemo)) {
          _tmpMemo = null;
        } else {
          _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
        }
        _result.setMemo(_tmpMemo);
        final String _tmpBillstate;
        if (_cursor.isNull(_cursorIndexOfBillstate)) {
          _tmpBillstate = null;
        } else {
          _tmpBillstate = _cursor.getString(_cursorIndexOfBillstate);
        }
        _result.setBillstate(_tmpBillstate);
        final String _tmpDeliverycourse;
        if (_cursor.isNull(_cursorIndexOfDeliverycourse)) {
          _tmpDeliverycourse = null;
        } else {
          _tmpDeliverycourse = _cursor.getString(_cursorIndexOfDeliverycourse);
        }
        _result.setDeliverycourse(_tmpDeliverycourse);
        final String _tmpCreatdate;
        if (_cursor.isNull(_cursorIndexOfCreatdate)) {
          _tmpCreatdate = null;
        } else {
          _tmpCreatdate = _cursor.getString(_cursorIndexOfCreatdate);
        }
        _result.setCreatdate(_tmpCreatdate);
        final String _tmpDelivery_state;
        if (_cursor.isNull(_cursorIndexOfDeliveryState)) {
          _tmpDelivery_state = null;
        } else {
          _tmpDelivery_state = _cursor.getString(_cursorIndexOfDeliveryState);
        }
        _result.setDelivery_state(_tmpDelivery_state);
        final String _tmpDelivery_picture_path;
        if (_cursor.isNull(_cursorIndexOfDeliveryPicturePath)) {
          _tmpDelivery_picture_path = null;
        } else {
          _tmpDelivery_picture_path = _cursor.getString(_cursorIndexOfDeliveryPicturePath);
        }
        _result.setDelivery_picture_path(_tmpDelivery_picture_path);
      } else {
        _result = null;
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
