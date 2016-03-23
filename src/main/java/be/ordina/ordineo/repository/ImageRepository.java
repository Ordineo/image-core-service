package be.ordina.ordineo.repository;

import be.ordina.ordineo.model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by DeDu on 23/03/2016.
 */
public interface ImageRepository extends PagingAndSortingRepository<Image,Long> {

    Image findByUsernameIgnoreCase(@Param("username") String username);

}
