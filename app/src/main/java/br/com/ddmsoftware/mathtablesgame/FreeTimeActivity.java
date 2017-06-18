package br.com.ddmsoftware.mathtablesgame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;




public class FreeTimeActivity extends AppCompatActivity {

    TableGame tableGame = new TableGame();

    Timer timer;

    public static final String EXTRA_MESSAGE = "br.com.ddmsoftware.mathtablesgame.MESSAGE";

    String sResultado = "";
    Button btnStartNewGame;
    Button btnVoltar;
    MediaPlayer errorSound;
    MediaPlayer okSound;

    Boolean bExtras_StartCountdown_Game;

    Character[] aSignal = {'+', '-', '/', 'x'};
    Boolean bZeroDivision = false;

    float fPercAcertos = 0;
    int iCountAcertos = 0;
    int iCountErros = 0;
    int iCountAcertosFreeTime = 0;

    float iOldFistNumber = 0;
    float iOldSecondNumber = 0;

    boolean STOP_COUNTDOWN = false;
    boolean bRestartApp = true;
    boolean bRestartStars = false;

    ImageView imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;

    LinearLayout linearMainCountainer;
    TextView tvNumber1;
    TextView tvSignal;
    TextView tvNumber2;
    TextView tvResultCalculado;
    TextView tvResultDigitado;
    TextView tvCountDown;

    TextView tvGridResult_TotAcertos;
    TextView tvGridResult_TotErros;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_time);

        Bundle extras = getIntent().getExtras();

        bExtras_StartCountdown_Game = extras.getBoolean("StartCountdown:");

        linearMainCountainer = (LinearLayout) findViewById(R.id.linearFTMainCountainer);

        //textView = (TextView)findViewById(R.id.tvFTResultDigitado);
        tvNumber1 = (TextView) findViewById(R.id.tvFTFirstNumber);
        tvNumber2 = (TextView) findViewById(R.id.tvFTSecondNumber);
        tvSignal = (TextView) findViewById(R.id.tvFTSignal);
        tvResultDigitado = (TextView) findViewById(R.id.tvFTResultDigitado);
        tvResultCalculado = (TextView) findViewById(R.id.tvFTResultCalculado);

        imgStar1 = (ImageView) findViewById(R.id.imgStar1);
        imgStar2 = (ImageView) findViewById(R.id.imgStar2);
        imgStar3 = (ImageView) findViewById(R.id.imgStar3);
        imgStar4 = (ImageView) findViewById(R.id.imgStar4);
        imgStar5 = (ImageView) findViewById(R.id.imgStar5);

        tvGridResult_TotAcertos = (TextView) findViewById(R.id.tvFTGridResult_TotalAcertos);
        tvGridResult_TotErros = (TextView) findViewById(R.id.tvFTGridResult_TotalErros);

        final Button btnOne = (Button) findViewById(R.id.btnFTOne);
        final Button btnTwo = (Button) findViewById(R.id.btnFTTwo);
        final Button btnThree = (Button) findViewById(R.id.btnFTThree);
        final Button btnFour = (Button) findViewById(R.id.btnFTFour);
        final Button btnFive = (Button) findViewById(R.id.btnFTFive);
        final Button btnSix = (Button) findViewById(R.id.btnFTSix);
        final Button btnSeven = (Button) findViewById(R.id.btnFTSeven);
        final Button btnEight = (Button) findViewById(R.id.btnFTEight);
        final Button btnNine = (Button) findViewById(R.id.btnFTNine);
        final Button btnZero = (Button) findViewById(R.id.btnFTZero);
        final Button btnSend = (Button) findViewById(R.id.btnFTSend);
        final Button btnErase = (Button) findViewById(R.id.btnFTErase);

        btnStartNewGame = (Button) findViewById(R.id.btnFTStartNewGame);
        btnVoltar = (Button) findViewById(R.id.btnFTVoltar1);

        //errorSound = MediaPlayer.create(FreeTimeActivity.this, R.raw.error);
        //okSound = MediaPlayer.create(FreeTimeActivity.this,R.raw.ok);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnOne);
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnTwo);
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnThree);
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnFour);
            }
        });
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnFive);
            }
        });
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnSix);
            }
        });
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnSeven);
            }
        });
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnEight);
            }
        });
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnNine);
            }
        });
        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKeyboardEntry(btnZero);
            }
        });

        linearMainCountainer.setVisibility(View.INVISIBLE);

        btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvResultDigitado.getText().length() > 0)
                    tvResultDigitado.setText(tvResultDigitado.getText().toString().substring(0, tvResultDigitado.getText().length() - 1));
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvResultDigitado.getText().toString().trim().equals("")) {
                    Toast.makeText(getBaseContext(), "DIGITE UM VALOR ANTES DE CONTINUAR.", Toast.LENGTH_SHORT).show();
                } else {

                    int iValorDigitado = Integer.parseInt(tvResultDigitado.getText().toString());
                    int iValorCalculado = Integer.parseInt(tvResultCalculado.getText().toString());

                    if (tableGame.validateResult(iValorCalculado, iValorDigitado)) {
                        //okSound.start();
                        iCountAcertos++;
                        iCountAcertosFreeTime++;

                        Toast.makeText(getBaseContext(), "Resposta Correta", Toast.LENGTH_SHORT).show();
                    } else {
                        //errorSound.start();
                        iCountErros++;
                        iCountAcertosFreeTime = 0;
                        bRestartStars = true;
                        Toast.makeText(getBaseContext(), "Resposta INCORRETA!!!", Toast.LENGTH_SHORT).show();

                    }

                    fPercAcertos = iCountAcertos * 100 / (iCountAcertos + iCountErros);

                    tvGridResult_TotAcertos.setText(String.valueOf(iCountAcertos));
                    tvGridResult_TotErros.setText(String.valueOf(iCountErros));

                    // Rank Stars according to the Results
                    tableGame.rankResult(iCountAcertosFreeTime, bRestartStars);

                    tableGame.generateNewCalc();

                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //tableGame.startNotificationService();
                tableGame.startNewGame(true);
                tableGame.loadAdvertisement();

                btnStartNewGame.setVisibility(View.INVISIBLE);
                btnVoltar.setVisibility(View.INVISIBLE);
                linearMainCountainer.setVisibility(View.VISIBLE);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Toast.makeText(getApplicationContext(), "Passei no OnDestroy", Toast.LENGTH_LONG).show();

        tableGame.stopCountDown();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bRestartApp) {

            sResultado = "";
            bRestartApp = false;

            //Toast.makeText(getApplicationContext(), "Passei no ON_RESUME", Toast.LENGTH_LONG).show();

            btnVoltar.setVisibility(View.VISIBLE);
            btnStartNewGame.setVisibility(View.VISIBLE);
            linearMainCountainer.setVisibility(View.INVISIBLE);

            timer = new Timer();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        bRestartApp = !sResultado.equals("");

        //Toast.makeText(getApplicationContext(), "Passei no ON_PAUSE: " + sResultado, Toast.LENGTH_LONG).show();

    }

    private void getKeyboardEntry(Button mButtonText) {

        String sResult = tvResultDigitado.getText() + mButtonText.getText().toString();
        tvResultDigitado.setText(sResult);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("FreeTime Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class TableGame {

        private void initializeVariables() {

            errorSound = MediaPlayer.create(FreeTimeActivity.this, R.raw.error);
            okSound = MediaPlayer.create(FreeTimeActivity.this, R.raw.ok);

            iCountAcertos = 0;
            iCountErros = 0;
            fPercAcertos = 0;

            tvGridResult_TotAcertos.setText("");
            tvGridResult_TotErros.setText("");
        }

        private void rankResult(int pTotalAcertos, boolean pRestart_Stars) {

            if (!pRestart_Stars) {
                switch (pTotalAcertos) {
                    case 10:
                        imgStar1.setImageResource(R.drawable.star32yellow);
                        okSound.start();
                        break;
                    case 20:
                        imgStar1.setImageResource(R.drawable.star32yellow);
                        imgStar2.setImageResource(R.drawable.star32yellow);
                        okSound.start();
                        break;
                    case 30:
                        imgStar1.setImageResource(R.drawable.star32yellow);
                        imgStar2.setImageResource(R.drawable.star32yellow);
                        imgStar3.setImageResource(R.drawable.star32yellow);
                        okSound.start();
                        break;
                    case 40:
                        imgStar1.setImageResource(R.drawable.star32yellow);
                        imgStar2.setImageResource(R.drawable.star32yellow);
                        imgStar3.setImageResource(R.drawable.star32yellow);
                        imgStar4.setImageResource(R.drawable.star32yellow);
                        okSound.start();
                        break;
                    case 50:
                        imgStar1.setImageResource(R.drawable.star32yellow);
                        imgStar2.setImageResource(R.drawable.star32yellow);
                        imgStar3.setImageResource(R.drawable.star32yellow);
                        imgStar4.setImageResource(R.drawable.star32yellow);
                        imgStar5.setImageResource(R.drawable.star32yellow);
                        okSound.start();
                        break;
                }

            } else {
                bRestartStars = false;
                imgStar1.setImageResource(R.drawable.star32gray);
                imgStar2.setImageResource(R.drawable.star32gray);
                imgStar3.setImageResource(R.drawable.star32gray);
                imgStar4.setImageResource(R.drawable.star32gray);
                imgStar5.setImageResource(R.drawable.star32gray);
                errorSound.start();

            }
        }

        private void startCountDown(final String pSeconds) {

            //final Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {

                int i = Integer.parseInt(pSeconds);

                public void run() {

                    System.out.println(i--);

                    STOP_COUNTDOWN = false;

                    if (i <= 0) {
                        timer.cancel();

                        i = 0;
                        STOP_COUNTDOWN = true;

                    }

                    // Apenas para atualizar o User Interface
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //tvCountDown.setText("Countdown:\n" + (i+1));
                            tvCountDown.setText(String.valueOf(i));

                            if (STOP_COUNTDOWN) {

                                //Thread.currentThread().interrupt();

                                //Toast.makeText(FreeTimeActivity.this, "Total de Acertos: " + iCountAcertos + "\n + Total de Erros: " + iCountErros, Toast.LENGTH_SHORT).show();

                                sResultado = iCountAcertos + ";" + iCountErros + ";" + fPercAcertos;

                                //Toast.makeText(getApplicationContext(), sResultado, Toast.LENGTH_LONG).show();

                                bRestartApp = true;

                                // Exibe resultado em outra Activity
                                Intent intent = new Intent(getApplication(), ResultActivity.class);
                                intent.putExtra(EXTRA_MESSAGE, sResultado);

                                startActivity(intent);
                            }
                        }
                    });
                }
            }, 0, 1000);
        }

        private void stopCountDown() {
            timer.cancel();
        }

        private void startNewGame(boolean pStartOnResume) {

            if (pStartOnResume) {
                // Inicia as variaveis globais
                initializeVariables();

                // Gera um Novo Calculo
                generateNewCalc();

                // Iniciar a Contagem Regressiva

                // startCountDown("61");
            }
        }

        private void generateNewCalc() {

            tvResultDigitado.setText("");

            Random random = new Random();

            float iFirstNumber = random.nextInt(40);
            float iSecondNumber = random.nextInt(10);

            if ((iOldFistNumber == iFirstNumber) && (iOldSecondNumber == iSecondNumber)) {

                generateNewCalc();

            } else {

                iOldFistNumber = iFirstNumber;
                iOldSecondNumber = iSecondNumber;
            }

            int iSignal = random.nextInt(4);
            String sSignal = aSignal[iSignal].toString();
            //String sSignal = "/";//aSignal[iSignal].toString();

            bZeroDivision = false;
            int resto;
            float Result = 0;

            //clearFields(false);
            switch (sSignal) {

                case "+":
                    Result = iFirstNumber + iSecondNumber;
                    break;
                case "-":
                    Result = iFirstNumber - iSecondNumber;
                    if (Result < 0) generateNewCalc();
                    bZeroDivision = true;
                    break;
                case "/":
                    resto = (int) (iFirstNumber % iSecondNumber);

                    if (resto != 0 || iFirstNumber == 0.0 || iSecondNumber == 0.0) {
                        generateNewCalc();
                        bZeroDivision = true;
                    }
                    break;
                case "x":
                    iFirstNumber = random.nextInt(10);
                    Result = iFirstNumber * iSecondNumber;
                    break;
            }

            /*
            if (sSignal.equals("+"))
            else if (sSignal.equals("-")) {
                Result = iFirstNumber - iSecondNumber;
                if (Result < 0) generateNewCalc();
                bZeroDivision = true;
            } else {
                if (sSignal.equals("/")) {

                    resto = (int) (iFirstNumber % iSecondNumber);

                    if (resto != 0 || iFirstNumber == 0.0 || iSecondNumber == 0.0) {
                        generateNewCalc();
                        bZeroDivision = true;
                    } else
                        Result = iFirstNumber / iSecondNumber;
                } else if (sSignal.equals("x")) {
                    iFirstNumber = random.nextInt(10);
                    Result = iFirstNumber * iSecondNumber;
                }
            } */

            if (!bZeroDivision) {

                String sFirstNumber = String.format("%.0f", iFirstNumber);
                String sSecondNumber = String.format("%.0f", iSecondNumber);
                //String sResult = String.valueOf(Result);
                //if (!sSignal.equals("/"))
                String sResult = String.format("%.0f", Result);

                tvNumber1.setText(sFirstNumber);
                tvSignal.setText(sSignal);
                tvNumber2.setText(sSecondNumber);
                tvResultCalculado.setText(sResult);
            }
        }

        private boolean validateResult(int pCalculatedValue, int pTypedValue) {
            boolean ok;
            //okSound.start();
            //zerrorSound.start();
            ok = pCalculatedValue == pTypedValue;
            return ok;
        }

        private void startNotificationService() {
            // Enable Notification Service (12 hours)
            NotificationEventReceiver.setupAlarm(getApplicationContext());
        }

        private void loadAdvertisement() {
            // Create a AdView
            // Load Advertisement Banner
            AdView mFTAdView = (AdView) findViewById(R.id.adViewFTTableGame);
            AdRequest adFTRequest = new AdRequest.Builder().build();
            mFTAdView.loadAd(adFTRequest);
        }
    }
}
