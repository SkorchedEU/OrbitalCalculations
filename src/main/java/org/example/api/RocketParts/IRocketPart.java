package org.example.api.RocketParts;

public interface IRocketPart {

    double getMass();

    PartTypeEnum getPartType();

    // Added as a potential Tier-skipping control
    int getTier();

    double getRadius();

    double getHeight();


}
