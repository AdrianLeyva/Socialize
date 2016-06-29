package teamprogra.app.socialize.socialize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.Gson;

import teamprogra.app.domain.User;
import teamprogra.app.util.Util;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private User user;
    private SocializeApplication app;
    private EditText editTextUbication;
    private EditText editTextAge;
    private RadioGroup radioGroupGender;
    private Spinner spinnerOcupation;
    private EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hace la actividad FULLSCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        app = (SocializeApplication) getApplicationContext();
        user = new User();
        editTextUbication = (EditText)findViewById(R.id.editText_ubicacionRA);
        editTextAge = (EditText)findViewById(R.id.editText_edadRA);
        radioGroupGender =  (RadioGroup)findViewById(R.id.radioGroup_generoRA);
        editTextPhone = (EditText)findViewById(R.id.editText_telefonoRA);
        spinnerOcupation = (Spinner) findViewById(R.id.spinner_ocupacionRA);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.ocupaciones,
                                                            R.layout.support_simple_spinner_dropdown_item);
        spinnerOcupation.setAdapter(adapter);
        spinnerOcupation.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /*
        String[] array = getResources().getStringArray(R.array.ocupaciones);
        String item = array[i];
        user.setOccupation(item);
        */
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getUserData(){
        user.setBirthday((editTextAge.getText().toString()));
        int radioButtonCheckedId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton radioButtonChecked = (RadioButton)findViewById(radioButtonCheckedId);
        user.setGender(radioButtonChecked.getText().toString());
        user.setPhone(Long.valueOf(editTextPhone.getText().toString()));
    }

    public void finalizeRegister(View view){
        getUserData();
        saveDataUser();
        Util.sendAndFinish(this,MainActivity.class);
    }

    public User getUserObject(){
        return user;
    }

    public void saveDataUser(){

    }
}
