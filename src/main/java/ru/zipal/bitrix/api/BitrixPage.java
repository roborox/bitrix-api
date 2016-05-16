package ru.zipal.bitrix.api;

import java.util.List;

public class BitrixPage<T> {
    private final Integer next;
    private final List<T> list;

    public BitrixPage(Integer next, List<T> list) {
        this.next = next;
        this.list = list;
    }

    public Integer getNext() {
        return next;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "BitrixPage{" +
                "next=" + next +
                '}';
    }
}
