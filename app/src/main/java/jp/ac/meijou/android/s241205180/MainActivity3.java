package jp.ac.meijou.android.s241205180;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.ac.meijou.android.s241205180.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    private ActivityMain3Binding binding;
    // 値を保持する変数
    private BigDecimal operand1 = null;
    // 演算子を保持する変数
    private String operator = "";
    // 演算子がクリックされたかどうかを示すフラグ
    private boolean isOperatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupButtonListeners();
    }
    // ボタンのリスナーを設定
    private void setupButtonListeners() {
        // 数字ボタン
        View.OnClickListener numberListener = view -> {
            Button button = (Button) view;
            onNumberClick(button.getText().toString());
        };

        binding.button0.setOnClickListener(numberListener);
        binding.button1.setOnClickListener(numberListener);
        binding.button2.setOnClickListener(numberListener);
        binding.button3.setOnClickListener(numberListener);
        binding.button4.setOnClickListener(numberListener);
        binding.button5.setOnClickListener(numberListener);
        binding.button6.setOnClickListener(numberListener);
        binding.button7.setOnClickListener(numberListener);
        binding.button8.setOnClickListener(numberListener);
        binding.button9.setOnClickListener(numberListener);

        // 演算子ボタン
        View.OnClickListener operatorListener = view -> {
            Button button = (Button) view;
            onOperatorClick(button.getText().toString());
        };

        binding.buttonAdd.setOnClickListener(operatorListener);
        binding.buttonSub.setOnClickListener(operatorListener);
        binding.buttonMul.setOnClickListener(operatorListener);
        binding.buttonDiv.setOnClickListener(operatorListener);

        // ACボタン
        binding.buttonAC.setOnClickListener(view -> onClearClick());

        // =ボタン
        binding.buttonEqual.setOnClickListener(view -> onEqualClick());
    }


    // 数字ボタンがクリックされたときの処理
    private void onNumberClick(String number) {
        String currentText = binding.resultTextView.getText().toString();

        // 現在0か前に演算子が押された場合は新しい数字に置き換え
        if (currentText.equals("0") || isOperatorClicked) {
            binding.resultTextView.setText(number);
            isOperatorClicked = false;
        } else {
            // それ以外の場合は末尾に追記
            binding.resultTextView.setText(currentText + number);
        }
    }


    // 演算子ボタンがクリックされたときの処理
    private void onOperatorClick(String op) {
        // 最初の数値を取得して保存
        operand1 = new BigDecimal(binding.resultTextView.getText().toString());
        // 演算子を保存
        operator = op;
        // 演算子が押されたことを記録
        isOperatorClicked = true;
    }

    // ACボタンがクリックされたときの処理
    private void onClearClick() {
        // 状態をリセット
        operand1 = null;
        operator = "";
        isOperatorClicked = false;
        binding.resultTextView.setText("0");
    }


    // =ボタンがクリックされたときの処理
    private void onEqualClick() {
        // 演算子または最初の数値がなければ何もしない
        if (operator.isEmpty() || operand1 == null) {
            return;
        }

        // 2番目の数値を取得
        BigDecimal operand2 = new BigDecimal(binding.resultTextView.getText().toString());
        BigDecimal result = BigDecimal.ZERO;

        // 演算子ごとに演算を実行
        switch (operator) {
            case "+":
                result = operand1.add(operand2);
                break;
            case "-":
                result = operand1.subtract(operand2);
                break;
            case "×":
                result = operand1.multiply(operand2);
                break;
            case "÷":
                // 0除算のチェック
                if (operand2.compareTo(BigDecimal.ZERO) == 0) {
                    binding.resultTextView.setText("Error");
                    // 状態をリセット
                    operand1 = null;
                    operator = "";
                    isOperatorClicked = true;
                    return;
                }
                // 小数第10位を四捨五入
                result = operand1.divide(operand2, 9, RoundingMode.HALF_UP);
                break;
        }

        // 末尾の0を削除して表示
        binding.resultTextView.setText(result.stripTrailingZeros().toPlainString());

        // 計算後の状態をリセット
        operand1 = null;
        operator = "";
        isOperatorClicked = true;
    }
}