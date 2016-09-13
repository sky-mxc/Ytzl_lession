package com.skymxc.demo.lession_17_content_provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skymxc.demo.lession_17_content_provider.entity.Person;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class P1Adapter extends BaseAdapter {
   private   List<Person> persons;
    private LayoutInflater lif ;

    public P1Adapter(Context context,List<Person> persons ){
        this.persons = persons;
        lif= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return persons==null?0:persons.size();
    }

    @Override
    public Person getItem(int i) {
        return persons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return persons.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder ;
        if (view ==null){
            view = lif.inflate(R.layout.layout_list_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Person person = getItem(i);
        holder.tvDescribe.setText(person.describe);
        holder.tvTitle.setText(person.title);
        Bitmap bmp= BitmapFactory.decodeFile(person.path);
        holder.image.setImageBitmap(bmp);

        return view;
    }
    class ViewHolder {
        ImageView image;
        TextView tvTitle;
        TextView tvDescribe;
        public ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.image);
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvDescribe = (TextView) view.findViewById(R.id.describe);
        }
    }

    public void addAll(List<Person> persons){
        this.persons.clear();
        this.persons.addAll(persons);
        this.notifyDataSetChanged();
    }

}
