package moka.mokapos.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moka.mokapos.R;

public class LibraryFragment extends BaseFragment implements View.OnClickListener {

    private OnLeftPanelOptionClickListener listener;

    public interface OnLeftPanelOptionClickListener{
        void onOptionClicked(int id);
    }

    public void setOnOptionClickListener(OnLeftPanelOptionClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    boolean shouldShowBack() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.all_items).setOnClickListener(this);
        getView().findViewById(R.id.all_discounts).setOnClickListener(this);
    }

    @Override
    int getHeaderTitle() {
        return R.string.header_title_library;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        View view = inflater.inflate(R.layout.library_layout, null);
        return view;
    }

    @Override
    public void onClick(View view) {
     if(listener != null){
         listener.onOptionClicked(view.getId());
     }
    }
}
