package altitech.firedetector;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    TextView textView;
    String htmlResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        myURLTask task = new myURLTask();
        task.execute();

    }


    private class myURLTask extends AsyncTask<String, Void, String> {

        public String connect(String urlString){

            URL url;
            HttpURLConnection conn;
            InputStream is;
            BufferedReader reader;
            String data;

            String result="";

            try {
                url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                is = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));


                while ((data = reader.readLine()) != null){
                    result += data + "\n";
                }

                conn.disconnect();


            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

            htmlResponse = result;

            return result;
        }

        @Override
        protected String doInBackground(String... params) {




            String url = "http://requestb.in/1fvkyai1?inspect";

            connect(url);

            return null;
        }


        @Override
        protected void onPostExecute(String result) {


            int fireIndex = htmlResponse.indexOf("POST");
            int noneIndex = htmlResponse.indexOf("GET");

            if(fireIndex < noneIndex && fireIndex != -1){
                Log.i("ONPOSTEXECITE", "YES FOUND FIRE");
                textView.setText("FIRE");
            }else{
                Log.i("ONPOSTEXECITE", "NO FIRE");
                textView.setText("NO FIRE");
            }
        }
    }
}
