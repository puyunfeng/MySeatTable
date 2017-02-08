package com.example.pyf.myseattable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SeatTable mySeatTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // com.wanjian.sak.LayoutManager.init(this);
        initView();
    }

    private void initView() {
        mySeatTable = (SeatTable) findViewById(R.id.mySeatTable);
        mySeatTable.setSeatChecker(new SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if(row==2&&column==3) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==4&&column==4){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return new String[0];
            }
        });
    }
}
