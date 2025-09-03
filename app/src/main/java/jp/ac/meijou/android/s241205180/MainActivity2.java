package jp.ac.meijou.android.s241205180;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s241205180.databinding.ActivityMain2Binding;
import jp.ac.meijou.android.s241205180.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Activity3へ遷移するインテント
        binding.buttonA.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
        });

        // ブラウザを起動するインテント
        binding.buttonB.setOnClickListener(view -> {
            var intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse("https://www.yahoo.co.jp"));
            startActivity(intent);
        });

        binding.buttonSend.setOnClickListener( view -> {
            var message = binding.editTextText.getText().toString();
            var intent = new Intent(this, MainActivity4.class);
            intent.putExtra("message", message);
            startActivity(intent);
        });

        binding.buttonLaunch.setOnClickListener( view -> {
            var intent = new Intent(this, MainActivity4.class);
            getActivityResult.launch(intent);
        });
    }

    private ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case RESULT_OK:
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> "Result: " + text)
                                .ifPresent(text -> binding.resultTextView.setText(text));
                        break;
                    case RESULT_CANCELED:
                        binding.resultTextView.setText("Result: canceled");
                        break;

                    default:
                        binding.resultTextView.setText("Result: unknown(" + result.getResultCode() + ")");
                        break;
                }
            }

    );
}