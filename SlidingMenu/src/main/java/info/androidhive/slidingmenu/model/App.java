package info.androidhive.slidingmenu.model;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;
import com.parse.ParseInstallation;

import info.androidhive.slidingmenu.util.LruBitmapCache;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(getApplicationContext(), "emZ0b7JfFnd2Dt1jHvGkqtcS9XBcnqfs5UAszsE3", "lxbVihNI9Z6Ded2Odm3f2lMTeC57u5mwvNzz1GnY");
        ParseInstallation.getCurrentInstallation().saveInBackground();


        mInstance = this;
    }

    public static final String TAG = App.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static App mInstance;



    public static synchronized App getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }






}
