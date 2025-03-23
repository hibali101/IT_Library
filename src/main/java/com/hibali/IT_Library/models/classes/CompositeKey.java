package com.hibali.IT_Library.models.classes;

import java.util.Objects;

public class CompositeKey {
    private int keyPart1;
    private int keyPart2;

    public CompositeKey() {
    }

    public CompositeKey(int keyPart1, int keyPart2) {
        this.keyPart1 = keyPart1;
        this.keyPart2 = keyPart2;
    }

    public int getKeyPart1() {
        return keyPart1;
    }

    public void setKeyPart1(int keyPart1) {
        this.keyPart1 = keyPart1;
    }

    public int getKeyPart2() {
        return keyPart2;
    }

    public void setKeyPart2(int keyPart2) {
        this.keyPart2 = keyPart2;
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || object.getClass() != this.getClass()) return false;
        CompositeKey other = (CompositeKey) object;
        return this.keyPart1 == other.keyPart1 && this.keyPart2 == other.keyPart2;
    }

    @Override
    public int hashCode(){
        return Objects.hash(keyPart1,keyPart2);
    }

    @Override
    public String toString(){
        return keyPart1+"_"+keyPart2;
    }
}
