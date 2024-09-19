package com.TravelBookingSystem_BE.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor //chi tao Contructor cho cac thuoc tinh co kdl la @NotNull hay final, giam thieu ma nguon can phai viet cho cac thuoc tinh khac
public enum Role { //muc tieu cua Role la dinh nghia va lien ket cac quyen lai giua cac AGENT, CUSTOMER, ADMIN

    ADMIN(                      //mac dinh ADMIN se co cac quyen sau day
            Set.of(
                    Permission.CUSTOMER_READ,
                    Permission.CUSTOMER_CREATE,
                    Permission.CUSTOMER_DELETE,
                    Permission.CUSTOMER_UPDATE,
                    Permission.AGENT_READ,
                    Permission.AGENT_CREATE,
                    Permission.AGENT_UPDATE,
                    Permission.AGENT_DELETE
            )
    ),

    AGENT(                         //mac dinh AGENT se co cac quyen sau day
            Set.of(
                    Permission.CUSTOMER_READ,
                    Permission.CUSTOMER_CREATE,
                    Permission.CUSTOMER_DELETE,
                    Permission.CUSTOMER_UPDATE,
                    Permission.AGENT_READ,
                    Permission.AGENT_CREATE,
                    Permission.AGENT_UPDATE,
                    Permission.AGENT_DELETE
            )
    ),
    CUSTOMER(
            Set.of(
                    Permission.CUSTOMER_READ,
                    Permission.CUSTOMER_CREATE,
                    Permission.CUSTOMER_DELETE,
                    Permission.CUSTOMER_UPDATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() { //khi da tao va phan quyen xong roi nhung muon add cac quyen nay vao de Security quan ly thi
        var authorities = getPermissions()
                .stream() //xem trong notePad
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }


}
