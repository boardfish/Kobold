package com.example.kobold;

public abstract class Soundpack {
    int[] resources;
    int pitchOffset;
    int pitchVariation;
    int speedOffset;
    int speedVariation;

    public Soundpack(int[] resources, int pitchOffset, int pitchVariation, int speedOffset, int speedVariation) {
        this.resources = resources;
        this.pitchOffset = pitchOffset;
        this.pitchVariation = pitchVariation;
        this.speedOffset = speedOffset;
        this.speedVariation = speedVariation;
    }

    public int getPitchOffset() {
        return pitchOffset;
    }

    public void setPitchOffset(int pitchOffset) {
        this.pitchOffset = pitchOffset;
    }

    public int getPitchVariation() {
        return pitchVariation;
    }

    public void setPitchVariation(int pitchVariation) {
        this.pitchVariation = pitchVariation;
    }

    public int getSpeedOffset() {
        return speedOffset;
    }

    public void setSpeedOffset(int speedOffset) {
        this.speedOffset = speedOffset;
    }

    public int getSpeedVariation() {
        return speedVariation;
    }

    public void setSpeedVariation(int speedVariation) {
        this.speedVariation = speedVariation;
    }
}
