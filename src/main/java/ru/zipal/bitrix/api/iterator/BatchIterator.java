package ru.zipal.bitrix.api.iterator;

import ru.zipal.bitrix.api.BitrixApiException;
import ru.zipal.bitrix.api.BitrixPage;

import java.util.Iterator;

public class BatchIterator<T> implements Iterator<T> {
    private final BitrixFunction<Integer, BitrixPage<T>> loadFunction;
    private BitrixPage<T> currentPage;
    private Iterator<T> currentIterator;

    public BatchIterator(BitrixFunction<Integer, BitrixPage<T>> loadFunction) {
        this.loadFunction = loadFunction;
    }

    @Override
    public boolean hasNext() {
        try {
            return hasNextInternal();
        } catch (BitrixApiException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasNextInternal() throws BitrixApiException {
        if (currentIterator != null && currentIterator.hasNext()) {
            return true;
        }
        if (currentPage == null) {
            currentPage = loadFunction.apply(null);
            currentIterator = currentPage.getList().iterator();
        } else if (currentPage.getNext() != null) {
            currentPage = loadFunction.apply(currentPage.getNext());
            currentIterator = currentPage.getList().iterator();
        }
        return currentIterator.hasNext();
    }

    @Override
    public T next() {
        return currentIterator.next();
    }

    public interface BitrixFunction<T, R> {
        R apply(T t) throws BitrixApiException;
    }
}
