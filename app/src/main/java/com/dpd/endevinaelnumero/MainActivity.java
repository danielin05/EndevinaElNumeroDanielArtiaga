package com.dpd.endevinaelnumero;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private Button btnCheck;
    private TextView tvHistory, tvAttempts;
    private ScrollView scrollView;
    private int secretNumber;
    private int attemptsCount = 0;
    private StringBuilder history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referències als elements de la UI
        etNumber = findViewById(R.id.etNumber);
        btnCheck = findViewById(R.id.btnCheck);
        tvHistory = findViewById(R.id.tvHistory);
        tvAttempts = findViewById(R.id.tvAttempts);
        scrollView = findViewById(R.id.scrollView);

        // Inicialitzem l'historial i el nombre secret
        history = new StringBuilder();
        generateSecretNumber();

        // Listener del botó per verificar l'entrada
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etNumber.getText().toString();

                if (input.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Introdueix un número", Toast.LENGTH_SHORT).show();
                    return;
                }

                int userNumber = Integer.parseInt(input);
                attemptsCount++;

                // Comprovem si el nombre és major o menor
                if (userNumber > secretNumber) {
                    addToHistory("Intent " + attemptsCount + ": " + userNumber + " - Massa alt!");
                    Toast.makeText(MainActivity.this, "El número és massa alt!", Toast.LENGTH_SHORT).show();
                } else if (userNumber < secretNumber) {
                    addToHistory("Intent " + attemptsCount + ": " + userNumber + " - Massa baix!");
                    Toast.makeText(MainActivity.this, "El número és massa baix!", Toast.LENGTH_SHORT).show();
                } else {
                    addToHistory("Intent " + attemptsCount + ": " + userNumber + " - Felicitats, has guanyat!");
                    showGameOverDialog();
                }

                // Actualitzem la UI
                tvAttempts.setText("Intentos: " + attemptsCount);
                scrollView.fullScroll(View.FOCUS_DOWN); // Fer scroll fins al final
            }
        });
    }

    private void generateSecretNumber() {
        // Generem un nombre aleatori entre 1 i 100
        Random random = new Random();
        secretNumber = random.nextInt(100) + 1;
    }

    private void addToHistory(String text) {
        history.append(text).append("\n");
        tvHistory.setText(history.toString());
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fi del joc")
                .setMessage("Felicitats! Has endevinat el nombre secret en " + attemptsCount + " intents.")
                .setPositiveButton("Tornar a jugar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Reiniciar el joc
                        generateSecretNumber();
                        attemptsCount = 0;
                        history.setLength(0); // Esborrar l'historial
                        tvHistory.setText("");
                        tvAttempts.setText("Intentos: 0");
                    }
                })
                .setCancelable(false)
                .show();
    }
}
