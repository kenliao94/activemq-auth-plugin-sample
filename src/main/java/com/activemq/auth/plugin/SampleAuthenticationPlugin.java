package com.activemq.auth.plugin;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.security.AuthenticationUser;

/**
 * A sample authentication plugin
 *
 * @org.apache.xbean.XBean element="sampleAuthenticationPlugin"
 *                         description="Provides a simple authentication plugin
 *                         configured with a map of user-passwords and a map of
 *                         user-groups or a list of authentication users"
 *
 *
 */
public class SampleAuthenticationPlugin implements BrokerPlugin {

    private Map<String, String> userPasswords = new HashMap<String, String>();
    private Map<String, Set<Principal>> userGroups = new HashMap<String, Set<Principal>>();

    public SampleAuthenticationPlugin() {
    }

    public SampleAuthenticationPlugin(List<?> users) {
        setUsers(users);
    }

    public Broker installPlugin(Broker parent) {
        return new SampleAuthenticator(parent, userPasswords, userGroups);
    }

    public Map<String, Set<Principal>> getUserGroups() {
        return userGroups;
    }

    /**
     * Sets individual users for authentication
     *
     * @org.apache.xbean.ElementType class="org.apache.activemq.security.AuthenticationUser"
     */
    public void setUsers(List<?> users) {
        userPasswords.clear();
        userGroups.clear();
        for (Iterator<?> it = users.iterator(); it.hasNext();) {
            AuthenticationUser user = (AuthenticationUser)it.next();
            userPasswords.put(user.getUsername(), user.getPassword());
            Set<Principal> groups = new HashSet<Principal>();
            if (user.getGroups() != null) {
                StringTokenizer iter = new StringTokenizer(user.getGroups(), ",");
                while (iter.hasMoreTokens()) {
                    String name = iter.nextToken().trim();
                    groups.add(new GroupPrincipal(name));
                }
            }
            userGroups.put(user.getUsername(), groups);
        }
    }

}
