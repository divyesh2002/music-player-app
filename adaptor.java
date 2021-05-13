package com.example.djplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class adaptor extends ArrayAdapter<String> {
    private String [] s;
    private Context context;
    public adaptor(@NonNull Context context, int resource, @NonNull String[] s) {
        super(context, resource, s);
        this.s=s;
        this.context=context;
    }
    @Nullable
    @Override
    public String getItem(int position) {
        return s[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.divyeshlayout,parent,false);
        TextView t=convertView.findViewById(R.id.textView);
        t.setText(getItem(position));

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "You click on "+s[position]+" "+position, Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }
}
