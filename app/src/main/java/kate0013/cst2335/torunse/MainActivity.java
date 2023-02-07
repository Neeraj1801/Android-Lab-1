package kate0013.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kate0013.cst2335.torunse.data.MainActivityViewModel;
import kate0013.cst2335.torunse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
    MainActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(click->{
            viewModel.editTextContents.postValue(binding.editText.getText().toString());
        });
        viewModel.isSelected.observe(this, selected-> {
            binding.myCheckBox.setChecked(selected);
            binding.myRadioButton.setChecked(selected);
            binding.mySwitch.setChecked(selected);
            binding.textView.setText("The value is: "+binding.editText.getText().toString());
            Context context= getApplicationContext();
            CharSequence text="Your value is: "+binding.myCheckBox.isChecked();
            int duration=Toast.LENGTH_LONG;
            Toast.makeText(context,text,duration).show();
        });
        binding.myCheckBox.setOnCheckedChangeListener((myCheckBox,isChecked)->{
            viewModel.isSelected.postValue(isChecked);
        });
        binding.myRadioButton.setOnCheckedChangeListener((myRadioButton,isChecked)->{
            viewModel.isSelected.postValue(isChecked);
        });
        binding.mySwitch.setOnCheckedChangeListener((mySwitch,isChecked)->{
            viewModel.isSelected.postValue(isChecked);
        });
        binding.myImage.setOnClickListener(click->{
            Toast.makeText(MainActivity.this,"Width: "+binding.myImage.getWidth()+" Length: "+binding.myImage.getHeight(),Toast.LENGTH_LONG).show();
        });
    }
}