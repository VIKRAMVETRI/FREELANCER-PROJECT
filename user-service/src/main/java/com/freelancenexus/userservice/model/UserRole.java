package com.freelancenexus.userservice.model;

/**
 * UserRole
 *
 * <p>Enumeration of user roles used throughout the Freelance Nexus system to
 * control access and permissions. Roles are persisted as strings in the database
 * (see {@code @Enumerated(EnumType.STRING)}) and are used with method- and
 * endpoint-level security checks (for example, {@code @PreAuthorize}).</p>
 *
 * <p>Typical usage:
 * <ul>
 *   <li>ADMIN — full administrative privileges</li>
 *   <li>CLIENT — a user who posts jobs and hires freelancers</li>
 *   <li>FREELANCER — a user who performs work for clients</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */
public enum UserRole {
    /**
     * Administrative user with full system privileges (manage users, configuration, etc.).
     */
    ADMIN,

    /**
     * Client user who can create projects, post jobs, and hire freelancers.
     */
    CLIENT,

    /**
     * Freelancer user who can apply to projects, submit work, and receive payments.
     */
    FREELANCER
}
