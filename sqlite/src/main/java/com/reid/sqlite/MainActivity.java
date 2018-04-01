package com.reid.sqlite;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.reid.sqlite.db.BaseDaoFactory;
import com.reid.sqlite.db.Box;
import com.reid.sqlite.db.IBaseDao;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    IBaseDao<User> baseDao;
    private  PermissionManager mPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPermissionManager = new PermissionManager(this);

        if (!mPermissionManager.checkCameraLaunchPermissions()) {
            if (!mPermissionManager.requestCameraAllPermissions()) {
                return;
            }
        }

        baseDao = BaseDaoFactory.getInstance().getDataHelper(UserDao.class, User.class);
    }

    public void save(View view) {
        User user = new User("Reid", "123456");

        baseDao.insert(user);
        //Box<Integer> box = new Box<Integer>();
        //box.setmObject(new Integer(2));
        //box.getmObject();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult(), grantResults = " + grantResults.length);
        if (permissions.length <= 0) {
            return;
        }
        if (mPermissionManager.getCameraLaunchPermissionRequestCode()
                == requestCode) {
            // If permission all on, activity will resume.
            if (!mPermissionManager.isCameraLaunchPermissionsResultReady(
                    permissions, grantResults)) {
                // more than one critical permission was denied
                // activity finish, exit and destroy

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.denied_required_permission), Toast.LENGTH_SHORT)
                        .show();

                finish();
            }
        }
    }
}
