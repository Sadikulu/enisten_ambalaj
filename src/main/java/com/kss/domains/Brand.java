package com.kss.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kss.domains.enums.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private BrandStatus status;

    @Column
    private Boolean builtIn = false;

    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @Column
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "brand", orphanRemoval = true)
    private List<Product> product = new ArrayList<>();

    @OneToOne(orphanRemoval = true)
    private ImageFile image;

}
