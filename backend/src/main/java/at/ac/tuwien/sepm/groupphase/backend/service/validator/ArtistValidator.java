package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;

public class ArtistValidator extends Validator<Artist> {
    @Override
    protected void doValidation(Artist address) {
        notEmptyOrName(address.getFirstname(), "Vorname");
        notEmptyOrName(address.getLastname(), "Nachname");
    }
}
