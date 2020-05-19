package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;

public class NewNewsValidator extends Validator<News> {
    @Override
    protected void doValidation(News news) {
        notNullOrName(news.getPublishedAt(), "PubliziertAm");
        notEmptyOrName(news.getTitle(), "Titel");
        notEmptyOrName(news.getSummary(), "Kurzfassung");
        notEmptyOrName(news.getContent(), "Text");
        callValidatorOnChild(new NewAuthorValidator(), news.getAuthor());
    }
}
