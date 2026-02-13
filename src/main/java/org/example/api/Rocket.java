package org.example.api;

public class Rocket {
    private double baseMass;
    private double fuelMass;
    private double maxFuelStorage;
    private double specificImpulse;
    private Fuel propellant;

    public Rocket(double baseMass, double maxFuelStorage, double specificImpulse) {
        this.baseMass = baseMass;
        this.maxFuelStorage = maxFuelStorage;
        this.specificImpulse = specificImpulse;
        this.fuelMass = 0;
    }

    public double getBaseMass() {
        return baseMass;
    }

    public void setBaseMass(double baseMass) {
        this.baseMass = baseMass;
    }

    public double getMaxFuelStorage() {
        return maxFuelStorage;
    }

    public void setMaxFuelStorage(double maxFuelStorage) {
        this.maxFuelStorage = maxFuelStorage;
    }

    public double getSpecificImpulse() {
        return specificImpulse;
    }

    public void setSpecificImpulse(double specificImpulse) {
        this.specificImpulse = specificImpulse;
    }

    public Fuel getPropellant() {
        return propellant;
    }

    public void setPropellant(Fuel propellant) {
        this.propellant = propellant;
    }

    public double getFuelMass() {
        return fuelMass;
    }

    public void setFuelMass(double fuelMass) {
        this.fuelMass = fuelMass;
    }

    public void addFuel(double volume) {
        if ( getMaxFuelStorage() - getFuelMass() < volume) {
            System.out.println("Not able to add fuel - Not enough space");
            return;
        }
        this.fuelMass += volume;
    }

    public double getTotalMass() {
        return fuelMass + baseMass;
    }
}
