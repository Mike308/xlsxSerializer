package org.xlsx.serializer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValueField {
    private int columnIndex;
    private String value;
}
