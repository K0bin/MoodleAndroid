package k0bin.moodle.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsCallback;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import k0bin.moodle.R;
import k0bin.moodle.model.LoginRequest;
import k0bin.moodle.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {
    @Nullable
    private LoginViewModel viewModel;

    @Nullable
    private WebView webView;

    @Nullable
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.getIsDone().observe(this, it -> {
            if (navController == null) {
                return;
            }
            navController.navigate(R.id.action_login_done);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        webView = view.findViewById(R.id.login_web_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                viewModel.setTokenUrl(url);
            }
        });

        viewModel.getLoginRequest()
                .observe(this, it -> {
                    if (it == null) {
                        return;
                    }
                    if (it instanceof LoginRequest.SsoLoginRequest) {
                        prepareSsoLogin(((LoginRequest.SsoLoginRequest) it).getLoginUrl());
                    }
                });
    }

    private void prepareSsoLogin(@NonNull String url) {
        if (webView == null) {
            return;
        }
        webView.loadUrl(url);
    }
}
