package Homepage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.socialapp.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Authentication.FetchData;
import Authentication.PostService;
import POJO.PostsResponse;
import POJO.Response;

public class HomepageFragment extends Fragment {

    public static final String Title = "Homepage";
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    HomepageAdapter adapter;
    PostService postService = new PostService();


    public static HomepageFragment newInstance(){
        return  new HomepageFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, null);
        adapter = new HomepageAdapter(getActivity());


        ExtendedFloatingActionButton fab = view.findViewById(R.id.addPost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPost.class);
                startActivity(intent);

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.home);
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setRefreshing(true);
        refreshData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void refreshData(){
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        FetchData fetchData = new FetchData() {
            @Override
            public void ProcessData(Response response) {
                adapter.setData(response.postsResponse);
                refreshLayout.setRefreshing(false);
                Log.d("TAG", "ProcessData: ");
                adapter.notifyDataSetChanged();
            }
        };

        postService.getPosts(getActivity(), null , fetchData, "/post");
    }
}
