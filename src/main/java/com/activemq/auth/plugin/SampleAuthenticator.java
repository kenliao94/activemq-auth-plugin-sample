package com.activemq.auth.plugin;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.security.AbstractAuthenticationBroker;
import org.apache.activemq.security.SecurityContext;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;

/**
 * Handles authenticating a users against a simple username/password map.
 */
public class SampleAuthenticator extends AbstractAuthenticationBroker {

    private Map<String,String> userPasswords;
    private Map<String,Set<Principal>> userGroups;

    public SampleAuthenticator(Broker next, Map<String,String> userPasswords, Map<String,Set<Principal>> userGroups) {
        super(next);
        this.userPasswords = userPasswords;
        this.userGroups = userGroups;
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        SecurityContext securityContext = context.getSecurityContext();
        if (securityContext == null) {
            securityContext = authenticate(info.getUserName(), info.getPassword(), null);
            context.setSecurityContext(securityContext);
            securityContexts.add(securityContext);
        }

        try {
            super.addConnection(context, info);
        } catch (Exception e) {
            securityContexts.remove(securityContext);
            context.setSecurityContext(null);
            throw e;
        }
    }

    @Override
    public SecurityContext authenticate(String username, String password, X509Certificate[] certificates) throws SecurityException {
        SecurityContext securityContext = null;

        String pw = userPasswords.get(username);
        if (pw == null || !pw.equals(password)) {
            throw new SecurityException("User name [" + username + "] or password is invalid.");
        }

        final Set<Principal> groups = userGroups.get(username);
        securityContext = new SecurityContext(username) {
            @Override
            public Set<Principal> getPrincipals() {
                return groups;
            }
        };

        return securityContext;
    }
}
