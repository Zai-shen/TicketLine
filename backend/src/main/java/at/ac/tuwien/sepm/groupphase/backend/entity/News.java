package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "picture_path")
    private String picturePath;

    @Column(nullable = false, length = 100)
    private String author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_read_news",
               joinColumns = {@JoinColumn(name = "news_id", nullable = false, updatable = false),},
               inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)})
    private Set<User> readByUsers;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<User> getReadByUsers() {
        return readByUsers;
    }

    public void setReadByUsers(Set<User> readByUsers) {
        this.readByUsers = readByUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof News)) {
            return false;
        }
        News news = (News) o;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publishedAt, title, summary, content, author);
    }

    @Override
    public String toString() {
        return "News{" + "id=" + id + ", publishedAt=" + publishedAt + ", title='" + title + '\'' + ", summary='" +
            summary + '\'' + ", content='" + content + '\'' + ", author='" + author + '\'' + '}';
    }
}