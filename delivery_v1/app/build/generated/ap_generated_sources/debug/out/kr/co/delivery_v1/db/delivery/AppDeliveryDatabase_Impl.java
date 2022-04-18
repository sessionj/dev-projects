package kr.co.delivery_v1.db.delivery;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDeliveryDatabase_Impl extends AppDeliveryDatabase {
  private volatile BasicDeliveryProcessDao _basicDeliveryProcessDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tb_delivery` (`delivery_no` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `billno` TEXT, `input_date` TEXT, `input_time` TEXT, `transcode` TEXT, `sendingagencycode` TEXT, `arrivalagencycode` TEXT, `sendingmantel` TEXT, `sendingman` TEXT, `arrivalmantel` TEXT, `arrivalman` TEXT, `zipcode` TEXT, `adress` TEXT, `prefare` TEXT, `fare` TEXT, `deliveryfare` TEXT, `ogideliveryfare` TEXT, `distance` TEXT, `payway` TEXT, `goods` TEXT, `pojang` TEXT, `qty` INTEGER NOT NULL, `weight` TEXT, `memo` TEXT, `billstate` TEXT, `deliverycourse` TEXT, `creatdate` TEXT, `delivery_state` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'e301308735908a7f4a9867180a055c9d')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `tb_delivery`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTbDelivery = new HashMap<String, TableInfo.Column>(28);
        _columnsTbDelivery.put("delivery_no", new TableInfo.Column("delivery_no", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("billno", new TableInfo.Column("billno", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("input_date", new TableInfo.Column("input_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("input_time", new TableInfo.Column("input_time", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("transcode", new TableInfo.Column("transcode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("sendingagencycode", new TableInfo.Column("sendingagencycode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("arrivalagencycode", new TableInfo.Column("arrivalagencycode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("sendingmantel", new TableInfo.Column("sendingmantel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("sendingman", new TableInfo.Column("sendingman", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("arrivalmantel", new TableInfo.Column("arrivalmantel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("arrivalman", new TableInfo.Column("arrivalman", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("zipcode", new TableInfo.Column("zipcode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("adress", new TableInfo.Column("adress", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("prefare", new TableInfo.Column("prefare", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("fare", new TableInfo.Column("fare", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("deliveryfare", new TableInfo.Column("deliveryfare", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("ogideliveryfare", new TableInfo.Column("ogideliveryfare", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("distance", new TableInfo.Column("distance", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("payway", new TableInfo.Column("payway", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("goods", new TableInfo.Column("goods", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("pojang", new TableInfo.Column("pojang", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("qty", new TableInfo.Column("qty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("weight", new TableInfo.Column("weight", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("memo", new TableInfo.Column("memo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("billstate", new TableInfo.Column("billstate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("deliverycourse", new TableInfo.Column("deliverycourse", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("creatdate", new TableInfo.Column("creatdate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbDelivery.put("delivery_state", new TableInfo.Column("delivery_state", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTbDelivery = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTbDelivery = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTbDelivery = new TableInfo("tb_delivery", _columnsTbDelivery, _foreignKeysTbDelivery, _indicesTbDelivery);
        final TableInfo _existingTbDelivery = TableInfo.read(_db, "tb_delivery");
        if (! _infoTbDelivery.equals(_existingTbDelivery)) {
          return new RoomOpenHelper.ValidationResult(false, "tb_delivery(kr.co.delivery_v1.models.DeliveryModelView).\n"
                  + " Expected:\n" + _infoTbDelivery + "\n"
                  + " Found:\n" + _existingTbDelivery);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "e301308735908a7f4a9867180a055c9d", "8dd79634d83f097b9c6b4ab17afb77d4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tb_delivery");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tb_delivery`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BasicDeliveryProcessDao.class, BasicDeliveryProcessDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public BasicDeliveryProcessDao basicDeliveryProcessDao() {
    if (_basicDeliveryProcessDao != null) {
      return _basicDeliveryProcessDao;
    } else {
      synchronized(this) {
        if(_basicDeliveryProcessDao == null) {
          _basicDeliveryProcessDao = new BasicDeliveryProcessDao_Impl(this);
        }
        return _basicDeliveryProcessDao;
      }
    }
  }
}
