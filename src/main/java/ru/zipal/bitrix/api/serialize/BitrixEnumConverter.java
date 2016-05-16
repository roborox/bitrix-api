package ru.zipal.bitrix.api.serialize;

import ru.zipal.bitrix.api.common.BitrixEnum;
import org.apache.commons.beanutils.Converter;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class BitrixEnumConverter<T extends BitrixEnum> implements Converter {
    private final Map<String, T> map = new HashMap<>();

    public BitrixEnumConverter(Class<T> clazz) {
        try {
            for (T t : ((T[]) clazz.getMethod("values").invoke(null))) {
                map.put(t.getId(), t);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T convert(Class type, Object value) {
        return map.get(value);
    }
}
