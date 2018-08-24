package com.luci.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.luci.LuciApp;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import com.luci.R;
import com.luci.ui.activity.MainActivity;
import com.luci.ui.activity.SplashActivity;

import es.dmoral.toasty.Toasty;

public class LUCIHttpClient {
    private static final String BASE_URL = Constant.getAPIBaseUrl();
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static Context mContext = LuciApp.mContext;

    public static void get(Context _context, String url, RequestParams params, SimpleHttpResponseHandler responseHandler) {
        final Context context = _context;

        if (Constant.DEBUG) {
            client.setTimeout(10000000);
        }

        final WaitDialog spinner = new WaitDialog(context);
        spinner.show();

        final SimpleHttpResponseHandler handler = responseHandler;
        client.get(getAbsoluteUrl(url), params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {
                // TODO Auto-generated method stub
                spinner.hide();

                if (handler!=null) {
                    handler.onSuccess(response);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                spinner.hide();

                if (catchFailure(context, response))
                    return;

                if (handler!=null) {
                    handler.onSuccess(response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                // TODO Auto-generated method stub
                spinner.hide();

                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONArray errorResponse) {
                // TODO Auto-generated method stub
                spinner.hide();

                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                spinner.hide();

                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }
        });
    }

    public static void get(Context _context, String url, RequestParams params, HashMap<String, String> headers, SimpleHttpResponseHandler responseHandler) {
        final Context context = _context;

        if (Constant.DEBUG) {
            client.setTimeout(10000000);
        }

        Set<String> keys = headers.keySet();
        client.removeAllHeaders();
        for (String key : keys) {
            client.addHeader(key, headers.get(key));
        }
        final SimpleHttpResponseHandler handler = responseHandler;
        client.get(getAbsoluteUrl(url), params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {
                // TODO Auto-generated method stub
                if (handler!=null) {
                    handler.onSuccess(response);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                if (handler!=null) {
                    handler.onSuccess(response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                // TODO Auto-generated method stub
                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONArray errorResponse) {
                // TODO Auto-generated method stub
                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }
        });
    }

    public static void post(Context _context, String url, RequestParams params, SimpleHttpResponseHandler responseHandler) {
        final Context context = _context;

        if (Constant.DEBUG) {
            client.setTimeout(10000000);
        }

        final WaitDialog spinner = new WaitDialog(context);
        spinner.show();

        final SimpleHttpResponseHandler handler = responseHandler;
        client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {
                // TODO Auto-generated method stub
                spinner.hide();

                if (handler!=null) {
                    handler.onSuccess(response);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                spinner.hide();

                if (catchFailure(context, response))
                    return;

                if (handler!=null) {
                    handler.onSuccess(response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                // TODO Auto-generated method stub
                spinner.hide();

                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONArray errorResponse) {
                // TODO Auto-generated method stub
                spinner.hide();

                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                spinner.hide();

                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }
        });
    }

    public static void post(Context _context, String url, RequestParams params, HashMap<String, String> headers, SimpleHttpResponseHandler responseHandler) {
        final Context context = _context;
        
        if (Constant.DEBUG) {
            client.setTimeout(10000000);
        }

        Set<String> keys = headers.keySet();
        client.removeAllHeaders();
        for (String key : keys) {
            client.addHeader(key, headers.get(key));
        }
//        client.post("", new TextHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int arg0, Header[] arg1, String arg2) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
//                // TODO Auto-generated method stub
//
//            }
//        });


        final SimpleHttpResponseHandler handler = responseHandler;
        client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {
                // TODO Auto-generated method stub
                if (handler!=null) {
                    handler.onSuccess(response);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                if (handler!=null) {
                    handler.onSuccess(response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                // TODO Auto-generated method stub
                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONArray errorResponse) {
                // TODO Auto-generated method stub
                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                catchError(context, statusCode);

                if (handler!=null) {
                    handler.onFailure(statusCode, throwable);
                }
            }
        });
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        if (Constant.DEBUG) {
            return BASE_URL + relativeUrl;// + "?XDEBUG_SESSION_START=netbeans-xdebug";
        } else {
            return BASE_URL + relativeUrl;
        }
    }

    private static void catchError(Context context, int statusCode) {
        Log.e("LUCI", "Fail status_code : " + statusCode);
        switch (statusCode) {
            case 404: case 0: {
                Toasty.error(context, mContext.getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
            break;
            case 500: {
                Toasty.error(context, mContext.getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
            break;
            default: {
                Toasty.error(context, mContext.getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private static boolean catchFailure(Context context, JSONObject response) {
        if (response != null) {
            try {
                Boolean status = response.getBoolean("success");
                if (!status) {
                    // TODO: Catch reason_code, and alert appropriate toast.
                    int reason_code = response.getInt("reason");
                    switch (reason_code) {
                        case 101:
                            Toasty.normal(context, mContext.getString(R.string.aj_invalid_email), Toast.LENGTH_SHORT).show();
                            return true;
                        case 102:
                            Toasty.normal(context, mContext.getString(R.string.aj_invalid_password), Toast.LENGTH_SHORT).show();
                            return true;
                        case 103:
                            Toasty.normal(context, mContext.getString(R.string.aj_invalid_activationcode), Toast.LENGTH_SHORT).show();
                            return true;
                        case 104:
                            Toasty.normal(context, mContext.getString(R.string.aj_need_signin), Toast.LENGTH_SHORT).show();
                            return true;
                        case 105:
                            Toasty.normal(context, mContext.getString(R.string.aj_invalid_channelid), Toast.LENGTH_SHORT).show();
                            return true;
                    }
                }
            } catch (Exception e) {
//                Toasty.error(mContext, mContext.getString(R.string.server_error), Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
            }
        } else {
            Toasty.error(mContext, mContext.getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    public interface SimpleHttpResponseHandler {
        public void onSuccess(JSONObject object);
        public void onSuccess(JSONArray array);
        public void onFailure(int statusCode, Throwable throwable);
    }

}
