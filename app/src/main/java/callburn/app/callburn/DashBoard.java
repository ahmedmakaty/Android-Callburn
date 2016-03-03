package callburn.app.callburn;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import callburn.app.callburn.DataModels.Group;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoard extends Fragment {

    private ListView list;
    private MyGroupAdapter gAdapter;
    private List<Group> groups = new ArrayList<Group>();
    private List<String> names = new ArrayList<String>();
    private RecyclerView recyclerView;
    private DashboardAdapter myAdapter;
    View view;
    RelativeLayout empty;

    public DashBoard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        setHasOptionsMenu(true);

        groups.add(new Group("Friends"));
        groups.add(new Group("Msh 2wy"));
        groups.add(new Group("Wla 3la balo"));
        groups.add(new Group("bs b2a msh kda"));
        groups.add(new Group("With benefits"));

        empty = (RelativeLayout) view.findViewById(R.id.noMessage);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(groups.size() > 0){
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        //Toast.makeText(getContext(), "A7a", Toast.LENGTH_SHORT).show();
        //list = (ListView) view.findViewById(R.id.list);



        myAdapter = new DashboardAdapter(getContext(), groups);
        recyclerView.setAdapter(myAdapter);

        //adapter = new MyGroupAdapter(getContext(),names);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                inflater.getContext(), android.R.layout.simple_list_item_1,
//                names);
//        list.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.compose_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigate) {
            Intent i = new Intent(getActivity(), GetContacts.class);
            i.putExtra("REQUESTTYPE","new");
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
