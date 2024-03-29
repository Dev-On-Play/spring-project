package mos.category.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mos.mogako.entity.Mogako;

@Entity
@Getter
@RequiredArgsConstructor
public class MogakoCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mogako mogako;

    @ManyToOne(fetch = FetchType.LAZY)
    private SmallCategory smallCategory;
}
