package com.obstrom.packerservice.units;

import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;

public interface PackerUnit<T extends Quantity<T>> {

    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PackerUnit.class);

    Unit<T> getUnit();

    static <T extends Quantity<T>> int convert(Unit<T> sourceUnit, Unit<T> targetUnit, int value) {
        if (sourceUnit.equals(targetUnit)) {
            log.debug("weight unit was already {}, not converting", targetUnit);
            return value;
        }

        int convertedValue = Quantities
                .getQuantity(value, sourceUnit)
                .to(targetUnit)
                .getValue().intValue();

        log.debug("successfully converted weight {} {} to {} {}", value, sourceUnit, convertedValue, targetUnit);
        return convertedValue;
    }

}
