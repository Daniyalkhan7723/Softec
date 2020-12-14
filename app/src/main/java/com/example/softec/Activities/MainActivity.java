package com.example.softec.Activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softec.MenuModel.MenuOrderData;
import com.example.softec.PojoClasses.CollectionList;
import com.example.softec.PojoClasses.OtherData;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;
import com.example.softec.StaticClasses.UnicodeFormatter;
import com.example.softec.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends Activity implements Runnable {

    private static final int REQUEST_ENABLE_BT = 2;

    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;

    ArrayList<MenuOrderData> menuOrderDataList = new ArrayList<>();
    OtherData otherData;

    int printstat;

    StaticClass st;
    ActivityMainBinding activityMainBinding;

    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        st = new StaticClass(this);

        if(getIntent()!=null){

            menuOrderDataList = (ArrayList<MenuOrderData>) getIntent().getSerializableExtra("collection_list");
            otherData = (OtherData) getIntent().getSerializableExtra("other_detail");

        }

        datatoWrite();

        activityMainBinding.scanDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initials();
            }
        });

        initials();

//        Calligrapher calligrapher = new Calligrapher(this);
//        calligrapher.setFont(this,"font/monster.ttf", true );

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        activityMainBinding.mPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {


                p1();

                int TIME = 10000; //5000 ms (5 Seconds)

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        p2(); //call function!

                        printstat = 1;
                    }
                }, TIME);


            }
        });

        controlLay(0);
    }


    private void controlLay(int i){

        activityMainBinding.printerProgress.setVisibility(i==2?View.VISIBLE:View.GONE);
        activityMainBinding.blueDeviceListView.setVisibility((i==1 || i==0)?View.VISIBLE:View.GONE);
        activityMainBinding.scanDevice.setVisibility((i==1 || i==0)?View.VISIBLE:View.GONE);
        activityMainBinding.mPrint.setVisibility(i==1?View.VISIBLE:View.GONE);
        activityMainBinding.bpstatus.setText(i==2?"Connecting....":i==1?"Connected...":"Can't Connect Try Again...");
    }

    private void initials(){

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }

        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        activityMainBinding.blueDeviceListView.setAdapter(mPairedDevicesArrayAdapter);
        activityMainBinding.blueDeviceListView.setOnItemClickListener(mDeviceClickListener);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();

        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                mPairedDevicesArrayAdapter.add(mDevice.getName()+"\n"+mDevice.getAddress());
            }
        } else {
            String mNoDevices = "None Paired";
            mPairedDevicesArrayAdapter.add(mNoDevices);
        }


        activityMainBinding.blueDeviceListView.setAnimation(st.getOneInAll(0));

    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> mAdapterView, View mView, int mPosition, long mLong) {

            try {

                mBluetoothAdapter.cancelDiscovery();
                String mDeviceInfo = ((TextView) mView).getText().toString();
                String mDeviceAddress = mDeviceInfo.substring(mDeviceInfo.length() - 17);
                mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                controlLay(2);
                Thread mBlutoothConnectThread = new Thread(MainActivity.this);
                mBlutoothConnectThread.start();

            } catch (Exception ex) {
                controlLay(1);
                st.toast(ex.getMessage());
            }
        }
    };

    private StringBuffer datatoWrite(){

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("Invoice\n\n");
        stringBuffer.append("Amount\t\t\t\t\t\t\t\t\t\tBK/USD\n\n");
        stringBuffer.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t--------\n");
        stringBuffer.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t--------\n");
        stringBuffer.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t--------\n\n\n");
        stringBuffer.append("Paid At\t\t\t\t\t\t\t\t\t\t12/12/2020\n\n\n");
        stringBuffer.append("Purchased Items\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");

        String fruits[] = {"Apple","Banana","Mango","Pineapple","Watermelon","Orange"};

        for(int i=0;i<fruits.length;i++){

            stringBuffer.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+fruits[i]+"\n\n");

        }

        stringBuffer.append("Thank You For Shopping Here!!!");

        return stringBuffer;

    }

    private StringBuffer datatWrite(){

        StringBuffer stringBuffer = new StringBuffer();

//        stringBuffer.append("My Printer Test");

        stringBuffer.append(("\n\n-> \n"));
        stringBuffer.append(("\n\n-> \tItem\t\t\t\t  Qty\tTotal\n\n"));

        for(int i=0;i<menuOrderDataList.size();i++){

            MenuOrderData col = menuOrderDataList.get(i);

            String name = col.getName();
            String price = col.getTotal_price()+"";
            String qty = col.getQuantity()+"";
            String description = col.getDescription();

            if(name.length()>15){
                name = name.substring(0,15)+"...";
            }
            else {
                for(int j=name.length();j<19;j++){
                    name += ".";
                }
            }

            description = (description.length()>15?description.substring(0,15):description)+"...";

            stringBuffer.append((i+1)+"-> "+name+"\t  "+qty+"\t\t$"+price+"\n\tDesc: "+description+"\n\n");

        }

        stringBuffer.append(".........................................." +
                "\n->\tTotal Price: \t\t"+otherData.getTotal_price()+
                "\n->\tPayment By: \t\t"+(otherData.getPayment_type().equals("cash")?"Cash":"Credit Card"));

//        Log.i("aj","\n\n"+stringBuffer.toString());

        return stringBuffer;

    }

    public void p1(){

        Thread t = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket.getOutputStream();

                    String data = datatoWrite().toString();
                    os.write(data.getBytes());

                    //This is printer specific code you can comment ==== > Start

                    // Setting height
                    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 104;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));


                } catch (Exception e) {
                    Log.e("PrintActivity", "Exe ", e);
                }
            }
        };
        t.start();
    }

    public void p2(){

        Thread tt = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket.getOutputStream();

                    String data = datatoWrite().toString();
                    os.write(data.getBytes());

                    //This is printer specific code you can comment ==== > Start

                    // Setting height
                    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 104;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));


                } catch (Exception e) {
                    Log.e("PrintActivity", "Exe ", e);
                }
            }
        };
        tt.start();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }


    public void onActivityResult(int mRequestCode, int mResultCode, Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    initials();
                    st.toast("Connected To Bluetooth");
                } else {
                    Toast.makeText(MainActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v("", "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            cancelHandler.sendEmptyMessage(0);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d("", "SocketClosed");
        } catch (IOException ex) {
            Log.d("", "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            controlLay(1);
        }
    };

    private Handler cancelHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            controlLay(0);
        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }

}
