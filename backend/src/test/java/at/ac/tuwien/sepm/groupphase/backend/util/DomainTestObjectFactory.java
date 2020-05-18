package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Author;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;

import java.time.LocalDateTime;

public class DomainTestObjectFactory {

    public static User getUser() {
        final User user = new User();
        user.setAddress(getAddress());
        user.setRole(AuthorizationRole.USER);
        user.setFirstname("Hans");
        user.setLastname("Müller");
        user.setEmail("hans.mueller@example.com");
        user.setPassword("secretPassword");
        user.setId(14L);
        return user;
    }
    public static Address getAddress() {
        final Address address = new Address();
        address.setStreet("Hauptstraße");
        address.setHousenr("24A");
        address.setPostalcode("3100");
        address.setCity("St. Pölten");
        address.setCountry("Österreich");
        return address;
    }

    public static Author getAuthor() {
        final Author author = new Author();
        author.setEmail("hans.mueller@example.com");
        author.setFirstName("Hans");
        author.setLastName("Mueller");
        return author;
    }

    public static News getNews(){
        final News news = new News();
        news.setId(16L);
        news.setTitle("Breaking News!");
        news.setSummary("In conclusion we can conclude alternative facts are in fact no facts.");
        news.setContent("Today we discovered, that I am to lazy to write more content.");
        news.setPublishedAt(LocalDateTime.of(2002,1,13,12,55,59));
        news.setAuthor(getAuthor());
        return news;
    }

}
