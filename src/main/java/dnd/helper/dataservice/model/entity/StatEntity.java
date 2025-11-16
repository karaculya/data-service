package dnd.helper.dataservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stats")
public class StatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

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
