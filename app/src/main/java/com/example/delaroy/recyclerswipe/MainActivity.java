package com.example.delaroy.recyclerswipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.delaroy.recyclerswipe.R;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    int decremented = 0;
    private static final int TIME_TO_AUTOMATICALLY_DISMISS_ITEM = 300;
    Company company;
    Route route;
    int numberOfSeats;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i){
        decremented = i.getExtras().getInt("numberOfSeatsDecremented");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar t = (Toolbar)findViewById(R.id.toolbarmain);
        t.setTitle("Stops...");
        Intent previous = getIntent();
        company =(Company) previous.getSerializableExtra("Company");
        route = (Route) previous.getSerializableExtra("Route");
        numberOfSeats = route.getNumberOfSeats();
        init((RecyclerView) findViewById(R.id.recycler_view));
    }

    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        final MyBaseAdapter adapter = new MyBaseAdapter(route);
        recyclerView.setAdapter(adapter);
        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onPendingDismiss(RecyclerViewAdapter recyclerView, int position) {
                                Intent i = new Intent(MainActivity.this, OccupySeats.class);
                                i.putExtra("totalNumberOfSeats", numberOfSeats);
                                if(decremented>=numberOfSeats)
                                    i.putExtra("NumberOfSeatsAvailable",0);
                                else
                                    i.putExtra("NumberOfSeatsAvailable", numberOfSeats-decremented);
                                startActivityForResult(i, 10);
                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
                                adapter.remove(position);
                            }
                        });
        touchListener.setDismissDelay(TIME_TO_AUTOMATICALLY_DISMISS_ITEM);
        recyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        }
                    }
                }));

    }

    static class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.MyViewHolder> {

        List<String> mDataSet = new ArrayList<>();
        String[] stops = {"Stop 1","Stop 2","Stop 3","Stop 4", "Stop 5", "Stop 6"};

        public MyBaseAdapter() {
            for (int i = 0; i < stops.length; i++)
                mDataSet.add(i,stops[i]);
        }

        public MyBaseAdapter(Route route){
            mDataSet = route.getStops();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.dataTextView.setText(mDataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        public void remove(int position) {
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView dataTextView;
            MyViewHolder(View view) {
                super(view);
                dataTextView = (TextView) view.findViewById(R.id.txt_data);
            }
        }
    }

}