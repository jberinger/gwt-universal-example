package net.sprd.gwt.shared.data;

import java.util.List;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import net.sprd.gwt.shared.page.data.PageData;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class TestPageData implements PageData {
    
    private List<Item> items;
    
    @JsOverlay
    public final List<Item> getItems() {
        return items;
    }

    @JsOverlay
    public final void setItems(List<Item> items) {
        this.items = items;
    }
    

}
