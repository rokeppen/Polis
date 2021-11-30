package polis.utils;

import polis.core.Polis;
import polis.tiles.*;

public interface InfoVisitor<R> {
    R visitCommerce(Commerce commerce);
    R visitIndustry(Industry industry);
    R visitResidence(Residence residence);
    R visitPolis(Polis polis);
}
