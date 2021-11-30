package polis.core;

import polis.actors.Actor;
import polis.buttons.ToolButton;
import polis.tiles.*;
import polis.utils.InfoVisitor;
import polis.utils.InfoVisitorAcceptor;
import polis.utils.RCCoordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
import static polis.utils.PropertyReader.POLIS_DIMENSION;

/**
 * Een stad met een wegensysteem, winkels, industrieën, woningen en actoren.
 * Een stad heeft een regio die immigranten in de stad binnenbrengt.
 * De impact van een actie op een stad is afhankelijk van de geselecteerde gereedschap in het gereedschapspaneel.
 * Een stad is een informatiemodel waarnaar de afbeelding van de stad kan luisteren.
 */
public class Polis extends InfoVisitorAcceptor {

    private final RoadSystem roadSystem = new RoadSystem(this);
    private final ArrayList<Commerce> commerces = new ArrayList<>();
    private final ArrayList<Industry> industries = new ArrayList<>();
    private final ArrayList<Residence> residences = new ArrayList<>();
    private final Region region = new Region(this);
    private final ArrayList<Actor> actors = new ArrayList<>();
    private ToolButton tool;

    public Polis() {
        IntStream.range(0, POLIS_DIMENSION/2-1)
                .forEach(row -> roadSystem.addRoad(new Road(new RCCoordinate(row, ((double) POLIS_DIMENSION)/2-1), false)));
        fireInvalidationEvent();
    }

    public void setTool(ToolButton tool) {
        this.tool = tool;
    }

    public void clickedAt(Map<RCCoordinate, Boolean> coordinates) {
        if (tool != null) {
            for (RCCoordinate coordinate : coordinates.keySet()) {
                if (coordinates.get(coordinate)) {
                    tool.handle(coordinate);
                }
            }
        }
        fireInvalidationEvent();
    }

    public RoadSystem getRoadSystem() {
        return roadSystem;
    }

    public ArrayList<Commerce> getCommerces() {
        return commerces;
    }

    public ArrayList<Industry> getIndustries() {
        return industries;
    }

    public ArrayList<Residence> getResidences() {
        return residences;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public Region getRegion() {
        return region;
    }

    // Bepaal de residentie op de gegeven coördinaat, als die bestaat
    public Residence getResidenceAt(RCCoordinate coordinate) {
        return residences.stream().filter(t -> t.isOn(coordinate)).findFirst().orElse(null);
    }

    // Bepaal de industrie op de gegeven coördinaat, als die bestaat
    public Industry getIndustryAt(RCCoordinate coordinate) {
        return industries.stream().filter(t -> t.isOn(coordinate)).findFirst().orElse(null);
    }

    // Bepaal de winkel op de gegeven coördinaat, als die bestaat
    public Commerce getCommerceAt(RCCoordinate coordinate) {
        return commerces.stream().filter(t -> t.isOn(coordinate)).findFirst().orElse(null);
    }

    // Bepaal de werkgever op de gegeven coördinaat, als die bestaat
    public Employer getEmployerAt(RCCoordinate coordinate) {
        Industry industry = getIndustryAt(coordinate);
        return industry != null ? industry : getCommerceAt(coordinate);
    }

    // Bepaal de zone op de gegeven coördinaat, als die bestaat
    public Zone getZoneAt(RCCoordinate coordinate) {
        Zone[] zones = {getResidenceAt(coordinate), getIndustryAt(coordinate), getCommerceAt(coordinate)};
        return Arrays.stream(zones).filter(Objects::nonNull).findFirst().orElse(null);
    }

    // Bepaal de tegel op de gegeven coördinaat, als die bestaat
    public Tile getTileAt(RCCoordinate coordinate) {
        Zone zone = getZoneAt(coordinate);
        return zone == null ? getRoadSystem().getRoadAt(coordinate) : zone;
    }

    // Bepaal of een gegeven coördinaat leeg is en op de kaart ligt
    public boolean emptyOnMapPosition(RCCoordinate coordinate) {
        return getTileAt(coordinate) == null && positionOnMap(coordinate);
    }

    // Bepaal of een gegeven coördinaat op de kaart ligt
    public boolean positionOnMap(RCCoordinate coordinate) {
        return coordinate.getRow() < POLIS_DIMENSION && coordinate.getRow() >= 0
                && coordinate.getColumn() < POLIS_DIMENSION && coordinate.getColumn() >= 0;
    }

    @Override
    public <R> R accept(InfoVisitor<R> visitor) {
        return visitor.visitPolis(this);
    }

    @Override
    public String toString() {
        return "Statistics:";
    }
}
