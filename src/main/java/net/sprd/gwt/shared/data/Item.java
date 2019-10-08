package net.sprd.gwt.shared.data;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class Item {
    
    private String name;
    private String imageUrl;
    
    @JsOverlay
    public final String getName() {
        return name;
    }
    @JsOverlay
    public final void setName(String name) {
        this.name = name;
    }
    @JsOverlay
    public final String getImageUrl() {
        return imageUrl;
    }
    @JsOverlay
    public final void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
