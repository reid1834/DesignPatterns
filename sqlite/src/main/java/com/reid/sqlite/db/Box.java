package com.reid.sqlite.db;

public class Box<T> {
    private T mObject;

    public T getmObject() {
        return mObject;
    }

    public void setmObject(T mObject) {
        this.mObject = mObject;
    }

    public void write(int a, String[] b) {

    }

    public void write(int a, int b) {

    }

    public void write(String b, float d) {

    }

    public <T> void write(T e, T f) {

    }

    interface pair<K,V> {
        public K getKey();
        public V getValue();
    }

    class Mpair<K,V> implements pair<K, V> {
        private K key;
        private V value;

        public Mpair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return null;
        }

        @Override
        public V getValue() {
            return null;
        }
    }

    pair A = new Mpair("String", 8);
    pair B = new Mpair("String", "String");
    pair<String, Integer> C = new Mpair<String, Integer>("fff", 89);
    pair<String, Float> d = new Mpair<String, Float>("sss", 2f);
    pair<String, Box<Integer>> e = new Mpair<String, Box<Integer>>("jjj", new Box<Integer>());

    public static <K, V> boolean compare(pair<K, V> p1, pair<K, V> p2) {
        if (p1.getKey().equals(p2.getKey())
                && p1.getValue().equals(p2.getValue())) {
            return true;
        } else {
            return false;
        }
    }

    pair<Integer, String> p1 = new Mpair<>(1, "a");
    pair<Integer, String> p2 = new Mpair<>(2, "b");

    boolean same = compare(p1, p2);

}
