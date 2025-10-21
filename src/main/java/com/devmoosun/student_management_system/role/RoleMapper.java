package com.devmoosun.student_management_system.role;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    // Custom mappings -- Automatically
    default String roleToString(Role role) {
        return role != null ? role.getName() : null;
    }

    default Role stringToRole(String roleName) {
        if (roleName == null) {
            return null;
        }

        Role role = new Role();
        role.setName(roleName);
        return role;
    }
}
