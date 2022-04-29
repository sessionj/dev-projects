package kr.co.delivery_v1.db.delivery;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.models.DeliveryRequestModelView;

@Database(entities = {DeliveryRequestModelView.class}, version = 1, exportSchema = false)
public abstract class BasicDeliveryRequestDatabase extends RoomDatabase{

    private static BasicDeliveryRequestDatabase database;
    private static String DATABASE_NAME = Label.DELIVERY_BASE_ROOM_DELIVERY_REQ_DATABASE_NAME;

    public synchronized static BasicDeliveryRequestDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), BasicDeliveryRequestDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract BasicDeliveryRequestProcessDao basicDeliveryRequestProcessDao();

}
