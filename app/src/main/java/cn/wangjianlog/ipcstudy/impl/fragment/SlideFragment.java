package cn.wangjianlog.ipcstudy.impl.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wangjianlog.ipcstudy.R;
import cn.wangjianlog.ipcstudy.view.ScrollerLayout;

public class SlideFragment extends Fragment {

    private ScrollerLayout scroller_layout;

    public SlideFragment() {

    }

    public static SlideFragment newInstance() {
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initView(View view){
        scroller_layout = view.findViewById(R.id.scroller_layout);
        view.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroller_layout.startScroll(0,-200);
            }
        });
    }

}
