package callburn.app.callburn;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import callburn.app.callburn.DataModels.Template;


/**
 * A simple {@link Fragment} subclass.
 */
public class Templates extends Fragment {


    private RecyclerView recyclerView;
    private TemplatesAdapter myAdapter;
    View view;
    List<Template> templates = new ArrayList<Template>();
    FrameLayout empty;
    FloatingActionButton fap;

    public Templates() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_templates, container, false);
        setHasOptionsMenu(true);

        populateList();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_temp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        empty = (FrameLayout) view.findViewById(R.id.noMessage);
        fap = (FloatingActionButton) view.findViewById(R.id.fab);

        if(templates.size() > 0){
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        myAdapter = new TemplatesAdapter(getContext(), templates);
        recyclerView.setAdapter(myAdapter);

//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                //Remove swiped item from list and notify the RecyclerView
//                Toast.makeText(getContext(), "Swiped" + swipeDir, Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);

        fap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddTemplate.class);
                startActivity(i);
            }
        });


        return view;
    }

    private void populateList() {

        try {
            templates.add(new Template("Template1","Message1", false, "aad", 45000, "Fredsa"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            templates.add(new Template("Template2","Message12", false, "aad", 45000, "Fredsa"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            templates.add(new Template("Template3","Message14", false, "aad", 45000, "Fredsa"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            templates.add(new Template("Template4","Message17", false, "aad",45000,"Fredsa"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.compose_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
