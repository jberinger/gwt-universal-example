package net.sprd.gwt.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elemental2.core.Global;
import elemental2.core.JsArray;
import jsinterop.base.JsPropertyMap;

public class Conversion {

    @SuppressWarnings("unchecked")
    public static <T> List<T> asList(T... values) {
        List<T> list = new ArrayList<>(values.length);
        for (T value:values) {
            list.add(value);
        }
        return list;
    }
    
    private static <T> Map<String, T> asMap(JsPropertyMap<T> value) {
        Map<String, T> map = new HashMap<>();
        value.forEach(key -> map.put(key, value.get(key)));
        return map;
    }
    
    @SuppressWarnings("unchecked")
    private static <T> JsPropertyMap<T> asJsMap(Map<String, T> map) {
        JsPropertyMap<T> jsMap = (JsPropertyMap<T>)JsPropertyMap.of();
        for (String key :map.keySet()) {
            jsMap.set(key, map.get(key));
        }
        return jsMap;
    }
    
    public static <T> T parseJson(String json) {
        return parseJson(json, null, null);
    }
    
    public static <T> String jsonStringify(T object) {
        return jsonStringify(object, null, null);
    }
    
    
    public static <T> T parseJson(String json, List<String> listKeys) {
        return parseJson(json, listKeys, null);
    }
    
    public static <T> String jsonStringify(T object, List<String> listKeys) {
        return jsonStringify(object, listKeys, null);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T parseJson(String json, List<String> listKeys, List<String> mapKeys) {
        Object object = Global.JSON.parse(json, (key,value) -> {
            if (listKeys != null && JsArray.isArray(value) && listKeys.contains(key)) {
                value = Conversion.asList((Object[])value);
            }
            if (mapKeys != null && mapKeys.contains(key)) {
                value = Conversion.asMap((JsPropertyMap<?>)value);
            }
            return value;
        });
        return (T)object;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> String jsonStringify(T object, List<String> listKeys, List<String> mapKeys) {
        return Global.JSON.stringify(object, (key,value) -> {
            if (listKeys != null && listKeys.contains(key) && value instanceof List) {
                value = ((List<?>)value).toArray();
            }
            if (mapKeys != null && mapKeys.contains(key) && value instanceof Map) {
                value = Conversion.asJsMap((Map<String, ?>)value);
            }
            return value;
        });
    }

}
