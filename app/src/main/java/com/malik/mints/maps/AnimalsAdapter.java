package com.malik.mints.maps;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.malik.mints.models.animal.Animal;

import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

public class AnimalsAdapter extends BaseAdapter
{

    private List<Animal> animals;
    private MapItemListener mapItemListener;

    public AnimalsAdapter(List<Animal> animals, MapItemListener itemListener) {
        setList(animals);
        mapItemListener = itemListener;
    }

    public void replaceData(List<Animal> animals) {
        setList(animals);
        notifyDataSetChanged();
    }

    private void setList(List<Animal> animals) {
        this.animals = checkNotNull(animals);
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Animal getItem(int i) {
        return animals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        /*if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            //rowView = inflater.inflate(R.layout.task_item, viewGroup, false);
        }

        final Map map = getItem(i);

        TextView titleTV = (TextView) rowView.findViewById(R.id.title);
        titleTV.setText(task.getTitleForList());

        CheckBox completeCB = (CheckBox) rowView.findViewById(R.id.complete);

        // Active/completed task UI
        completeCB.setChecked(task.isCompleted());
        if (task.isCompleted()) {
            rowView.setBackgroundDrawable(viewGroup.getContext()
                    .getResources().getDrawable(R.drawable.list_completed_touch_feedback));
        } else {
            rowView.setBackgroundDrawable(viewGroup.getContext()
                    .getResources().getDrawable(R.drawable.touch_feedback));
        }

        completeCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!task.isCompleted()) {
                    mItemListener.onCompleteTaskClick(task);
                } else {
                    mItemListener.onActivateTaskClick(task);
                }
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onTaskClick(task);
            }
        });*/

        return rowView;
    }

    public interface MapItemListener {

        void onMapClick(Animal clickedAnimal);


    }
}

