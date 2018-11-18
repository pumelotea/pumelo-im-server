package io.pumelo.im.model;

import lombok.Data;

import java.util.Set;

@Data
public class SessionGroup {
    private Set<String> memberSets;
}
