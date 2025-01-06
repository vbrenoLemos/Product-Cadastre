package br.com.springtests.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


@Data
@Entity
@Table(name = "TB_PRODUCTS")
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private BigDecimal value;
    private String description;
}
