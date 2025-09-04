package jp.ac.meijou.android.s241205180;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Optional;

import jp.ac.meijou.android.s241205180.databinding.ActivityMain5Binding;
import jp.ac.meijou.android.s241205180.databinding.ActivityMainBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity5 extends AppCompatActivity {

    // http通信を行うためのOkHttpClientのインスタンスを生成
    private final OkHttpClient okHttpClient = new OkHttpClient();
    // jsonをjavaオブジェクトに変換するためのMoshiのインスタンスを生成
    private final Moshi moshi = new Moshi.Builder().build();
    // Gistクラスのjsonアダプタを生成
    private final JsonAdapter<Gist> gistJsonAdapter = moshi.adapter(Gist.class);

    private ActivityMain5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // アクセス先を指定
        var request = new Request.Builder()
                //.url("https://api.github.com/gists/c2a7c39532239ff261be")
                .url("https://mura.github.io/meijou-android-sample/gist.json")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
                // レスポンスのJSONをGistクラスのインスタンスに変換
                var gist = gistJsonAdapter.fromJson(response.body().source());
                // OKHttp.txtの内容をTextViewに設定
                Optional.ofNullable(gist)
                        .map(g -> g.files.get("OkHttp.txt"))
                        .ifPresent(gistFile -> {
                            runOnUiThread(() -> binding.textView.setText(gistFile.content));
                        });
            }
        });
    }
}