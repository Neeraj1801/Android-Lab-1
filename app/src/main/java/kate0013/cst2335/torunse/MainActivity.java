package kate0013.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kate0013.cst2335.torunse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private String cityName;
    private RequestQueue requestQueue = null;
    private ImageRequest imageRequest;
    private Bitmap bitmap;
    private String URL;
    private String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        requestQueue = Volley.newRequestQueue(this);
        binding.forecastButton.setOnClickListener(clk -> {
            cityName = binding.cityNameField.getText().toString();
            try {
                URL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                    (res) -> {
                        try {
                            JSONArray weatherArr = res.getJSONArray("weather");
                            JSONObject weatherObj = weatherArr.getJSONObject(0);
                            String description = weatherObj.getString("description");
                            String iconCode = res.getJSONArray("weather")
                                    .getJSONObject(0)
                                    .getString("icon");
                            JSONObject mainObj = res.getJSONObject("main");
                            double temp = mainObj.getDouble("temp");
                            double min = mainObj.getDouble("temp_min");
                            double max = mainObj.getDouble("temp_max");
                            int humidity = mainObj.getInt("humidity");
                            imageURL = "https://openweathermap.org/img/w/" + iconCode + ".png";
                            String pathName = getFilesDir() + "/" + iconCode + ".png";
                            File file = new File(pathName);
                            System.err.println("File path for the bitmap is: " + pathName);
                            if (file.exists()) {
                                bitmap = BitmapFactory.decodeFile(pathName);
                            } else {
                                imageRequest = new ImageRequest(imageURL, new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap response) {
                                        try {
                                            bitmap = response;
                                            bitmap.compress(Bitmap.CompressFormat.PNG,
                                                    100, MainActivity.this.openFileOutput(iconCode + ".png", Activity.MODE_APPEND));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                        err -> {

                                        });
                                requestQueue.add(imageRequest);
                            }

                            runOnUiThread(() -> {
                                binding.temperatureTextView.setText("Current Temperature is: " + temp);
                                binding.temperatureTextView.setVisibility(View.VISIBLE);

                                binding.minimumTemperatureTextView.setText("The min temperature is: " + min);
                                binding.minimumTemperatureTextView.setVisibility(View.VISIBLE);

                                binding.maximumTemperatureTextView.setText("The max temperature is: " + max);
                                binding.maximumTemperatureTextView.setVisibility(View.VISIBLE);

                                binding.humidityTextView.setText("The humidity is: " + humidity);
                                binding.humidityTextView.setVisibility(View.VISIBLE);

                                binding.imageView.setImageBitmap(bitmap);
                                binding.imageView.setVisibility(View.VISIBLE);

                                binding.descriptionTextView.setText(description);
                                binding.descriptionTextView.setVisibility(View.VISIBLE);
                            });
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    },
                    (resErr) -> {

                    });
            requestQueue.add(jsonObjectRequest);
        });
    }
}