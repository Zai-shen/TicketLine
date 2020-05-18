package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "published_at")
    private LocalDateTime publishedAt;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String summary;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = true, name = "picture_path")
    private String picturePath;

    @ManyToOne
    private Author author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        this.content = text;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picture) {
        this.picturePath = picture;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) &&
            Objects.equals(publishedAt, news.publishedAt) &&
            Objects.equals(title, news.title) &&
            Objects.equals(summary, news.summary) &&
            Objects.equals(content, news.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publishedAt, title, summary, content);
    }

    @Override
    public String toString() {
        return "News{" +
            "id=" + id +
            ", publishedAt=" + publishedAt +
            ", title='" + title + '\'' +
            ", summary='" + summary + '\'' +
            ", text='" + content + '\'' +
            '}';
    }


    public static final class MessageBuilder {
        private Long id;
        private LocalDateTime publishedAt;
        private String title;
        private String summary;
        private String text;

        private MessageBuilder() {
        }

        public static MessageBuilder aMessage() {
            return new MessageBuilder();
        }

        public MessageBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MessageBuilder withPublishedAt(LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public MessageBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public MessageBuilder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public MessageBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public News build() {
            News news = new News();
            news.setId(id);
            news.setPublishedAt(publishedAt);
            news.setTitle(title);
            news.setSummary(summary);
            news.setContent(text);
            return news;
        }
    }
}