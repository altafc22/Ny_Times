package gq.altafchaudhari.nytimes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import gq.altafchaudhari.nytimes.BuildConfig;
import gq.altafchaudhari.nytimes.R;
import gq.altafchaudhari.nytimes.activities.DetailsActivity;
import gq.altafchaudhari.nytimes.adapters.PopularListAdapter;
import gq.altafchaudhari.nytimes.base.BaseFragment;
import gq.altafchaudhari.nytimes.models.Response;
import gq.altafchaudhari.nytimes.utils.MostPopularDataRequest;
import gq.altafchaudhari.nytimes.utils.RecyclerviewItemDecoration;
import gq.altafchaudhari.nytimes.utils.Utils;
import io.reactivex.disposables.Disposable;

public class PopularListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = PopularListFragment.class.getSimpleName();

    @Nullable
    private View errorLayout;
    @Nullable
    private Disposable disposable;
    @Nullable
    private MostPopularDataRequest mostPopularDataRequest;
    @Nullable
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    private RecyclerView recyclerView;
    @Nullable
    private PopularListAdapter popularListAdapter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this._context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_popular_list, container, false);
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onRefresh();
    }

    private void initViews(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recycler_view);

        popularListAdapter = new PopularListAdapter(_context, newsItem -> DetailsActivity.start(_context, newsItem));
        recyclerView.setAdapter(popularListAdapter);

        recyclerView.addItemDecoration(new RecyclerviewItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_micro)));
        recyclerView.setLayoutManager(new LinearLayoutManager(_context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = view.findViewById(R.id.xSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        errorLayout = view.findViewById(R.id.layout_error);
        Button buttonRetry = view.findViewById(R.id.btn_retry);

        buttonRetry.setOnClickListener(v -> onRefresh());

        initRequest();
    }

    private void initRequest() {
        if (mostPopularDataRequest == null) {
            mostPopularDataRequest = new MostPopularDataRequest();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        popularListAdapter = null;
        swipeRefreshLayout = null;
        recyclerView = null;
    }

    @Override
    public void onRefresh() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onRefresh() called");
        }
        showProgress(true);

        initRequest();

        disposable = mostPopularDataRequest.getMostPopularArticles()
                .subscribe(this::updateItems,
                        this::handleError);
    }

    private void showProgress(boolean shouldShow) {
        swipeRefreshLayout.setRefreshing(shouldShow);
        Utils.setVisible(recyclerView, !shouldShow);
        Utils.setVisible(errorLayout, !shouldShow);
    }

    private void updateItems(@Nullable Response response) {
        if (popularListAdapter != null)
            popularListAdapter.replaceItems(response.getResults());

        Utils.setVisible(recyclerView, true);
        swipeRefreshLayout.setRefreshing(false);
        Utils.setVisible(errorLayout, false);
    }

    private void handleError(Throwable th) {
        if (Utils.isDebug()) {
            Log.e(TAG, th.getMessage(), th);
        }
        Utils.setVisible(errorLayout, true);
        swipeRefreshLayout.setRefreshing(false);
        Utils.setVisible(recyclerView, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        showProgress(false);
        Utils.disposeSafe(disposable);
        disposable = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (popularListAdapter != null && popularListAdapter.getItemCount() > 0) {
            Utils.setVisible(errorLayout, false);
        }
    }
}