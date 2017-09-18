package com.github.markusbernhardt.springcontexttemplate;

import java.util.Map;

/**
 * Created by mehmet on 5/1/2017.
 */
public class Tuple<K, V> implements Map.Entry<K, V> {

    private K _k;
    private V _v;

    public Tuple(K _k, V _v) {
        this._k = _k;
        this._v = _v;
    }

    @Override
    public K getKey() {
        return _k;
    }

    @Override
    public V getValue() {
        return _v;
    }

    @Override
    public V setValue(V value) {
        _v = value;
        return _v;
    }
}
