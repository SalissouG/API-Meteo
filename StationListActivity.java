package com.example.sgdjibo.hello;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by sgdjibo on 15/11/15.
 */
public class StationListActivity extends ListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] values = new String[] { "Stations de Ski Alpes du Nord", "Stations Ski Alpes du Sud", "Stations de Ski Pyrénées ",
                                          "Stations de Ski Massif Central", "Station de Ski Jura","Stations de Ski Corse",
                                          "Stations de Ski Apex Mountain","Stations de Ski Vosges","Stations de Ski Big White","Stations de Ski calabogie Peaks ",
                                          "Stations de Ski Camp Fortune","Stations de Ski Castle Mountain","Stations de Ski Glen Eden","Stations de Ski Grouse Mountain","Stations de Ski Hidden Valley"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.activity_list, values);

                     setListAdapter(adapter);

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        int itemPosition     = position;
        String  itemValue    = (String) l.getItemAtPosition(position);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",itemValue);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }

}

