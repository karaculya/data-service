package dnd.helper.dataservice.model;

import jakarta.persistence.*;
import openapi.dto.Stat;

public class StatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Stat name;

//    min=1 max=30
    private Integer value;

//    min=-5 max=10
    private Integer modifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    /*
    STR("Сила"),
    DEX("Ловкость"),
    CON("Телосложение"),
    INT("Интеллект"),
    WIS("Мудрость"),
    CHA("Харизма");
     */
}
