package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArtistService {

    /**
     * Returns a Page of Artists
     * @param pageable Pagination information
     * @return List of Artists of specified size by pagination
     */
    Page<Artist> getAllArtists(Pageable pageable);

    /**
     * Searches for Artists by firstname and lastname
     * @param artist artist object
     * @param pageable Pagination information
     * @return List of Artists that fit search object of specified size by pagination
     */
    Page<Artist> searchArtists(Artist artist, Pageable pageable);

    /**
     * Retrieve an artist entity from storage
     * @param artistId id of the artist to fetch
     * @return Artist entity with the specified ID
     */
    Artist getArtist(Long artistId);
}
