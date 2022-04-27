package kr.co.delivery_v1.db.delivery;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.db.login.BasicProcessDao;
import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.LoginModelView;

@Database(entities = {DeliveryModelView.class}, version = 1, exportSchema = false)
@TypeConverters(kr.co.delivery_v1.comm.Converters.class)
public abstract class AppDeliveryDatabase extends RoomDatabase{

    private static AppDeliveryDatabase database;
    private static String DATABASE_NAME = Label.DELIVERY_BASE_ROOM_DELIVERY_DATABASE_NAME;

    public synchronized static AppDeliveryDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), AppDeliveryDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract BasicDeliveryProcessDao basicDeliveryProcessDao();

}
