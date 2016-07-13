package teamprogra.app.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import teamprogra.app.domain.User;
import teamprogra.app.socialize.socialize.LoginActivity;
import teamprogra.app.socialize.socialize.R;
import teamprogra.app.socialize.socialize.SocializeApplication;
import teamprogra.app.util.CircleTransform;
import teamprogra.app.util.EmailValidator;
import teamprogra.app.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDataUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDataUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDataUser extends Fragment implements View.OnClickListener {

    public static final int ACTIVITY_SELECT_IMAGE = 1020;
    private User user;
    private SocializeApplication app;
    private OnFragmentInteractionListener mListener;

    private ImageView imageViewUser;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextUbication;
    private EditText editTextPhone;
    private EditText editTextBirthDay;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Button buttonSave;

    public FragmentDataUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment FragmentDataUser.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDataUser newInstance() {
        FragmentDataUser fragment = new FragmentDataUser();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (SocializeApplication)getActivity().getApplicationContext();
        user = new User();
        user.getDataUser(app);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_user,container,false);

        editTextName = (EditText)view.findViewById(R.id.editText_nameDU);
        editTextEmail = (EditText)view.findViewById(R.id.editText_emailDU);
        editTextBirthDay = (EditText)view.findViewById(R.id.editText_birthdayDU);
        editTextUbication = (EditText)view.findViewById(R.id.editText_ubicationDU);
        editTextPhone = (EditText)view.findViewById(R.id.editText_phoneDU);
        buttonSave = (Button)view.findViewById(R.id.button_saveDU);
        imageViewUser = (ImageView)view.findViewById(R.id.imageView_userImageDU);
        radioGroupGender = (RadioGroup)view.findViewById(R.id.radioGroup_generoDU);

        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());
        editTextBirthDay.setText(user.getBirthday());
        editTextUbication.setText(user.getLocale());
        editTextPhone.setText(user.getPhone());

        imageViewUser.setOnClickListener(this);
        editTextBirthDay.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        Picasso.with(getContext()).load(user.getPhoto()).error(R.drawable.login).transform(new CircleTransform()).into(imageViewUser);

        if(user.getGender().equals("male")){
            radioButtonMale = (RadioButton) radioGroupGender.findViewById(R.id.radioButton_masculinoDU);
            radioButtonMale.setChecked(true);
        }else if(user.getGender().equals("female")){
            radioButtonFemale = (RadioButton) radioGroupGender.findViewById(R.id.radioButton_femeninoDU);
            radioButtonFemale.setChecked(true);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_userImageDU:
                Intent galeyIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galeyIntent.setType("image/*");
                startActivityForResult(galeyIntent, ACTIVITY_SELECT_IMAGE);
                break;
            case R.id.editText_birthdayDU:
                DialogFragment dp = new DatePickerFragment();
                dp.show(getActivity().getFragmentManager(),"DATEPICKER");
                break;
            case R.id.button_saveDU:
                if(Util.isOnline(getContext())){
                    if (EmailValidator.verifyEmail(editTextEmail.getText().toString())){
                        saveDataUser();
                        getActivity().recreate();
                        Util.showToastShort(this.getActivity(),"Datos guardados correctamente");
                    }else{
                        editTextEmail.setError("Introduce un email válido");
                    }
                }else{
                    Snackbar.make(view, "Error, verifique su conexión a Internet", Snackbar.LENGTH_LONG)
                            //.setActionTextColor(Color.CYAN)
                            .setActionTextColor(getResources().getColor(R.color.colorAccent))
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .show();
                }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVITY_SELECT_IMAGE && resultCode == getActivity().RESULT_OK){
            user.setPhoto(data.getDataString());
            Picasso.with(getContext()).load(user.getPhoto()).transform(new CircleTransform()).into(imageViewUser);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void saveDataUser(){
        app.saveValuePreferences(SocializeApplication.APP_VALUE_NAME,editTextName.getText().toString());
        app.saveValuePreferences(SocializeApplication.APP_VALUE_EMAIL,editTextEmail.getText().toString());
        app.saveValuePreferences(SocializeApplication.APP_VALUE_BIRTHDAY,editTextBirthDay.getText().toString());
        app.saveValuePreferences(SocializeApplication.APP_VALUE_PHONE,editTextPhone.getText().toString());
        app.saveValuePreferences(SocializeApplication.APP_VALUE_PICTURE,user.getPhoto());
        if(radioButtonMale.isChecked()){
            app.saveValuePreferences(SocializeApplication.APP_VALUE_GENDER,"male");
        }else {
            app.saveValuePreferences(SocializeApplication.APP_VALUE_GENDER,"female");
        }
    }

}
