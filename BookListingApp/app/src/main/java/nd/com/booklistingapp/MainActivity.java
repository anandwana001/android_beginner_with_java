package nd.com.booklistingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;

    private EditText searchWord;
    private Button searchBtn;
    private TextView noResult;

    private static final String URL_BOOK = "https://www.googleapis.com/books/v1/volumes?q=";
    private String URL_BOOK_WORD="";

    private static ArrayList<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        noResult.setText(R.string.search_sugs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        bookAdapter = new BookAdapter(bookList);
        recyclerView.setAdapter(bookAdapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchWord.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(),R.string.typemsg,Toast.LENGTH_LONG).show();
                }else{
                    URL_BOOK_WORD = URL_BOOK.concat(searchWord.getText().toString().replaceAll(" ","").trim());
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();
                    if(netInfo != null && netInfo.isConnectedOrConnecting()){
                        bookList.clear();
                        noResult.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        new BookAsyncTask().execute();
                    }else{
                        noResult.setText(R.string.noresnet);
                        noResult.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private class BookAsyncTask extends AsyncTask<String,Void,List<Book>>{
        private ProgressDialog progressDialog;
        @Override
        protected List<Book> doInBackground(String... strings) {
            List<Book> result = fetchBooksData(URL_BOOK_WORD);
            return result;
        }
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(List<Book> data) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            bookAdapter = new BookAdapter(data);
            recyclerView.setAdapter(bookAdapter);
        }
    }

    public static List<Book> fetchBooksData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makehttpRequest(url);
        }catch (IOException e){}
        List<Book> result = extractBooks(jsonResponse);
        return result;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch (MalformedURLException e){}
        return url;
    }

    private static String makehttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
            }
        }catch (IOException e){
            Log.e(TAG,e.toString());
        }finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static List<Book> extractBooks(String bookJSON){
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBooks = bookArray.getJSONObject(i);
                Book book = new Book();
                JSONObject info = currentBooks.getJSONObject("volumeInfo");
                String title = info.getString("title");
                book.setmBookName(title);
                JSONArray bookauthArray =null;
                try{
                    bookauthArray = info.getJSONArray("authors");
                }catch (JSONException ignored){}
                String authors="";
                if(bookauthArray == null){
                    authors = "Unknown";
                }else{
                    for(int j=0;j<bookauthArray.length();j++){
                        authors = bookauthArray.getString(j);
                        book.setMbookAuth(authors);
                    }
                }
                book.setMbookAuth(authors);
                bookList.add(book);
            }
        }catch (JSONException e){
            Log.e(TAG,"json retrieving");
        }
        return bookList;
    }
    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchWord = (EditText) findViewById(R.id.search_word);
        searchBtn = (Button) findViewById(R.id.search);
        noResult = (TextView) findViewById(R.id.noResultShow);
    }
}