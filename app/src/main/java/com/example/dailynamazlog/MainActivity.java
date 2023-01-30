package com.example.dailynamazlog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public String[] raqat;
     Spinner spinner;
     Spinner spinnerR;

    EditText raqats;
    CheckBox isBajamat;
    Button btnfajar;
    Button btnzuhr;
    Button btnasar;
    Button btnmaghrib;
    Button btnesha;
    Button btnInsert;

    DbHelper dbHelper;

    long stdID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=findViewById(R.id.spinner);
        dbHelper=new DbHelper(this);

        raqat=new String[5];
        Arrays.fill(raqat, "0,0");
        Student s=new Student("Muhammad Rizwan","Muhammad Ilyas","bitf19m018");
        Student s1=new Student("Muhammad Huzaifa","Muhammad Ilyas","bitf19m004");
        Student s2=new Student("danish","Muhammad Ilyas","bitf19m046");

        //dbHelper.addStudent(s);
        //dbHelper.addStudent(s1);
        //dbHelper.addStudent(s2);

        isBajamat=findViewById(R.id.checkBox);

        raqats=findViewById(R.id.editTextRaqat);

        btnfajar=findViewById(R.id.btnF);
        btnzuhr=findViewById(R.id.btnZ);
        btnasar=findViewById(R.id.btnA);
        btnmaghrib=findViewById(R.id.btnM);
        btnesha=findViewById(R.id.btnE);

        btnInsert=findViewById(R.id.btnInsert);

        loadStudents();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //dbHelper.insertNamaz(raqat,id);
                stdID=id;

                ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,dbHelper.getReport(stdID));
                arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerR.setAdapter(arrayAdapter1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    void loadStudents(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,dbHelper.getStudents());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }




    public String getData(){
        String str="";
        if(TextUtils.isEmpty(raqats.getText().toString())){
            str="0,";
        }else{
            str=raqats.getText().toString()+",";
        }

        if(isBajamat.isSelected()){
            str+="1";
        }else{
            str+="0";
        }
        return str;
    }

    public void FajarClicked(View view){
        raqat[0]=getData();
        raqats.setText("");
        isBajamat.setSelected(false);

    }
    public void ZuhrClicked(View view){
        raqat[1]=getData();
        raqats.setText("");
        isBajamat.setSelected(false);


    }
    public void AsarClicked(View view){
        raqat[2]=getData();
        raqats.setText("");
        isBajamat.setSelected(false);

    }
    public void MaghribClicked(View view){
        raqat[3]=getData();
        raqats.setText("");
        isBajamat.setSelected(false);

    }
    public void EshaClicked(View view){
        raqat[4]=getData();
        raqats.setText("");
        isBajamat.setSelected(false);

    }
    public void insertClicked(View view){
        if(dbHelper.insertNamaz(raqat,stdID)){
            Toast.makeText(this,"Inserted",Toast.LENGTH_LONG);
        }else{
            Toast.makeText(this,"Not Inserted",Toast.LENGTH_LONG);
        }

    }





}