package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.UserTable;
import org.home.d2e.numbersprinter.adapter.UserCursorAdapter;


public class ResultsFragment extends Fragment {
    private final String TAG = "TAG_ResultListFragemnt ";
    private ListView lvAchievers;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userListCursor;
    private UserCursorAdapter userCursorAdapter;
    DataRetainFragment dataRetainFragment;

    public ResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init db
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getReadableDatabase();
        //get user list from db
        userListCursor = db.query(UserTable.TABLE, null, null, null, null, null, UserTable.Columns.SCORE_TOTAL + " DESC;");
        //if there are any users
        if (userListCursor.getCount() > 0) {
            // build adapter
            userCursorAdapter = new UserCursorAdapter(getActivity(), userListCursor);
            lvAchievers = (ListView) view.findViewById(R.id.lvAchievers);
            // attach listview to adapter
            lvAchievers.setAdapter(userCursorAdapter);
            //setting onFragmentListener on item in listview
           /***
            lvAchievers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //on click - getting info from cursor
                    Cursor cursor = (Cursor) lvAchievers.getItemAtPosition(position);
                    String name = cursor.getString(cursor.getColumnIndex(UserTable.Columns.NAME));
                    Toast.makeText(parent.getContext(), "Click to " + name, Toast.LENGTH_SHORT).show();


                }
            });
            */
        }
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if(dataRetainFragment!=null){
            dataRetainFragment.setCurrFragTag(MainActivity.RESULTS_FRAGMENT_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
