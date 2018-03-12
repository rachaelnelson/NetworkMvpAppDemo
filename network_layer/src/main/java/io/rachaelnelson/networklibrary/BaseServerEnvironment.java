package io.rachaelnelson.networklibrary;

import android.content.Context;
import android.preference.PreferenceManager;

public abstract class BaseServerEnvironment {

    public String defaultUrl = getProdUrl();

    public abstract String getProdUrl();
    public abstract String getStageUrl();
    public abstract String getQaUrl();

    private enum Server {
        PROD,
        STAGE,
        QA
    }

    public final String getEnvironment(Context context) {
        String environment = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("environment", defaultUrl);

        return environment;
    }

    public final void setEnvironment(Context context, Server server) {

        String url;
        switch (server) {
            case PROD:
                url = getProdUrl();
                break;
            case STAGE:
                url = getStageUrl();
                break;
            case QA:
                url = getQaUrl();
                break;
                default:
                    url = getProdUrl();
                    break;
        }

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("environment", url)
                .apply();
    }
}
