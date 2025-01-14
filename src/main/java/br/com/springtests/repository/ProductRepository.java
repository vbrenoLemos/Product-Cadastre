package br.com.springtests.repository;

import br.com.springtests.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    List<ProductModel> findAllByOrderByValueAsc();
}
