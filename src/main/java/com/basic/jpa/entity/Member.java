package com.basic.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "MEMBER")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 15, name = "member_id")
    private String memberId;

    @Column(nullable = false, name = "member_password")
    private String password;

    @Column(unique = true, nullable = false, length = 5, name = "member_name")
    private String name;

    @Column(unique = true, nullable = false, length = 15, name = "member_phone")
    private String phone;

    @Column(unique = true, nullable = false, name = "member_email")
    private String email;

    @Column(nullable = false, name = "member_use")
    private boolean useYn;

    @Column(nullable = false, length = 10, name = "member_role")
    private String role;

    @CreatedDate
    @Column(nullable = false, name = "create_datetime")
    private LocalDateTime createDatetime;

    @LastModifiedDate
    @Column(nullable = false, name = "modify_datetime")
    private LocalDateTime modifyDatetime;
}