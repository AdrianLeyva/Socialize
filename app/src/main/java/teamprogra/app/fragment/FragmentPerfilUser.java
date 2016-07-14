package teamprogra.app.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import teamprogra.app.domain.User;
import teamprogra.app.socialize.socialize.R;
import teamprogra.app.socialize.socialize.SocializeApplication;
import teamprogra.app.utils.CircleTransform;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPerfilUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPerfilUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPerfilUser extends Fragment {

    private User user;
    private SocializeApplication app;
    private OnFragmentInteractionListener mListener;

    private ImageView imageViewUser;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewUbication;
    private TextView textViewEventRealized;
    private TextView textViewEventsHistorial;
    private RatingBar ratingBarUser;

    public FragmentPerfilUser() {
        // Required empty public constructor
    }


    public static FragmentPerfilUser newInstance() {
        FragmentPerfilUser fragment = new FragmentPerfilUser();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_user, container, false);
        imageViewUser = (ImageView)view.findViewById(R.id.imageView_userImagePU);
        textViewName = (TextView)view.findViewById(R.id.textView_userNamePU);
        textViewEmail = (TextView)view.findViewById(R.id.textView_userEmailPU);
        textViewPhone = (TextView)view.findViewById(R.id.textView_userPhonePU);
        textViewUbication = (TextView)view.findViewById(R.id.textView_userUbicationPU);
        textViewEventRealized = (TextView)view.findViewById(R.id.textView_totalEventsPU);
        textViewEventsHistorial = (TextView)view.findViewById(R.id.textView_eventsHistorialPU);
        ratingBarUser = (RatingBar)view.findViewById(R.id.ratingBar);

        Picasso.with(getContext()).load(user.getPhoto()).error(R.drawable.login).transform(new CircleTransform()).into(imageViewUser);
        textViewName.setText(user.getName());
        textViewEmail.setText(user.getEmail());
        textViewPhone.setText(user.getPhone());
        textViewUbication.setText(user.getLocale());
        textViewEventRealized.setText(String.valueOf(user.getOrganizedEvents()));
        ratingBarUser.setRating(user.getScore());
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

}
