package jp.ac.meijou.android.s241205180;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s241205180.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //binding.text.setText(R.string.text2);
        //TextView text = findViewById(R.id.text);
        //text.setText(R.string.text2);

        //binding.imageView2.setImageResource(R.drawable.ic_android_1);

        binding.button.setOnClickListener(view -> {;
            binding.text.setText(R.string.text2);
            binding.imageView2.setImageResource(R.drawable.ic_android_1);

            //binding.text.setTextSize(30);
        });
//
//        binding.button2.setOnClickListener(view -> {;
//            var text = binding.editTextText.getText().toString();
//            binding.text.setText(text);
//        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // どちらかのEditTextが変更されるたびに、両方の内容を取得して連結し、TextViewにセットする
                String text1 = binding.editTextText.getText().toString();
                String text2 = binding.editTextText2.getText().toString();
                binding.text.setText(text1 + text2);
            }
        };
        binding.editTextText.addTextChangedListener(textWatcher);
        binding.editTextText2.addTextChangedListener(textWatcher);

    }


}