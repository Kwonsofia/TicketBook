package com.example.ledger2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


public class AddActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener {

    EditText eDate, eType, ePrice, eTitle;
    int year, month, day;
    String inputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // 현재 날짜 구하기
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        eDate = findViewById(R.id.input_date);
        eType = findViewById(R.id.input_type);
        ePrice = findViewById(R.id.input_price);
        eTitle = findViewById(R.id.input_title);

        inputDate = String.format("%04d-%02d-%02d", year,month+1, day);
        eDate.setText(inputDate);


        // 완료 버튼
        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("date", eDate.getText().toString());
                intent.putExtra("type", eType.getText().toString());
                intent.putExtra("price", ePrice.getText().toString());
                intent.putExtra("title", eTitle.getText().toString());
                setResult(0, intent);

                finish();
            }
        });

        // 취소 버튼
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, LedgerActivity.class);
                startActivity(intent);

            }
        });

        // 달력 버튼
        findViewById(R.id.imageView_calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        // 카테고리 버튼
        findViewById(R.id.imageView_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showType();
            }
        });
    }

    void showDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int month, int dayOfMonth) {
                inputDate = String.format("%04d-%02d-%02d",mYear,month+1,dayOfMonth);
                eDate.setText(inputDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    void showType(){
        DialogFragment singleChoiceDialog = new SingleChoiceDialogFragment();
        singleChoiceDialog.setCancelable(false);
        singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        eType.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

}

