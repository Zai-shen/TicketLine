package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

@Component
public class SecurityTestHelper {

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    public MockHttpServletRequestBuilder withUserAuthentication(MockHttpServletRequestBuilder builder) {
        List<String> roles = Arrays.asList(AuthorizationRole.USER.getAuthorities());
        builder.header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken("testuser", roles));
        return builder;
    }

    public MockHttpServletRequestBuilder withAdminAuthentication(MockHttpServletRequestBuilder builder) {
        List<String> roles = Arrays.asList(AuthorizationRole.ADMIN.getAuthorities());
        builder.header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken("testadmin", roles));
        return builder;
    }
}
