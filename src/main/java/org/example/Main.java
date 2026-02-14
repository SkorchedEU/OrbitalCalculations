package org.example;

import org.example.api.Fuel;
import org.example.api.Rocket;
import org.example.api.SpaceBody;
import org.example.api.SystemCenter;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Fuel diesel = new Fuel(39_600_000);
        Rocket rocket = new Rocket(10000,200_000, 300);
        SpaceBody earth = new SpaceBody(5.97 * Math.pow(10,24),
                6_371_000,
                1.496 * Math.pow(10,11));
        SpaceBody mars = new SpaceBody(6.417 * Math.pow(10,23),
                3_389_500,
                2.28 * Math.pow(10,11));
        SystemCenter sun = new SystemCenter(1.99 * Math.pow(10, 30));
        System.out.println("Calculating delta_v for Earth -> Mars");

        final double hohmannDeltaV = OrbitalCalculator.calculateHohmannVelocity(earth, mars, sun,
                earth.getRadius() + 300_000, mars.getRadius()+100_000);

        System.out.println("Hohmann deltaV total: " + hohmannDeltaV);

        final double requiredVolume = OrbitalCalculator.calculateRequiredFuelVolume(earth, rocket, hohmannDeltaV);
        System.out.println("Required volume: " + requiredVolume);


    }
}
