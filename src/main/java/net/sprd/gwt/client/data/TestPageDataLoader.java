package net.sprd.gwt.client.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import elemental2.core.Global;
import elemental2.dom.DomGlobal;
import jsinterop.base.JsPropertyMap;
import net.sprd.gwt.shared.data.TestPageData;
import net.sprd.gwt.shared.page.data.PageDataLoader;
import net.sprd.gwt.shared.state.TestPageState;

public class TestPageDataLoader extends PageDataLoader<TestPageState, TestPageData> {
    
    private static final  List<String> listKeys;
    
    public static TestPageData parseTestPageData(String json) {
        TestPageData testPageData =  Conversion.parseJson(json, listKeys);
        return testPageData;
    }
    
    public static String stringifyTestPageData(TestPageData testPageData) {
        return Conversion.jsonStringify(testPageData, listKeys);
    }
    
    static {
        listKeys = new ArrayList<>();
        listKeys.add("items");
    }

    @Override
    public void load(TestPageState pageState, Callback<TestPageData, String> callback) {
        final String finalUrl = "http://127.0.0.1:4242/page/data?query="+pageState.getQuery();

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, finalUrl);

        try {
            builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    GWT.log("Couldn't retrieve JSON");
                }

                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        TestPageData pageData = parseTestPageData(response.getText());
                        callback.onSuccess(pageData);
                    } else {
                        GWT.log("Couldn't retrieve JSON (" + response.getStatusText() + ")");
                        callback.onFailure("Couldn't retrieve JSON (" + response.getStatusText() + ")");
                    }
                }
                
            });
        } catch (RequestException e) {
            GWT.log("Couldn't retrieve JSON");
        }
    }
    
    @SuppressWarnings("unchecked")
    public TestPageData getInitPageData() {
        return parseTestPageData(Global.JSON.stringify(((JsPropertyMap<Object>)DomGlobal.window).get("pageData")));
    }
    
}
