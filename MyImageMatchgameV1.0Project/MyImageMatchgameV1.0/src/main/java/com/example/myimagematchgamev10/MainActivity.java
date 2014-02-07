package com.example.myimagematchgamev10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Random;

class MyCustomImageButton extends ImageButton
{
    protected int holdingImage;

    public MyCustomImageButton(Context context) {
        super(context);
        this.holdingImage = -1;
    }
    public MyCustomImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.holdingImage = -1;
    }
    public MyCustomImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.holdingImage = -1;
    }
}
//public class MainActivity extends ActionBarActivity {
    public class MainActivity extends Activity {
    Random myR = new Random ();
    MyCustomImageButton[] myImageButtons;
    int[] myImages={R.drawable.one,R.drawable.two,R.drawable.five,R.drawable.seven,R.drawable.eight,R.drawable.six,R.drawable.three,R.drawable.four};
    int[] myImageButtonArray = {R.id.imageButton0, R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, R.id.imageButton4,
            R.id.imageButton5, R.id.imageButton6, R.id.imageButton7, R.id.imageButton8, R.id.imageButton9,
            R.id.imageButton10, R.id.imageButton11, R.id.imageButton12, R.id.imageButton13, R.id.imageButton14,
            R.id.imageButton15};

    // The following four lies for holding temp data while checking the match
    int myFirstButtonImage = R.drawable.nine;
    int mySecondButtonImage = R.drawable.nine;
    ImageButton myFirstButton = null;
    ImageButton mySecondButton = null;

    // for message box
    int myNoOfClick = 0;
    int myNoOfCompletedCells = 0;

    Button myNewgame;
    AlertDialog.Builder alertDialog; // =  new AlertDialog.Builder(MainActivity.this);

    protected void initializeGrid()
    {
        for (int i=0; i < 16; ++i)
        {
            myImageButtons[i].setBackgroundResource(R.drawable.ic_launcher);
            myImageButtons[i].setClickable(true);
            myImageButtons[i].holdingImage = -1;
        }
        myNoOfClick = 0;
        myNoOfCompletedCells = 0;
    }

    protected void createGrid()
    {
        myImageButtons = new MyCustomImageButton[16];
        for (int i=0; i < 16; ++i)
        {
            myImageButtons[i] = (MyCustomImageButton) findViewById(myImageButtonArray[i]);

        }
    }

    protected void loadImages()
    {
        int myNextInt;
        for (int i=0; i < 8; ++i)
        {
            for(int j=0; j < 2; ++j)
            {
                myNextInt = myR.nextInt(16);
                while (myImageButtons[myNextInt].holdingImage != -1)
                {
                    myNextInt = myR.nextInt(16);
                }
                if(myImageButtons[myNextInt].holdingImage == -1)
                {
                    myImageButtons[myNextInt].setBackgroundResource(R.drawable.nine);
                    myImageButtons[myNextInt].holdingImage = myImages[i];
                    //myGridValues[myNextInt] = i+1;
                    // myMap.put(myImageButtonArray[myNextInt],myImages[i]);
                }
            }

        }
    }


    protected void showMessage()
    {
        alertDialog =  new AlertDialog.Builder(MainActivity.this);
        //alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Score");
        alertDialog.setMessage("No. Of clicks are:  " + myNoOfClick +" !");
        alertDialog.setInverseBackgroundForced(true);
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog,
                int which) {
                    dialog.dismiss();
                }
        });
        alertDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createGrid();
        initializeGrid();
//        loadImages();;

        myNewgame = (Button) findViewById(R.id.newGame);
        myNewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeGrid();
                loadImages();
            }
        });


        for(int i=0; i<myImageButtons.length; ++i)
        {
            final MyCustomImageButton myTempImageButton = myImageButtons[i];
            //final int myIBId = myTempImageButton.getId();
            myTempImageButton.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View view) {
                    ++myNoOfClick;
                    myTempImageButton.setBackgroundResource(myTempImageButton.holdingImage);
                    if (myFirstButtonImage == R.drawable.nine)
                    {
                        if(mySecondButtonImage == R.drawable.nine)
                        {
                            myFirstButtonImage = myTempImageButton.holdingImage;
                            myFirstButton = myTempImageButton;
                            myTempImageButton.setClickable(false);
                            myNoOfCompletedCells += 2;
                            if (myNoOfCompletedCells >= 16)
                            {
                                showMessage();
                            }
                        }
                        else
                        {
                            myFirstButton.setClickable(true);
                            mySecondButton.setClickable(true);
                            myFirstButton.setBackgroundResource(R.drawable.nine);
                            mySecondButton.setBackgroundResource(R.drawable.nine);

                            myFirstButton = myTempImageButton;
                            myFirstButtonImage = myTempImageButton.holdingImage;
                            myTempImageButton.setClickable(false);
                        }
                    }
                    else
                    {
                        myTempImageButton.setClickable(false);
                        if (myFirstButtonImage == myTempImageButton.holdingImage)   // For Success full Match
                        {
                            myFirstButton.setClickable(false);
                            myFirstButtonImage = R.drawable.nine;
                            mySecondButtonImage = R.drawable.nine;
                        }
                        else
                        {
                            mySecondButton = myTempImageButton;
                            mySecondButtonImage = myTempImageButton.holdingImage;
                            myFirstButtonImage = R.drawable.nine;
                        }
                    }
                }
            });
        }

    }


}
