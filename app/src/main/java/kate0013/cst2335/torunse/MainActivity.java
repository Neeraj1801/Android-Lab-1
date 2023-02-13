package kate0013.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

import kate0013.cst2335.torunse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static String Tag="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        SharedPreferences prefs=getSharedPreferences("MyData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        editor.putString("Email",binding.editText.getText().toString());
        editor.apply();
        binding.button.setOnClickListener(btn->{
            Intent intent=new Intent(MainActivity.this,SecondActivity.class);
            String typedEmail=binding.editText.getText().toString();
            String typedPassword=binding.editText2.getText().toString();
            intent.putExtra("Email",typedEmail);
            intent.putExtra("Password",typedPassword);
            String found=prefs.getString("Email","Missing Data");
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( Tag, "Now Visible" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( Tag, "Now listens for touch" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( Tag, "On Pause" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( Tag, "Now stopped" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}