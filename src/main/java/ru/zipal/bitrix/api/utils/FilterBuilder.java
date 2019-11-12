package ru.zipal.bitrix.api.utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FilterBuilder
{
    private Map<String, String> values = new HashMap<>();

    public void put(String key, String value)
    {
        values.put(key, value);
    }

    public JSONObject build()
    {
        JSONObject filterParams = new JSONObject();
        values.entrySet().stream()
            .forEach(entry ->
            {
                filterParams.put(entry.getKey(), entry.getValue());
            });
        JSONObject filter = new JSONObject();
        filter.put("filter", filterParams);
        return filter;
    }
}
