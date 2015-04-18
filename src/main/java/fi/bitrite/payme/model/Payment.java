package fi.bitrite.payme.model;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Author: johannes.
 */
@Value
@AllArgsConstructor
public class Payment {

    String number;
    Double sum;

}
