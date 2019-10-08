package net.sprd.gwt.server.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Callback;

import net.sprd.gwt.shared.data.Item;
import net.sprd.gwt.shared.data.TestPageData;
import net.sprd.gwt.shared.page.data.PageDataLoader;
import net.sprd.gwt.shared.state.TestPageState;

public class TestPageDataLoader extends PageDataLoader<TestPageState, TestPageData> {
    
    private static TestPageDataLoader INSTANCE = new TestPageDataLoader();
    
    public static TestPageDataLoader getInstance() {
        return INSTANCE;
    }

    @Override
    public void load(TestPageState pageState, Callback<TestPageData, String> callback) {
        callback.onSuccess(load(pageState));
    }
    
    public TestPageData load(TestPageState pageState) {
        TestPageData testPageData = new TestPageData();
        testPageData.setItems(loadSearchResults(pageState.getQuery()));
        return testPageData;
    }
    
    public List<Item> loadSearchResults(String query) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>();
        }
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            item.setName(query + "-" + (i + 1));
            item.setImageUrl("https://via.placeholder.com/200x200?text="+query + "-" + (i + 1));
            items.add(item);
        }
        return items;
    }

}
