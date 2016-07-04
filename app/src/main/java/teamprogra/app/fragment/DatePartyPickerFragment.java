package teamprogra.app.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import teamprogra.app.socialize.socialize.R;

/**
 * Created by adrianleyva on 3/07/16.
 */
public class DatePartyPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String date;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Utilice la fecha actual como la fecha predeterminada en el selector
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Crear una nueva instancia de DatePickerDialog y devolverlo
        DatePickerDialog dpd = new  DatePickerDialog(getActivity(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK,this, year, month, day);
        DatePicker dp = dpd.getDatePicker();
        dp.setMinDate(c.getTimeInMillis());
        return dpd;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        EditText editTextBirthDay = (EditText)getActivity().findViewById(R.id.editText_dateFS);
        editTextBirthDay.setText(date);
    }
}
