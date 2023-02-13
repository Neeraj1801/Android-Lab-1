package kate0013.cst2335.torunse;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kate0013.cst2335.torunse.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySecondBinding binding= ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        File file = new File( getFilesDir(), "Picture.png");
        if(file.exists())
        {
            Bitmap theImage= BitmapFactory.decodeFile(getFilesDir().getPath()+"/Picture.png");
            binding.imageView.setImageBitmap(theImage);
        }
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override

                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail=data.getParcelableExtra("data");
                            FileOutputStream fOut = null;


                            try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                            }
                            catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                });
        SharedPreferences prefs=getSharedPreferences("MyPhone",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        editor.apply();
        Intent fromPrevious=getIntent();
        String emailAddress=fromPrevious.getStringExtra("Email");
        binding.textView3.setText("Welcome back "+ emailAddress);
        binding.secondPageButton.setOnClickListener( click-> {
            String phoneNumber=binding.editTextPhone.getText().toString();
            editor.putString("PhoneNumber",binding.editTextPhone.getText().toString());
            editor.apply();
            Intent dial=new Intent(Intent.ACTION_DIAL);
            dial.setData(Uri.parse("tel:"+phoneNumber));
            startActivity(dial);
        });
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        binding.button3.setOnClickListener(click->{
            cameraResult.launch(cameraIntent);
        });
    }
}