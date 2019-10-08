package net.sprd.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;

import net.sprd.gwt.client.data.TestPageDataLoader;
import net.sprd.gwt.shared.TestApp;
import net.sprd.gwt.shared.version.GwtuTestVersion;

/**
 */
public class GwtUniversalEntryPoint implements EntryPoint {
    
    @Override
    public void onModuleLoad() {
        GWT.log("start Gwtu TestApp in Version "+GwtuTestVersion.VERSION);
        GwtuClient.startApp(new TestApp(), new TestPageDataLoader(), "http://127.0.0.1:4242/page/");
    }
    
}
