package ru.zipal.bitrix.api.serialize;

import ru.zipal.bitrix.api.BitrixApiException;
import ru.zipal.bitrix.api.common.BitrixEnum;
import ru.zipal.bitrix.api.common.FieldName;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.zipal.bitrix.api.model.enums.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@SuppressWarnings("unchecked")
public class Serializer {
    private static final ConvertUtilsBean convertUtils = new ConvertUtilsBean();
    private static final BeanUtilsBean beanUtils = new BeanUtilsBean(convertUtils);

    private final String prefix;

    public Serializer() {
        this("fields");
    }

    public Serializer(String prefix) {
        this.prefix = prefix;
    }

    static {
        convertUtils.register(new BitrixEnumConverter(OwnerType.class), OwnerType.class);
        convertUtils.register(new BitrixEnumConverter(ContentType.class), ContentType.class);
        convertUtils.register(new BitrixEnumConverter(ActivityDirection.class), ActivityDirection.class);
        convertUtils.register(new BitrixEnumConverter(ActivityPriority.class), ActivityPriority.class);
        convertUtils.register(new BitrixEnumConverter(ActivityType.class), ActivityType.class);
        convertUtils.register(new BitrixEnumConverter(CommunicationType.class), CommunicationType.class);
        convertUtils.register(new BitrixEnumConverter(YesNo.class), YesNo.class);
        convertUtils.register(new BitrixEnumConverter(ContactSource.class), ContactSource.class);
        convertUtils.register(new BitrixEnumConverter(ContactType.class), ContactType.class);
        convertUtils.register(new BitrixDateConverter(), Date.class);
    }

    public <T> List<T> deserializeArray(Class<T> clazz, JSONArray array) throws BitrixApiException {
        final List list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            final JSONObject item = array.getJSONObject(i);
            list.add(deserialize(clazz, item));
        }
        return list;
    }

    public <T> T deserialize(Class<T> clazz, JSONObject object) throws BitrixApiException {
        try {
            final T t = clazz.newInstance();
            for (Field field : getAllFields(clazz)) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                final String fieldName = determineName(field).toUpperCase();
                if (object.has(fieldName)) {
                    final Object value = object.get(fieldName);
                    if (value instanceof JSONArray) {
                        final ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                        final JSONArray array = (JSONArray) value;
                        beanUtils.setProperty(t, field.getName(), deserializeArray((Class) genericType.getActualTypeArguments()[0], array));
                    } else {
                        beanUtils.setProperty(t, field.getName(), value);
                    }
                }
            }

            return t;
        } catch (Exception e) {
            throw new BitrixApiException(e);
        }
    }

    private static List<Field> addAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null && !type.equals(type.getSuperclass())) {
            fields = addAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    private static List<Field> getAllFields(Class<?> type) {
        final ArrayList<Field> result = new ArrayList<>();
        addAllFields(result, type);
        return result;
    }

    public List<NameValuePair> serialize(Object what) {
        final List<NameValuePair> result = new ArrayList<>();
        for (Field field : getAllFields(what.getClass())) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            try {
                if (Collection.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    Collection values = (Collection) field.get(what);
                    if (values != null) {
                        int i = 0;
                        for (Object object : values) {
                            for (Field objectField : getAllFields(object.getClass())) {
                                addArrayField(result, determineName(field), i, determineName(objectField), getSimpleValue(objectField, object));
                            }
                        }
                    }
                } else {
                    addField(result, determineName(field), getSimpleValue(field, what));
                }
            } catch (IllegalAccessException ignored) {

            }
        }
        return result;
    }

    public void addField(List<NameValuePair> params, String name, String value) {
        if (value != null) {
            params.add(new BasicNameValuePair(prefix + "[" + name.toUpperCase() + "]", value));
        }
    }

    public void addArrayField(List<NameValuePair> params, String name, int index, String field, String value) {
        if (value != null) {
            params.add(new BasicNameValuePair(prefix + "[" + name.toUpperCase() + "][" + index + "][" + field.toUpperCase() + "]", value));
        }
    }

    private String determineName(Field field) {
        FieldName fieldName = field.getAnnotation(FieldName.class);
        if (fieldName != null) {
            return fieldName.value();
        } else {
            return field.getName();
        }
    }

    private String getSimpleValue(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        if (BitrixEnum.class.isAssignableFrom(field.getType())) {
            BitrixEnum value = (BitrixEnum) field.get(object);
            if (value != null) {
                return value.getId();
            }
        } else {
            Object value = field.get(object);
            if (value != null) {
                return value.toString();
            }
        }
        return null;
    }
}
