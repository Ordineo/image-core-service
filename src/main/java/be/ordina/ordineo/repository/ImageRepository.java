package be.ordina.ordineo.repository;

import be.ordina.ordineo.model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends PagingAndSortingRepository<Image,Long> {

    Image findByUsernameIgnoreCase(@Param("username") String username);

}
