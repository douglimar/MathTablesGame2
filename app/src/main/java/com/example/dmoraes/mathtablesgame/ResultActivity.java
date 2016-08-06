package com.example.dmoraes.mathtablesgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    private String url = "";
    private String s_tag = "";
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        Intent intent = getIntent();

        TextView tvResultAcertos =  (TextView)findViewById(R.id.textView9);
        TextView tvResultErros =  (TextView)findViewById(R.id.textView10);
        TextView tvResultPercAcerto =  (TextView)findViewById(R.id.textView11);
        TextView tvResult = (TextView) findViewById(R.id.tvResult2);
        float fPercentual = 0;

        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        String[] sResultados = message.split(";");


        tvResultAcertos.setText(sResultados[0]);
        tvResultErros.setText(sResultados[1]);
        tvResultPercAcerto.setText(sResultados[2] + "%");
        fPercentual = Float.parseFloat(sResultados[2]);

        Toast.makeText(ResultActivity.this, sResultados[0] + ";" + sResultados[1] + ";" + sResultados[2], Toast.LENGTH_SHORT).show();

        if( fPercentual >= 80)
            tvResult.setText("Parabéns. Você esta no caminho certo.");
        else
            if( fPercentual >= 50)
                tvResult.setText("Você esta no caminho certo, mas pode melhorar.");
        else
            if( fPercentual < 50)
                tvResult.setText("Vamos estudar um pouco mais.");

        //LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);

        /*AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
    }
}
