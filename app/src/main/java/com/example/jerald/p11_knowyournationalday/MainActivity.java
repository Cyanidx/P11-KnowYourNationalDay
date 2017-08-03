package com.example.jerald.p11_knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG = "MA>>";
    ListView lv;
    ArrayList<String> alStudent;
    ArrayAdapter<String> aaStudent;
    boolean loginA;
    int score;
    RadioGroup rg, rg1, rg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.lv);
        alStudent = new ArrayList<String>();
        aaStudent = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alStudent);
        lv.setAdapter(aaStudent);

        Log.i(TAG, "onCreate: "+loginA);


    }
    public void check(){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout passPhrase =
                (LinearLayout) inflater.inflate(R.layout.login, null);
        final EditText etLogin = (EditText) passPhrase
                .findViewById(R.id.editTextLogin);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Login")
                .setView(passPhrase)
                .setCancelable(false)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String code = etLogin.getText().toString();
                        if(code.equals("738964")){

                            alStudent.add("Singapore National Day is on 9 Aug");
                            alStudent.add("Singapore is 52 year old");
                            alStudent.add("Theme is '#OneNation Together'");
                            aaStudent.notifyDataSetChanged();
                            loginA = true;
                            SharedPreferences pref = getSharedPreferences("save_state",MODE_PRIVATE);
                            SharedPreferences.Editor prefEdit = pref.edit();
                            prefEdit.putBoolean("state",true);
                            prefEdit.commit();
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong Access Code", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.action_quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (item.getItemId() == R.id.action_send){
            String [] list = new String[] { "Email", "SMS" };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0

                        public void onClick(DialogInterface dialog, int which) {
                            String message = "";
                            for(int i = 0; i < alStudent.size(); i++){
                                message += (i+1) + ". " + alStudent.get(i) + "\n";
                            }
                            if (which == 0) {
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"15017292@myrp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "-");
                                email.putExtra(Intent.EXTRA_TEXT,
                                        "Hi Jerald, \n" + message);
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));
                                Toast.makeText(MainActivity.this, "Email has been sent",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                sendIntent.setData(Uri.parse("sms:"));

                                sendIntent.putExtra("sms_body", message);
                                startActivity(sendIntent);

                                Toast.makeText(MainActivity.this, "SMS has been sent",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.action_quiz){
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout quiz =
                    (LinearLayout) inflater.inflate(R.layout.quiz, null);

            rg = (RadioGroup)quiz.findViewById(R.id.rg);
            rg1 = (RadioGroup)quiz.findViewById(R.id.rg1);
            rg2 = (RadioGroup)quiz.findViewById(R.id.rg2);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Test Yourself!")
                    .setView(quiz)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            int selected = rg.getCheckedRadioButtonId();
                            int selected1 = rg1.getCheckedRadioButtonId();
                            int selected2 = rg2.getCheckedRadioButtonId();

                            if(selected == -1 | selected1 == -1 | selected2 == -2){
                                Toast.makeText(MainActivity.this, "Please answer all the Question", Toast.LENGTH_SHORT).show();
                            } else {
                                if(selected == R.id.rbNo){
                                    score = score + 1;
                                }
                                if(selected1 == R.id.rbYes1){
                                    score = score + 1;
                                }
                                if(selected2 == R.id.rbYes2){
                                    score = score + 1;
                                }
                                Toast.makeText(MainActivity.this, "Your Total Score is " + score, Toast.LENGTH_SHORT).show();
                                score = 0;
                            }
                        }
                    })
                    .setNegativeButton("Don't know lah", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if(item.getItemId() == R.id.action_logout){
            SharedPreferences pref = getSharedPreferences("save_state",MODE_PRIVATE);
            SharedPreferences.Editor prefEdit = pref.edit();
            prefEdit.putBoolean("state",false);
            prefEdit.commit();
            alStudent.clear();
            aaStudent.notifyDataSetChanged();
            check();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("save_state",MODE_PRIVATE);
        loginA = prefs.getBoolean("state",false);
        if(loginA == false){
            check();

        } else if(loginA == true){
            alStudent.add("Singapore National Day is on 9 Aug");
            alStudent.add("Singapore is 52 year old");
            alStudent.add("Theme is '#OneNation Together'");
            aaStudent.notifyDataSetChanged();
        }
        Log.i("", "onResume: "+ loginA);
    }
}
