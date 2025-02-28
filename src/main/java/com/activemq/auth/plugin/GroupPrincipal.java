package com.activemq.auth.plugin;
import java.security.Principal;

/**
 * @version $Rev: $ $Date: $
 */
public class GroupPrincipal implements Principal {

    private final String name;
    private transient int hash;

    public GroupPrincipal(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GroupPrincipal that = (GroupPrincipal)o;

        if (!name.equals(that.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = name.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return name;
    }
}
