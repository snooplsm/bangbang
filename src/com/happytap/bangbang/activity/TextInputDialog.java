package com.happytap.bangbang.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.happytap.bangbang.R;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/11/11
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextInputDialog extends Dialog {

    private EditText text;

    private Button ok;

    public TextInputListener getListener() {
        return listener;
    }

    public void setListener(TextInputListener listener) {
        this.listener = listener;
    }

    private TextInputListener listener;

    public TextInputDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
        setContentView(R.layout.text_dialog);
        text = (EditText)findViewById(R.id.text);
        ok = (Button)findViewById(android.R.id.button1);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(listener!=null) {
                    listener.onText(text.getText().toString());
                }
            }
        });

    }

    public interface TextInputListener {

        void onText(String text);

    }
}
