package kate0013.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
        binding.editText.setText(viewModel.editTextContents);
        binding.textView.setText(viewModel.editTextContents);
        binding.button.setText(viewModel.editTextContents);
        binding.mySwitch.setChecked(viewModel.isSelected);
        binding.myCheckBox.setChecked(viewModel.isSelected);
        binding.myRadioButton.setChecked(true);
        binding.myCheckBox.setOnCheckedChangeListener((whichButton,isChecked) -> {
            viewModel.isSelected=isChecked;
            Toast.makeText(MainActivity.this,"Checkbox check?:"+isChecked,Toast.LENGTH_LONG).show();
        });
        binding.mySwitch.setOnCheckedChangeListener((whichButton,isChecked) -> {
            viewModel.isSelected=isChecked;
            Toast.makeText(MainActivity.this,"Checkbox check?:"+isChecked,Toast.LENGTH_LONG).show();
        });
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(view -> {
            viewModel.editTextContents="You Clicked This Button!!!";
            binding.editText.setText(viewModel.editTextContents);
            binding.textView.setText(viewModel.editTextContents);
            binding.button.setText(viewModel.editTextContents);

        });
        binding.myImage.setOnClickListener(click->{
            Toast.makeText(MainActivity.this,"Width: "+binding.myImage.getWidth()+" Length: "+binding.myImage.getHeight(),Toast.LENGTH_LONG).show();
        });
    }
}