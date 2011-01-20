package com.happytap.bangbang;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.happytap.bangbang.activity.BangBangActivity;
import com.happytap.bangbang.activity.TextInputDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/9/11
 * Time: 8:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class InputUserNameActivity extends BangBangActivity {

    private ListView userNames;

    private ArrayAdapter<String> names;

    private TextInputDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_user_name_activity);
        userNames = (ListView) findViewById(R.id.user_names);


        names = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getNames());
        userNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {
                    if(dialog==null) {
                        dialog = new TextInputDialog(InputUserNameActivity.this);
                        dialog.setTitle("Enter Name:");
                        dialog.setListener(new TextInputDialog.TextInputListener() {
                            public void onText(String text) {
                                saveName(text);
                            }
                        });
                    }
                    dialog.show();
                } else {
                    saveName(names.getItem(i));
                }
            }
        });
        userNames.setAdapter(names);
    }

    private void saveName(String name) {
        getBangBang().setUsername(name);

        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private List<String> getNames() {
        List<String> list = new ArrayList<String>();
        list.add("- enter a name -");
        Account[] accounts = AccountManager.get(this).getAccounts();
        for(Account a : accounts) {
            if(a.type.contains("twitter") || a.type.contains("google") || a.type.contains("hotmail") || a.type.contains("facebook") || a.type.contains("yahoo")) {
                list.add(a.name.split("@")[0]);
            }
        }
        return list;
    }
}
