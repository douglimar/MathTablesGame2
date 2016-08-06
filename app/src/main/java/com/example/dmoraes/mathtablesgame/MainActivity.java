package com.example.dmoraes.mathtablesgame;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = new String ("com.example.dmoraes.mathtablesgame.MESSAGE");

    MediaPlayer errorSound;
    MediaPlayer okSound;

    boolean bChamaToast = false;
    boolean bRightAnswer;
    Thread thread;
    int iCont = 0;
    int iCountAcertos = 0;
    int iCountErros = 0;
    float fPercAcertos = 0;

    float iFirstNumber;
    float iSecondNumber;
    float Result = 0;
    int iSignal;
    String sSignal;

    static int interval;
    static Timer timer;

    Character[] aSignal = {'+', '-', '/', 'x'};
    //Character[] aSignal = {'x', 'x', 'x', 'x'};
    TextView tvNumber1;
    TextView tvSignal;
    TextView tvNumber2;
    TextView tvResult;
    TextView tvCountDown;
    TextView tvProgress;
    TextView tvTotalAcertos;
    TextView tvTotalErros;
    TextView tvPercentualAcertos;

    EditText edtResult;
    Boolean bZeroDivision = false;
    Button button;
    Random random = new Random();

    int iTvProgress = 0;
    // Add a progressBar
    ProgressBar mProgress;

    private Handler mHandler = new Handler();
    private int iProgressStatus = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorSound = MediaPlayer.create(MainActivity.this, R.raw.error);
        okSound = MediaPlayer.create(MainActivity.this,R.raw.ok);

        iCont = 0;
        iCountAcertos = 0;
        iCountErros = 0;
        fPercAcertos = 0;
        Result = 0;
        iTvProgress = 0;
        iProgressStatus = 0;

        tvNumber1 = (TextView) findViewById(R.id.tvNumber1);
        tvSignal = (TextView) findViewById(R.id.tvSignal);
        tvNumber2 = (TextView) findViewById(R.id.tvNumber2);
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvCountDown = (TextView) findViewById(R.id.tvCountDown);

        tvTotalAcertos = (TextView) findViewById(R.id.tvTotalAcertos);
        tvTotalErros = (TextView) findViewById(R.id.tvTotalErros);
        tvPercentualAcertos = (TextView)findViewById(R.id.tvPercentualAcertos);

        tvTotalAcertos.setText("");
        tvTotalErros.setText("");
        tvPercentualAcertos.setText("");

        //tvInstructions = (TextView) findViewById(R.id.tvInstructions);
        tvProgress = (TextView) findViewById(R.id.tvProgressBar);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        edtResult = (EditText) findViewById(R.id.editText);

        mProgress.setVisibility(View.VISIBLE);
        mProgress.setMax(61);
        // Altera o tamanho da barra do ProgressBar
        mProgress.setScaleY(5.0f);

        tvProgress.setText("61");

        clearFields(true);

        startCountDown1();

        button = (Button) findViewById(R.id.btnResult);

        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtResult.getText().toString().trim().equals(""))
                    Toast.makeText(getBaseContext(), "DIGITE UM VALOR ANTES DE CONTINUAR.", Toast.LENGTH_SHORT).show();
                else {

                    int iValorDigitado = Integer.parseInt(edtResult.getText().toString());
                    //int iValorCalculado = Integer.parseInt(tvResult.getText().toString());
                    int iValorCalculado = (int)Result;

                    if (validaResultado(iValorCalculado, iValorDigitado)) {
                        //Toast.makeText(getBaseContext(), "Resposta Correta", Toast.LENGTH_SHORT).show();
                        iCountAcertos++;
                        okSound.start();
                        bRightAnswer = true;
                    } else {
                        //Toast.makeText(getBaseContext(), "Resposta INCORRETA... BURRO!", Toast.LENGTH_SHORT).show();
                        iCountErros++;
                        errorSound.start();
                        bRightAnswer = false;
                    }

                    fPercAcertos = iCountAcertos*100/(iCountAcertos+iCountErros);

                    tvTotalAcertos.setText(String.valueOf(iCountAcertos));
                    tvTotalErros.setText(String.valueOf(iCountErros));
                    tvPercentualAcertos.setText(String.valueOf(fPercAcertos) + " %");

                    clearFields(true);
                    generateCalc();
                    gameCountDown();
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

  @Override
  protected void onResume(){
      super.onResume();

      errorSound = MediaPlayer.create(MainActivity.this, R.raw.error);
      okSound = MediaPlayer.create(MainActivity.this,R.raw.ok);

      iCont = 0;
      iCountAcertos = 0;
      iCountErros = 0;
      fPercAcertos = 0;
      Result = 0;
      iTvProgress = 0;
      iProgressStatus = 0;

      /*tvNumber1 = (TextView) findViewById(R.id.tvNumber1);
      tvSignal = (TextView) findViewById(R.id.tvSignal);
      tvNumber2 = (TextView) findViewById(R.id.tvNumber2);
      tvResult = (TextView) findViewById(R.id.tvResult);
      textView5 = (TextView) findViewById(R.id.textView5);

      tvTotalAcertos = (TextView) findViewById(R.id.tvTotalAcertos);
      tvTotalErros = (TextView) findViewById(R.id.tvTotalErros);
      tvPercentualAcertos = (TextView)findViewById(R.id.tvPercentualAcertos);

      //tvInstructions = (TextView) findViewById(R.id.tvInstructions);
      //tvProgress = (TextView) findViewById(R.id.tvProgressBar);

      //mProgress = (ProgressBar) findViewById(R.id.progressBar);

      //edtResult = (EditText) findViewById(R.id.editText);*/
      //edtResult.setText("Clique aqui para iniciar");
      tvTotalAcertos.setText("");
      tvTotalErros.setText("");
      tvPercentualAcertos.setText("");

      mProgress.setVisibility(View.VISIBLE);
      mProgress.setMax(61);
      // Altera o tamanho da barra do ProgressBar
      mProgress.setScaleY(5.0f);

      tvProgress.setText("61");

      clearFields(true);

      tvCountDown.setVisibility(View.VISIBLE);

      button.setEnabled(true);

      startCountDown1();


   }


    private void generateCalc() {

        tvNumber1.setText("");
        tvSignal.setText("");
        tvNumber2.setText("");
        tvResult.setText("");

        iFirstNumber = random.nextInt(50);
        iSecondNumber = random.nextInt(10);
        iSignal = random.nextInt(4);
        sSignal = aSignal[iSignal].toString();
        //String sSignal = "/";//aSignal[iSignal].toString();

        bZeroDivision = false;
        int resto = 0;


        clearFields(false);

        if (sSignal.equals("+"))
            Result = iFirstNumber + iSecondNumber;
        else {
            if (sSignal.equals("-")) {
                Result = iFirstNumber - iSecondNumber;

                if (Result < 0) {
                    bZeroDivision = true;
                    generateCalc();
                }
            } else {
                if (sSignal.equals("/")) {

                    resto = (int) (iFirstNumber % iSecondNumber);

                    if (resto != 0 || iFirstNumber == 0.0 || iSecondNumber == 0.0) {
                        bZeroDivision = true;
                        generateCalc();
                    } else
                        Result = iFirstNumber / iSecondNumber;
                } else if (sSignal.equals("x")) {
                    iFirstNumber = random.nextInt(10);
                    Result = iFirstNumber * iSecondNumber;
                }
            }
        }

        if (bZeroDivision == false) {

            String sFirstNumber = String.format("%.0f", iFirstNumber);
            String sSecondNumber = String.format("%.0f", iSecondNumber);
            //String sResult = String.valueOf(Result);
            //if (!sSignal.equals("/"))
            String sResult = String.format("%.0f", Result);

            tvNumber1.setText(sFirstNumber);
            tvSignal.setText(sSignal.toString());
            tvNumber2.setText(sSecondNumber);
            tvResult.setText(sResult);
        }
    }

    private boolean validaResultado(int pValorCalculado, int pValorDigitado) {

        boolean ok;
        if (pValorCalculado == pValorDigitado)
            ok = true;
        else
            ok = false;
        return ok;
    }

    private void startCountDown1() {

        CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int remaining = (int) ((millisUntilFinished / 1000));
                //textView5.setText("seconds remaining: " + millisUntilFinished / 1000);
                tvCountDown.setText(String.valueOf(remaining));
            }

            @Override
            public void onFinish() {
                startCountDown2();
            }
        }.start();
    }

    private void startCountDown2() {

        CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("GO");
            }

            @Override
            public void onFinish() {
                tvCountDown.setVisibility(View.INVISIBLE);
                tvCountDown.setText("");
                bChamaToast = false;
                generateCalc();
                gameCountDown();
            }
        }.start();
    }


    private void gameCountDown() {

        iTvProgress = Integer.parseInt(tvProgress.getText().toString());

        if (iProgressStatus == 0) {

            new Thread(new Runnable() {
                public void run() {
                    while (iProgressStatus <= 61) {
                        try {

                           // if (bRightAnswer==true) {
                           //     iProgressStatus = iProgressStatus -5;
                           //     iTvProgress = iTvProgress +5;
                           //     bRightAnswer = false;
                           // } else {
                            iProgressStatus = iProgressStatus + 1;
                            if ( iProgressStatus<=61)
                                iTvProgress = iTvProgress - 1;
                           // }

                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  tvProgress.setText(String.valueOf(iTvProgress));
                                                  try {
                                                      this.finalize();
                                                  } catch (Throwable throwable) {
                                                      throwable.printStackTrace();
                                                  }
                                              }
                                          }
                            );
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Update the progress bar
                        mHandler.post(new Runnable() {
                            public void run() {
                                mProgress.setProgress(iProgressStatus);

                            }
                        });
                    }
                    if (iProgressStatus > 61) {
                        //Toast.makeText(getApplicationContext(), "Time is up", Toast.LENGTH_LONG).show();
                        try {
                            bChamaToast = true;
                            iProgressStatus = 0;

                            // Chama um Toast (ou qq outro item da interface, dentro de uma thread)
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    mProgress.setProgress(iProgressStatus);
                                    mProgress.setEnabled(false);
                                    tvResult.setText("");
                                    button.setEnabled(false);

                                    String strResultado = "Time is up\n================" +
                                            "\nTotal de Acertos: " + iCountAcertos +
                                            "\nTotal de Erros: " + iCountErros +
                                            "\nPercentual de Acertos: " + fPercAcertos + "%";

                                    String sResultado = iCountAcertos +";" + iCountErros + ";" + fPercAcertos;

                                    Toast.makeText(getApplicationContext(), sResultado, Toast.LENGTH_LONG).show();

                                    // Exibe resultado em outra Activity
                                    Intent intent = new Intent(getApplication(), ResultActivity.class);
                                    intent.putExtra(EXTRA_MESSAGE, sResultado);

                                    startActivity(intent);

                                    iCountAcertos = 0;
                                    iCountErros = 0;

                                    try {
                                        this.finalize();
                                    } catch (Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                }
                            });
                            this.finalize();

                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    private void clearFields(boolean pArg) {

        if (pArg == true) {
            tvNumber1.setVisibility(View.INVISIBLE);
            tvSignal.setVisibility(View.INVISIBLE);
            tvNumber2.setVisibility(View.INVISIBLE);
            tvResult.setVisibility(View.INVISIBLE);
            edtResult.setText("");
        } else {
            tvNumber1.setVisibility(View.VISIBLE);
            tvSignal.setVisibility(View.VISIBLE);
            tvNumber2.setVisibility(View.VISIBLE);
            tvResult.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.dmoraes.mathtablesgame/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.dmoraes.mathtablesgame/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}