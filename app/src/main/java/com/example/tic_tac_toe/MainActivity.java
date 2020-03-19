package com.example.tic_tac_toe;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Vibrator;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] buttons = new Button[3][3];
    TextView ptsP1, ptsP2;
    boolean player1Turn = true;
    int roundCount=0;

    public int player1Pts=0;
    public int player2Pts=0;

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        ptsP1 = (TextView) findViewById(R.id.ptsP1);
        ptsP2 = (TextView) findViewById(R.id.ptsP2);

        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++){
                String buttonID = "btn_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.resetBtn);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {


        vibrator.vibrate(100);
        if(!((Button) v).getText().toString().equals("")){
            return;
        }

        if(player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 8) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    public boolean checkForWin(){
        String[][] field = new String[3][3];

        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2]) &&
                    !field[i][0].equals("")){
                return true;
            }
        }
        for (int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i]) &&
                    !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2]) &&
                !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0]) &&
                !field[0][2].equals("")){
            return true;
        }

        return false;
    }

    public void player1Wins(){
        player1Pts++;
        Toast.makeText(this, "Player 1 WON.", Toast.LENGTH_LONG).show();
        updatePts();
        reset();
    }
    public void player2Wins(){
        player2Pts++;
        Toast.makeText(this, "Player 2 WON.", Toast.LENGTH_LONG).show();
        updatePts();
        reset();
    }
    public void draw(){
        Toast.makeText(this, "Match Draw.", Toast.LENGTH_LONG).show();
        reset();
    }

    public void reset(){
        for(int i=0;i<3;i++) {
          for (int j = 0; j < 3; j++) {
              buttons[i][j].setText("");
          }
        }
        roundCount=0;
        player1Turn=true;
    }

    public void updatePts(){
        ptsP1.setText(String.valueOf(player1Pts));
        ptsP2.setText(String.valueOf(player2Pts));
    }

    public void resetGame() {
        player1Pts=0;
        player2Pts=0;
        updatePts();
        reset();
    }
}