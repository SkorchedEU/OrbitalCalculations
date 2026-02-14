package org.example;

import org.example.api.Fuel;
import org.example.api.Rocket;
import org.example.api.SpaceBody;
import org.example.api.SystemCenter;

public class OrbitalCalculator {
    static final double gravitationalConstant = 6.674 * Math.pow(10, -11);
    /**
     * Calculates the Effective Exhaust Velocity (v_e) based on the rocket and body to launch from
     * @param launchBody The Celestial Body from the surface of which to launch
     * @param rocket The rocket being used to calculate
     * @return Effective Exhaust Velocity (v_e)
     */
    public static double calculateEffectiveExhaustVelocity(SpaceBody launchBody, Rocket rocket) {
        return rocket.getPropellant().getSpecificImpulse() *
                (launchBody.getMass() * gravitationalConstant) / Math.pow(launchBody.getRadius(), 2);
    }

    /**
     * Calculates the Maximum DeltaV of the rocket provided launching from a given body
     * @param launchBody The Celestial Body from the surface of which to launch
     * @param rocket The rocket being used to calculate
     * @return Maximum Delta V achievable
     */
    public static double calculateMaxDeltaVelocity(SpaceBody launchBody, Rocket rocket) {
        final double effectiveExhaustVelocity = calculateEffectiveExhaustVelocity(launchBody, rocket);
        final double totalMass = rocket.getTotalMass();
        final double dryMass = rocket.getBaseMass();

        return effectiveExhaustVelocity * Math.log(totalMass / dryMass);
    }

    /**
     * Calculates the DeltaV required to enter elliptical orbit (stage 1)
     * @param launchBody The Celestial Body for starting orbit
     * @param centerBody The Celestial Body from which main source of Gravity in system
     * @param targetBody The Celestial Body for arrival orbit
     * @param startRadius The starting orbital radius (surface height for base launch)
     * @return DeltaV required for stage 1 of orbit transfer
     */
    public static double calculateHohmannV1(SpaceBody launchBody, SpaceBody targetBody, SystemCenter centerBody,
                                            double startRadius) {
        /*
         * Most of this is renaming readable variables to fit standard notation for calculation, and legibility
         */
        // GM of the center body
        final double mu_s = centerBody.getMass() * gravitationalConstant;

        //GM of departure body
        final double mu_1 = launchBody.getMass() * gravitationalConstant;

        // Distance from center body to launch body
        final double r_1 = launchBody.getSystemRadius();

        // Distance from center body to arrival body
        final double r_2 = targetBody.getSystemRadius();

        // Radius of original orbit
        final double a_1 = startRadius;

        final double v = Math.sqrt( Math.pow((Math.sqrt((2*mu_s*r_2)/(r_1 * (r_1+r_2))) - Math.sqrt(mu_s / r_1)),2)
                + (2*mu_1)/ a_1 ) - Math.sqrt(mu_1/a_1);

        System.out.print("Exit deltaV: ");
        System.out.println(v);

        return v;
    }

    /**
     * Calculates the DeltaV required to correct elliptical orbit into circular (stage 2)
     * @param centerBody The Celestial Body from which gravity is mainly felt
     * @param launchBody The Celestial Body for starting orbit
     * @param targetBody The Celestial Body for arrival orbit
     * @param endRadius The final orbital radius
     * @return DeltaV required for stage 2 of orbit transfer
     */
    public static double calculateHohmannV2(SpaceBody launchBody, SpaceBody targetBody, SystemCenter centerBody,
                                            double endRadius) {

        /*
         * Most of this is renaming readable variables to fit standard notation for calculation, and legibility
         * mu_s
         * mu_2
         * r_1
         * r_2
         * a_2
         */

        // GM of center body
        final double mu_s =  centerBody.getMass() * gravitationalConstant;

        // GM of target body
        final double mu_2 = targetBody.getMass() * gravitationalConstant;

        // Distance from center body to launch body
        final double r_1 = launchBody.getSystemRadius();

        // Distance from center body to target body
        final double r_2 = targetBody.getSystemRadius();

        // orbital height for target body
        final double a_2 = endRadius;

        final double v = Math.sqrt( (Math.pow((Math.sqrt((2*mu_s*r_1)/(r_2 * (r_1+r_2))) - Math.sqrt(mu_s / r_2)),2)
                + (2*mu_2)/ a_2) ) - Math.sqrt(mu_2/a_2);



        System.out.print("Enter deltaV: ");
        System.out.println(v);

        return v;

    }

    /**
     * Combines the two stages of Hohmann Transfer
     * @param launchBody The Celestial Body from first orbit
     * @param targetBody The Celestial Body for final orbit
     * @param centerBody The Celestial Body providing main gravitational pull in system (i.e. star etc.)
     * @param startRadius The starting circular radius of orbit
     * @param endRadius The final circular radius target
     * @return Total Delta V required to alter orbital radius
     */
    public static double calculateHohmannVelocity(SpaceBody launchBody, SpaceBody targetBody, SystemCenter centerBody,
                                                  double startRadius, double endRadius) {
        final double v_1 = calculateHohmannV1(launchBody, targetBody, centerBody, startRadius);
        final double v_2 = calculateHohmannV2(launchBody, targetBody, centerBody, endRadius);
        return  Math.round((v_1+ v_2)*100) / 100.0;
    }

    public static double calculateRequiredFuelVolume(SpaceBody launchBody, Rocket rocket, double deltaV) {
        final double effectiveExhaust = calculateEffectiveExhaustVelocity(launchBody, rocket);
        return rocket.getBaseMass() * ( Math.exp(deltaV/effectiveExhaust) - 1);
    }

    public static double calculateEscapeVelocity(SpaceBody launchBody) {
        return Math.sqrt( (2 * gravitationalConstant * launchBody.getMass()) / launchBody.getRadius());
    }

    /**
     * Calculates the DeltaV required to escape gravitational pull of launch body, and land safely on target body
     * @param launchBody The Celestial Body from which to launch
     * @param targetBody The Celestial Body at which to arrive
     * @return The total deltaV required to
     */
    public static double calculateDirectDeltaV(SpaceBody launchBody, SpaceBody targetBody) {
        return calculateEscapeVelocity(launchBody) + calculateEscapeVelocity(targetBody);
    }
}


