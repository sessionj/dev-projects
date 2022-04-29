package kr.co.delivery_v1.db.delivery;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.models.DeliveryModelView;

@Database(entities = {DeliveryModelView.class}, version = 1, exportSchema = false)
@TypeConverters(kr.co.delivery_v1.comm.Converters.class)
public abstract class BasicDeliveryDatabase extends RoomDatabase{

    private static BasicDeliveryDatabase database;
    private static String DATABASE_NAME = Label.DELIVERY_BASE_ROOM_DELIVERY_DATABASE_NAME;

    public synchronized static BasicDeliveryDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), BasicDeliveryDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract BasicDeliveryProcessDao basicDeliveryProcessDao();

}
