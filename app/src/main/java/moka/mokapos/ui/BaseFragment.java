package moka.mokapos.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import moka.mokapos.R;

public abstract class BaseFragment extends Fragment {

    private OnHeaderBackClickListener listener;
    public interface OnHeaderBackClickListener{
        void onHeaderBackClicked();
    }

    abstract boolean shouldShowBack();

    abstract int getHeaderTitle();

    abstract View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances);

    public void setOnHeaderBackClickListener(OnHeaderBackClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    final public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getView(inflater, container, savedInstanceState);
        if(view != null && view.findViewById(R.id.header_back) != null){
            ((TextView)(view.findViewById(R.id.header_title))).setText(getHeaderTitle());
            View back  = view.findViewById(R.id.header_back);
            if(back != null)
            if(shouldShowBack()) {
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listener != null){
                            listener.onHeaderBackClicked();
                        }
                    }
                });
            } else back.setVisibility(View.GONE);
        }

        return view;
    }
}
