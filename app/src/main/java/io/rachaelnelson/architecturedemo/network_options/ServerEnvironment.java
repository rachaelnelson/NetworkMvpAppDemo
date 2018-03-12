package io.rachaelnelson.architecturedemo.network_options;

import io.rachaelnelson.networklibrary.BaseServerEnvironment;

/**
 * Server environments to hold sandbox (QA), stage, and production server environments.
 */
public class ServerEnvironment extends BaseServerEnvironment {

    @Override
    public String getProdUrl() {
        return "https://api.github.com";
    }

    @Override
    public String getStageUrl() {
        return "https://api.github.com";
    }

    @Override
    public String getQaUrl() {
        return "https://api.github.com";
    }
}