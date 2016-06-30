package teamprogra.app.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import teamprogra.app.socialize.socialize.R;
import teamprogra.app.socialize.socialize.SocializeApplication;
import teamprogra.app.util.CircleTransform;
import teamprogra.app.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDataUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDataUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDataUser extends Fragment {

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
    private Button buttonSetImage;
    private Button buttonSetBirthDay;
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
        getDataUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_user,container,false);
        imageViewUser = (ImageView)view.findViewById(R.id.imageView_userImageDU);
        editTextName = (EditText)view.findViewById(R.id.editText_nameDU);
        editTextEmail = (EditText)view.findViewById(R.id.editText_emailDU);
        editTextUbication = (EditText)view.findViewById(R.id.editText_ubicationDU);
        editTextBirthDay = (EditText)view.findViewById(R.id.editText_birthdayDU);
        editTextPhone = (EditText)view.findViewById(R.id.editText_phoneDU);
        radioGroupGender = (RadioGroup)view.findViewById(R.id.radioGroup_generoDU);
        buttonSetImage = (Button)view.findViewById(R.id.button_setImageDU);
        buttonSave = (Button)view.findViewById(R.id.button_saveDU);
        buttonSetBirthDay = (Button)view.findViewById(R.id.button_configBirthdayDU);

        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());
        editTextBirthDay.setText(user.getBirthday());
        editTextUbication.setText(user.getLocale());

        Picasso.with(getContext()).load(user.getPhoto()).transform(new CircleTransform()).into(imageViewUser);

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

    public void getDataUser(){
        user.setName(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_NAME));
        user.setEmail(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_EMAIL));
        user.setBirthday(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_BIRTHDAY));
        user.setId(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_ID));
        user.setGender(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_GENDER));
        user.setPhoto(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_PICTURE));
        user.setLocale(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_LOCALE));
    }
}
