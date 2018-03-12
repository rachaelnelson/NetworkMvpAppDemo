package io.rachaelnelson.architecturedemo;

import android.os.Bundle;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import io.rachaelnelson.architecturedemo.home.HomeControllerView;
import io.rachaelnelson.coreui.BaseActivity;

public class Activity extends BaseActivity {

    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        ViewGroup container = findViewById(io.rachaelnelson.coreui.R.id.controller_container);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new HomeControllerView()));
        }
    }
}
