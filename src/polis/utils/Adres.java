package polis.utils;

import polis.tiles.Residence;

/**
 * Een adres bevat de informatie over de thuislocatie van een bewoner.
 * Een adres bestaat uit de residentie waar hij woont, de thuislocatie van waaruit hij
 * vertrekt bij het verlaten van de residentie en zijn bewonersnummer.
 */
public class Adres {

    private final Residence home;
    private final RCCoordinate homeLocation;
    private final int residenceNumber;

    public Adres(Residence home, RCCoordinate homeLocation, int residenceNumber) {
        this.home = home;
        this.homeLocation = homeLocation;
        this.residenceNumber = residenceNumber;
    }

    public Residence getHome() {
        return home;
    }

    public RCCoordinate getHomeLocation() {
        return homeLocation;
    }

    public int getResidenceNumber() {
        return residenceNumber;
    }
}
