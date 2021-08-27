package com.testforme.newregistr.stuff.observableStuff.observableArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.your_teachers.trafficrules.stuff.observableStuff.observableArrayList.ObservableArrayListListener;

import java.util.ArrayList;
import java.util.Collection;

public class ObservableArrayList<T> extends ArrayList<T> implements Parcelable {

    private ObservableArrayListListener<T> listener;

    public ObservableArrayList() {
        super();
    }

    public ObservableArrayList(int initialCapacity, ObservableArrayListListener<T> listener) {
        super(initialCapacity);
        this.listener = listener;
    }

    public ObservableArrayList(ObservableArrayListListener<T> listener) {
        this.listener = listener;
    }

    public ObservableArrayList(@NonNull Collection<? extends T> c, ObservableArrayListListener<T> listener) {
        super(c);
        this.listener = listener;
    }

//    public ObservableArrayListListener getListener() {
//        return listener;
//    }

    public void setListener(ObservableArrayListListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public T set(int index, T element) {
        T t = super.set(index, element);
        if(t !=null){
            listener.set();
            listener.inAll();
            return t;
        } else {
            return null;
        }

    }

    @Override
    public boolean add(T t) {
        if(super.add(t)){
            listener.add(t);
            listener.inAll();
            return true;
        } else  {
            return false;
        }
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
        listener.add(element);
        listener.inAll();
    }

    @Override
    public T remove(int index) {
        T t = super.remove(index);
        if(t !=null){
            listener.remove();
            listener.inAll();
            return t;
        } else {
            return null;
        }
    }

    @Override
    public boolean remove(@Nullable Object o) {
        if(super.remove(o)){
            listener.remove();
            listener.inAll();
            return true;
        } else  {
            return false;
        }
    }

    @Override
    public void clear() {
        super.clear();
        listener.clear();
        listener.inAll();
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        if(super.addAll(c)){
            listener.addAll();
            listener.inAll();
            return true;
        } else  {
            return false;
        }
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        if(super.addAll(index, c)){
            listener.addAll();
            listener.inAll();
            return true;
        } else  {
            return false;
        }
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        listener.removeRange();
        listener.inAll();
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        if(super.removeAll(c)){
            listener.removeRange();
            listener.inAll();
            return true;
        } else  {
            return false;
        }
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        if(super.retainAll(c)){
            listener.retainAll();
            listener.inAll();
            return true;
        } else  {
            return false;
        }
    }

    //Parcelable
    protected ObservableArrayList(Parcel in) {
    }

    public static final Creator<ObservableArrayList> CREATOR = new Creator<ObservableArrayList>() {
        @Override
        public ObservableArrayList createFromParcel(Parcel in) {
            return new ObservableArrayList(in);
        }

        @Override
        public ObservableArrayList[] newArray(int size) {
            return new ObservableArrayList[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}