package com.lunaciadev.choreographer;

import org.junit.jupiter.api.extension.Extension;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class GdxExtension extends ApplicationAdapter implements Extension {
    /**
     * Construct a headless application to utilize libGDX calls. DO NOT TEST UI WITH THIS.
     */
    public GdxExtension() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(this, config);
    }
}