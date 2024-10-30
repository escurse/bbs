package com.escass.advancedbbs.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class BoardEntity {
    private String id;
    private String text;
}
