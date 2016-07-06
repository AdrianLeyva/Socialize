package teamprogra.app.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import teamprogra.app.socialize.socialize.R;

/**
 * Created by adrianleyva on 5/07/16.
 */
public class AdapterListViewRules extends ArrayAdapter<String> {
    FragmentActivity appCompatActivity;
    ArrayList<String> listRules;

    public AdapterListViewRules(FragmentActivity context, ArrayList<String> listRules) {
        super(context, R.layout.list_item_rules, listRules);
        appCompatActivity = context;
        this.listRules = listRules;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = appCompatActivity.getLayoutInflater();
        View item = inflater.inflate(R.layout.list_item_rules, null);

        TextView textViewRule = (TextView)item.findViewById(R.id.textView_itemRule);
        textViewRule.setText(listRules.get(position));

        Button buttonRule = (Button)item.findViewById(R.id.button_itemRule);
        buttonRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listRules.remove(position);
                notifyDataSetChanged();
            }
        });
        return (item);
    }
}
