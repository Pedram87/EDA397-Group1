package com.group1.eda_397_group1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jimmy on 16-04-28.
 */
public class DatabaseHandler extends AsyncTask<Void, Void, JSONObject> {
    public AsyncResponse delegate = null;
    private JSONObject sendJSON;

    DatabaseHandler(JSONObject json) {
        sendJSON = json;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject jsonReturnObject = null;
        String resultString;
        String line;

        try {
            URL url = new URL("http://userapan.myds.me/pair_server/backend/index.php");
            //String query = parser.getLoginJSON(mEmail, mPassword).toString();
            String query = sendJSON.toString();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();
            outputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            resultString = stringBuilder.toString();

            try {
                jsonReturnObject = new JSONObject(resultString);
                //Log.d("DatabaseHandler", resultString);
            } catch (Throwable t) {
                Log.e("DatabaseHandler", "Could not parse malformed JSON: \"" + resultString + "\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonReturnObject;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (result != null) {
            //Log.d("onPostExecute", result.toString());
        } else {
            Log.d("onPostExecute", "result is null");
        }
        try {
            delegate.processFinish(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
