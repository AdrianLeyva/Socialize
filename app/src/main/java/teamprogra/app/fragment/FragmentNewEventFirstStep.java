package teamprogra.app.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import teamprogra.app.adapter.AdapterListViewRules;
import teamprogra.app.domain.Event;
import teamprogra.app.socialize.socialize.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentNewEventFirstStep.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentNewEventFirstStep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNewEventFirstStep extends Fragment implements View.OnClickListener,MapFragment.OnFragmentInteractionListener {

    private EditText editTextName;
    private Spinner spinnerCategory;
    private EditText editTextDescription;
    private ListView listViewRules;
    private EditText editTextRules;
    private Button buttonAddRules;
    private EditText editTextDate;
    private EditText editTextHour;
    private Button buttonNext;

    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentManager fragmentManager;
    private teamprogra.app.fragment.MapFragment mapFragment;

    private AdapterListViewRules adapterRules;
    private ArrayList<String> listRules;
    private Event event;
    private OnFragmentInteractionListener mListener;

    public FragmentNewEventFirstStep() {
        // Required empty public constructor
    }


    public static FragmentNewEventFirstStep newInstance() {
        FragmentNewEventFirstStep fragment = new FragmentNewEventFirstStep();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getActivity().getSupportFragmentManager();
        event = new Event();
        if(event.getRules() == null){
            listRules = new ArrayList<>();
        }else {
            listRules = event.getRules();
        }        adapterRules = new AdapterListViewRules(getActivity(),listRules);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_event_first_step, container, false);

        editTextName = (EditText)view.findViewById(R.id.textView_EventNameFS);
        spinnerCategory = (Spinner)view.findViewById(R.id.spinner_eventCategoryFS);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.event_category,R.layout.support_simple_spinner_dropdown_item);
        editTextDescription = (EditText)view.findViewById(R.id.editText_eventDescriptionFS);
        listViewRules = (ListView)view.findViewById(R.id.listView_rulesFS);
        editTextRules = (EditText)view.findViewById(R.id.editText_addRulesFS);
        buttonAddRules = (Button)view.findViewById(R.id.button_addRulesFS);
        buttonAddRules.setOnClickListener(this);
        editTextDate = (EditText)view.findViewById(R.id.editText_dateFS);
        editTextHour = (EditText)view.findViewById(R.id.editText_hourFS);
        buttonNext = (Button)view.findViewById(R.id.button_nextFS);
        buttonNext.setOnClickListener(this);

        editTextName.setText(event.getName());
        spinnerCategory.setAdapter(adapter);
        editTextDescription.setText(event.getDescription());
        listViewRules.setAdapter(adapterRules);
        editTextDate.setText(event.getDate());
        editTextDate.setOnClickListener(this);
        editTextHour.setText(event.getHour());
        editTextHour.setOnClickListener(this);
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
            case R.id.button_addRulesFS:
                Log.i("tag","Si entra al metodo");
                listRules.add(editTextRules.getText().toString());
                adapterRules.notifyDataSetChanged();
                break;

            case R.id.button_nextFS:
                fragmentTransaction = fragmentManager.beginTransaction();
                mapFragment = teamprogra.app.fragment.MapFragment.newInstance();
                fragmentTransaction.replace(R.id.container,mapFragment);
                fragmentTransaction.commit();
                break;

            case R.id.editText_dateFS:
                DialogFragment dp = new DatePartyPickerFragment();
                dp.show(getActivity().getFragmentManager(),"DATEPICKER");
                break;

            case R.id.editText_hourFS:
                DialogFragment tp = new TimePartyPickerFragment();
                tp.show(getActivity().getFragmentManager(),"TIMEPICKER");
                break;
            default:
                break;
        }
    }

    public void setEvent(Event event){
        this.event = event;
    }

    public Event getEvent(){
        return this.event;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
