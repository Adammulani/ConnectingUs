package com.example.connectingus.conversation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectingus.R;
import com.example.connectingus.adapters.TempMsgAdapter;
import com.example.connectingus.models.TempMsgModel;
import com.example.connectingus.models.User;
import com.example.connectingus.profile.ChatProfile;

import java.util.ArrayList;

public class TempDetailChatView extends AppCompatActivity {

    ImageView ivProf;
    ImageButton ibSend;
    ImageButton ibRecv;
    TextView tvUname;
    EditText etM;
    User user;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailchatview);
        ibSend=findViewById(R.id.sendButton);
        ibRecv=findViewById(R.id.receiveButton);

        ibSend.setVisibility(View.INVISIBLE);
        ibRecv.setVisibility(View.INVISIBLE);

        recyclerView=findViewById(R.id.recyclerview1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // back button

        getSupportActionBar().setDisplayShowTitleEnabled(false);  // hide Title

        getSupportActionBar().setDisplayShowCustomEnabled(true); // Custom Appbar
        LayoutInflater layoutInflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_appbar,null);
        getSupportActionBar().setCustomView(view);

        relativeLayout=findViewById(R.id.reltvlyout);
        ivProf=findViewById(R.id.iv_prof_pic);
        tvUname=findViewById(R.id.tv_uname);

        Intent intent=getIntent();
        if(intent.getExtras()!=null)
        {
            user= (User) intent.getSerializableExtra("user");
            ivProf.setImageResource(user.getImageId());
            tvUname.setText(user.getName());
        }

        etM=findViewById(R.id.edMsg);

        etM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str=etM.getText().toString().trim();
                if(!str.isEmpty())
                {
                    ibSend.setVisibility(View.VISIBLE);
                    ibRecv.setVisibility(View.VISIBLE);
                }
                else
                {
                    ibSend.setVisibility(View.INVISIBLE);
                    ibRecv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final ArrayList<TempMsgModel> tempMsgModels=new ArrayList<>();
        final TempMsgAdapter tempMsgAdapter=new TempMsgAdapter(tempMsgModels,this);
        recyclerView.setAdapter(tempMsgAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=etM.getText().toString().trim();
                TempMsgModel tempMsgModel=new TempMsgModel(msg,1);
                tempMsgModels.add(tempMsgModel);
                tempMsgAdapter.notifyDataSetChanged();
                etM.setText("");
            }
        });

        ibRecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=etM.getText().toString().trim();
                TempMsgModel tempMsgModel=new TempMsgModel(msg,2);
                tempMsgModels.add(tempMsgModel);
                tempMsgAdapter.notifyDataSetChanged();
                etM.setText("");
            }
        });


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChatProf = new Intent(getApplicationContext(), ChatProfile.class);
                intentChatProf.putExtra("UserDetails",user);
                intentChatProf.putExtra("calling_activity","TempDetailChatView");
                startActivity(intentChatProf);
                finish();
            }
        });
    }


    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_chat_menus,menu);
        return super.onCreateOptionsMenu(menu);
    }

}