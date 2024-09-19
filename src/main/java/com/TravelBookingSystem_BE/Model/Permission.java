package com.TravelBookingSystem_BE.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public enum Permission { //CUSTOMER_READ(có thể là bất kỳ kdl nào để mô tả).Nhưng phải thêm contructor để enum có thể nhận mô tả
                         //mục tiêu là tạo ra các quyền cho người dùng và đại lý

        CUSTOMER_READ("customer:read"),
        CUSTOMER_CREATE("customer:create"),
        CUSTOMER_DELETE("customer:delete"),
        CUSTOMER_UPDATE("customer:update"),
        AGENT_READ("agent:read"),
        AGENT_CREATE("agent:create"),
        AGENT_DELETE("agent:delete"),
        AGENT_UPDATE("agent:update");

        @Getter
        private final String permission;

}
