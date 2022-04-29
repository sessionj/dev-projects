package kr.co.delivery_v1.db.login;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.models.LoginModelView;

@Database(entities = {LoginModelView.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase database;
    private static String DATABASE_NAME = Label.DELIVERY_BASE_ROOM_PROFILE_DATABASE_NAME;

    public synchronized static AppDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract BasicProcessDao basicProcessDao();

}
