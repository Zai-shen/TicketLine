package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;

public class NewNewsValidator extends Validator<News> {
    @Override
    protected void doValidation(News news) {
        notNullOrName(news.getPublishedAt(), "PublishedAt");
        notEmptyOrName(news.getTitle(), "Title");
        notEmptyOrName(news.getSummary(), "Summary");
        notEmptyOrName(news.getText(), "Text");
        //notNullOrName();
    }
}
